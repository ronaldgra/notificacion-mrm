// =================================================================================
// PAQUETE: com.bancolombia.mrm
// DESCRIPCIÓN: Clase principal de la aplicación Spring Boot.
// =================================================================================
package com.bancolombia.mrm;

import com.bancolombia.mrm.service.NotificacionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificacionPlanesMrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificacionPlanesMrmApplication.class, args);
    }

    /**
     * CommandLineRunner se ejecuta automáticamente al iniciar la aplicación.
     * Es ideal para tareas batch como la que realiza este servicio.
     * Llama al servicio principal para iniciar el proceso de notificación.
     */
    @Bean
    public CommandLineRunner run(NotificacionService notificacionService) {
        return args -> {
            notificacionService.procesarYEnviarNotificaciones();
        };
    }
}
