// =================================================================================
// PAQUETE: com.bancolombia.mrm.config
// DESCRIPCIÓN: Clases de configuración para la aplicación.
// =================================================================================
package com.bancolombia.mrm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import java.util.List;

/**
 * Carga configuraciones personalizadas desde application.properties.
 * Esto centraliza la configuración y evita valores hardcodeados en el código.
 * Sigue el principio de Inversión de Control (IoC).
 */
@Configuration
@Getter
public class AppConfig {

    @Value("${app.notificaciones.dias-aviso}")
    private int diasAviso;

    @Value("${app.notificaciones.equipo-validador}")
    private List<String> equipoValidador;

    @Value("${app.notificaciones.roles-a-notificar}")
    private List<String> rolesANotificar;

    @Value("${app.notificaciones.estados-plan-excluidos}")
    private List<String> estadosPlanExcluidos;
    
    @Value("${spring.mail.username}")
    private String senderEmail;
}
