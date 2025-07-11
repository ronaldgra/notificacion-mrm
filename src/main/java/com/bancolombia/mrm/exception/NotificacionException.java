package com.bancolombia.mrm.exception;

/**
 * Excepción personalizada para errores en el proceso de notificaciones
 */
public class NotificacionException extends RuntimeException {
    
    public NotificacionException(String message) {
        super(message);
    }
    
    public NotificacionException(String message, Throwable cause) {
        super(message, cause);
    }
}
