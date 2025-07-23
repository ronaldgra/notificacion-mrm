// =================================================================================
// PAQUETE: com.bancolombia.mrm.service
// DESCRIPCIÓN: Lógica de negocio de la aplicación.
// =================================================================================
package com.bancolombia.mrm.service;

import com.bancolombia.mrm.config.AppConfig;
import com.bancolombia.mrm.dto.NotificacionDto;
import com.bancolombia.mrm.dto.PlanAccionDto;
import com.bancolombia.mrm.entity.PlanAccion;
import com.bancolombia.mrm.entity.UsuarioPlan;
import com.bancolombia.mrm.exception.NotificacionException;
import com.bancolombia.mrm.repository.PlanAccionRepository;
import com.bancolombia.mrm.repository.UsuarioPlanRepository;
import com.bancolombia.mrm.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificacionService {

    private final PlanAccionRepository planAccionRepository;
    private final UsuarioPlanRepository usuarioPlanRepository;
    private final EmailService emailService;
    private final HtmlTemplateService htmlTemplateService;
    private final AppConfig appConfig;

    public NotificacionService(PlanAccionRepository planAccionRepository, 
                              UsuarioPlanRepository usuarioPlanRepository, 
                              EmailService emailService, 
                              HtmlTemplateService htmlTemplateService, 
                              AppConfig appConfig) {
        this.planAccionRepository = planAccionRepository;
        this.usuarioPlanRepository = usuarioPlanRepository;
        this.emailService = emailService;
        this.htmlTemplateService = htmlTemplateService;
        this.appConfig = appConfig;
    }

    public void procesarYEnviarNotificaciones() {
        log.info("Iniciando proceso de notificación de planes de acción MRM...");

        try {
            // 1. Obtener datos con validación
            List<PlanAccion> planesActivos = obtenerPlanesActivos();
            List<UsuarioPlan> usuariosActivos = obtenerUsuariosActivos();

            if (planesActivos.isEmpty()) {
                log.info("No se encontraron planes activos para notificar. Proceso finalizado.");
                return;
            }

            if (usuariosActivos.isEmpty()) {
                log.info("No se encontraron usuarios activos para notificar. Proceso finalizado.");
                return;
            }

            // 2. Procesar y clasificar planes
            Map<Long, PlanAccionDto> planesDtoMap = procesarPlanes(planesActivos);

            // 3. Clasificar planes por estado
            Map<String, List<PlanAccionDto>> planesClasificados = clasificarPlanes(planesDtoMap.values());

            // 4. Obtener mapeo de usuarios con sus planes
            Map<String, List<Long>> planesPorUsuario = obtenerPlanesPorUsuario(usuariosActivos);

            // 5. Enviar notificaciones
            enviarNotificacionesAUsuarios(usuariosActivos, planesPorUsuario, planesClasificados);

            log.info("Proceso de notificación finalizado exitosamente. Planes procesados: {}, Usuarios evaluados: {}", 
                     planesActivos.size(), planesPorUsuario.size());

        } catch (Exception e) {
            log.error("Error crítico en el proceso de notificaciones: {}", e.getMessage(), e);
            throw new NotificacionException("Falló el proceso de notificaciones", e);
        }
    }

    private List<PlanAccion> obtenerPlanesActivos() {
        try {
            List<String> estadosExcluidos = appConfig.getEstadosPlanExcluidos();
            log.info("Buscando planes con estados NO incluidos en: {} y con usuario_activo_update_plan_acc = true", estadosExcluidos);
            List<PlanAccion> planes = planAccionRepository.findByEstadoPlanAccNotInAndUsuarioActivoUpdatePlanAcc(
                    estadosExcluidos, true);
            log.info("El repositorio encontró {} planes que cumplen los criterios iniciales.", planes.size());
            return planes;
        } catch (Exception e) {
            log.error("Error al obtener planes activos: {}", e.getMessage(), e);
            throw new NotificacionException("Error al consultar planes de acción", e);
        }
    }

    private List<UsuarioPlan> obtenerUsuariosActivos() {
        try {
            List<String> roles = appConfig.getRolesANotificar();
            log.info("Buscando usuarios con roles en: {} y con usuario_activo_rel_plan_acc = true", roles);
            List<UsuarioPlan> usuarios = usuarioPlanRepository.findByUsuarioActivoRelPlanAccAndTipoRelacionUsuarioIn(
                true, roles);
            log.info("El repositorio encontró {} usuarios que cumplen los criterios iniciales.", usuarios.size());
            return usuarios;
        } catch (Exception e) {
            log.error("Error al obtener usuarios activos: {}", e.getMessage(), e);
            throw new NotificacionException("Error al consultar usuarios", e);
        }
    }

    private Map<Long, PlanAccionDto> procesarPlanes(List<PlanAccion> planesActivos) {
        return planesActivos.stream()
            .map(this::convertirAplanDto)
            .collect(Collectors.toMap(PlanAccionDto::getId, dto -> dto));
    }

    private Map<String, List<PlanAccionDto>> clasificarPlanes(Collection<PlanAccionDto> planes) {
        Map<String, List<PlanAccionDto>> clasificados = new HashMap<>();
        clasificados.put("vencidos", new ArrayList<>());
        clasificados.put("proximos", new ArrayList<>());
        clasificados.put("inconsistentes", new ArrayList<>());

        planes.forEach(plan -> {
            try {
                if ("Vencido".equals(plan.getEstadoVencimiento())) {
                    clasificados.get("vencidos").add(plan);
                } else {
                    long diasParaVencimiento = calcularDiasParaVencimiento(plan);
                    if (diasParaVencimiento <= appConfig.getDiasAviso() && diasParaVencimiento >= 0) {
                        clasificados.get("proximos").add(plan);
                    }
                }
                
                // Detectar inconsistencias
                if (esInconsistente(plan)) {
                    clasificados.get("inconsistentes").add(plan);
                }
            } catch (Exception e) {
                log.warn("Error al clasificar plan {}: {}", plan.getId(), e.getMessage());
            }
        });

        log.info("Planes clasificados - Vencidos: {}, Próximos: {}, Inconsistentes: {}", 
                 clasificados.get("vencidos").size(), 
                 clasificados.get("proximos").size(), 
                 clasificados.get("inconsistentes").size());

        return clasificados;
    }

    private Map<String, List<Long>> obtenerPlanesPorUsuario(List<UsuarioPlan> usuariosActivos) {
        return usuariosActivos.stream()
            .collect(Collectors.groupingBy(
                UsuarioPlan::getUsuarioRelPlanAcc,
                Collectors.mapping(UsuarioPlan::getIdPlanAccion, Collectors.toList())
            ));
    }

    private void enviarNotificacionesAUsuarios(List<UsuarioPlan> usuariosActivos, 
                                             Map<String, List<Long>> planesPorUsuario,
                                             Map<String, List<PlanAccionDto>> planesClasificados) {
        int notificacionesEnviadas = 0;
        int erroresEnvio = 0;

        for (Map.Entry<String, List<Long>> entrada : planesPorUsuario.entrySet()) {
            String usuarioId = entrada.getKey();
            List<Long> planesIds = entrada.getValue();

            try {
                UsuarioPlan infoUsuario = buscarInfoUsuario(usuariosActivos, usuarioId);
                if (infoUsuario == null) {
                    log.warn("No se encontró información para usuario: {}", usuarioId);
                    continue;
                }

                // Filtrar planes del usuario
                List<PlanAccionDto> planesVencidosUsuario = filtrarPlanesPorUsuario(
                    planesClasificados.get("vencidos"), planesIds);
                List<PlanAccionDto> planesProximosUsuario = filtrarPlanesPorUsuario(
                    planesClasificados.get("proximos"), planesIds);
                List<PlanAccionDto> planesInconsistentesUsuario = filtrarPlanesPorUsuario(
                    planesClasificados.get("inconsistentes"), planesIds);

                // Solo enviar si hay planes que notificar
                if (hayPlanesParaNotificar(planesVencidosUsuario, planesProximosUsuario, planesInconsistentesUsuario)) {
                    enviarNotificacionIndividual(infoUsuario, planesVencidosUsuario, 
                                               planesProximosUsuario, planesInconsistentesUsuario);
                    notificacionesEnviadas++;
                    
                    // Pequeña pausa entre envíos para no saturar el servidor de correo
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                log.error("Error al procesar notificación para usuario {}: {}", usuarioId, e.getMessage());
                erroresEnvio++;
            }
        }

        log.info("Resumen de envíos - Exitosos: {}, Errores: {}", notificacionesEnviadas, erroresEnvio);
    }

    private UsuarioPlan buscarInfoUsuario(List<UsuarioPlan> usuariosActivos, String usuarioId) {
        return usuariosActivos.stream()
            .filter(u -> usuarioId.equals(u.getUsuarioRelPlanAcc()))
            .filter(u -> u.getEmailUsuarioRelPlanAcc() != null && !u.getEmailUsuarioRelPlanAcc().trim().isEmpty())
            .findFirst()
            .orElse(null);
    }

    private List<PlanAccionDto> filtrarPlanesPorUsuario(List<PlanAccionDto> planes, List<Long> planesIds) {
        return planes.stream()
            .filter(plan -> planesIds.contains(plan.getId()))
            .collect(Collectors.toList());
    }

    private boolean hayPlanesParaNotificar(List<PlanAccionDto>... listasPlanes) {
        return Arrays.stream(listasPlanes)
            .anyMatch(lista -> lista != null && !lista.isEmpty());
    }

    private void enviarNotificacionIndividual(UsuarioPlan infoUsuario,
                                            List<PlanAccionDto> planesVencidos,
                                            List<PlanAccionDto> planesProximos,
                                            List<PlanAccionDto> planesInconsistentes) {
        try {
            NotificacionDto notificacionDto = NotificacionDto.builder()
                .destinatarioEmail(infoUsuario.getEmailUsuarioRelPlanAcc())
                .asunto(generarAsuntoEmail(planesVencidos, planesProximos))
                .planesVencidos(planesVencidos)
                .planesProximos(planesProximos)
                .planesInconsistentes(planesInconsistentes)
                .templateModel(crearModeloTemplate(planesVencidos, planesProximos, planesInconsistentes))
                .build();

            String htmlContent = htmlTemplateService.generarEmail(notificacionDto);
            emailService.enviarEmail(
                infoUsuario.getEmailUsuarioRelPlanAcc(), 
                notificacionDto.getAsunto(), 
                htmlContent
            );

            log.info("Notificación enviada exitosamente a {} - Planes: {}V, {}P, {}I", 
                     infoUsuario.getEmailUsuarioRelPlanAcc(),
                     planesVencidos.size(), planesProximos.size(), planesInconsistentes.size());

        } catch (Exception e) {
            log.error("Error al enviar notificación a {}: {}", 
                     infoUsuario.getEmailUsuarioRelPlanAcc(), e.getMessage());
            throw new NotificacionException("Error al enviar email", e);
        }
    }

    private PlanAccionDto convertirAplanDto(PlanAccion plan) {
        try {
            long diasVencimiento = DateUtil.diasEntre(LocalDate.now(), plan.getFVencimientoPlanAcc());
            String estadoVencimiento = diasVencimiento < 0 ? "Vencido" : "Activo";
            String alerta = diasVencimiento < 0 ? 
                Math.abs(diasVencimiento) + " días vencido" :
                "Faltan " + diasVencimiento + " días";

            return PlanAccionDto.builder()
                .id(plan.getIdPlanAccion())
                .titulo(plan.getTituloPlanAcc())
                .prioridad(plan.getPrioridadPlanAcc())
                .estadoPlan(plan.getEstadoPlanAcc())
                .realizado(plan.getFCierrePlanAcc() == null ? "Pendiente" : "Entregado")
                .estadoVencimiento(estadoVencimiento)
                .alerta(alerta)
                .responsables("Por definir") // TODO: Mapear desde UsuarioPlan
                .aprobadores("Por definir")  // TODO: Mapear desde UsuarioPlan
                .propietarios("Por definir") // TODO: Mapear desde UsuarioPlan
                .build();
        } catch (Exception e) {
            log.error("Error al convertir plan {}: {}", plan.getIdPlanAccion(), e.getMessage());
            throw new NotificacionException("Error al procesar plan de acción", e);
        }
    }

    private Map<String, Object> crearModeloTemplate(List<PlanAccionDto> planesVencidos, 
                                                   List<PlanAccionDto> planesProximos,
                                                   List<PlanAccionDto> planesInconsistentes) {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("planesVencidos", planesVencidos != null ? planesVencidos : new ArrayList<>());
        modelo.put("planesProximos", planesProximos != null ? planesProximos : new ArrayList<>());
        modelo.put("planesInconsistentes", planesInconsistentes != null ? planesInconsistentes : new ArrayList<>());
        modelo.put("fechaGeneracion", LocalDate.now().toString());
        modelo.put("totalPlanes", (planesVencidos != null ? planesVencidos.size() : 0) + 
                                 (planesProximos != null ? planesProximos.size() : 0) + 
                                 (planesInconsistentes != null ? planesInconsistentes.size() : 0));
        return modelo;
    }

    private long calcularDiasParaVencimiento(PlanAccionDto plan) {
        // Esta implementación necesita la fecha de vencimiento del plan
        // Por ahora extraemos la información del campo alerta
        try {
            String alerta = plan.getAlerta();
            if (alerta != null && alerta.contains("Faltan")) {
                String[] partes = alerta.split(" ");
                return Long.parseLong(partes[1]);
            }
            return 0;
        } catch (Exception e) {
            log.warn("Error al calcular días para vencimiento del plan {}: {}", plan.getId(), e.getMessage());
            return 0;
        }
    }

    private boolean esInconsistente(PlanAccionDto plan) {
        // Detectar inconsistencias como:
        // - Planes marcados como entregados pero sin fecha de cierre
        // - Planes vencidos sin responsables asignados
        // - etc.
        return ("Entregado".equals(plan.getRealizado()) && "Vencido".equals(plan.getEstadoVencimiento())) ||
               (plan.getResponsables() == null || plan.getResponsables().trim().isEmpty());
    }

    private String generarAsuntoEmail(List<PlanAccionDto> planesVencidos, List<PlanAccionDto> planesProximos) {
        StringBuilder asunto = new StringBuilder("MRM - Notificación de Planes de Acción");
        
        if (!planesVencidos.isEmpty()) {
            asunto.append(" (").append(planesVencidos.size()).append(" vencidos)");
        }
        if (!planesProximos.isEmpty()) {
            asunto.append(" (").append(planesProximos.size()).append(" próximos)");
        }
        
        return asunto.toString();
    }
}
