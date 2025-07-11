// =================================================================================
// PAQUETE: com.bancolombia.mrm.controller
// DESCRIPCIÓN: Controladores REST para exponer endpoints HTTP.
// =================================================================================
package com.bancolombia.mrm.controller;

import com.bancolombia.mrm.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone un endpoint REST para poder disparar el proceso de notificación manualmente.
 * Esto facilita las pruebas y la integración con otras herramientas.
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificaciones() {
        try {
            notificacionService.procesarYEnviarNotificaciones();
            return ResponseEntity.ok("Proceso de notificación iniciado exitosamente.");
        } catch (Exception e) {
            // Loggear el error
            return ResponseEntity.status(500).body("Error al iniciar el proceso: " + e.getMessage());
        }
    }
}


