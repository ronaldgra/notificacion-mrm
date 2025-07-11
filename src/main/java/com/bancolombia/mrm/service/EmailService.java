package com.bancolombia.mrm.service;

import com.bancolombia.mrm.exception.NotificacionException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private static final int MAX_REINTENTOS = 3;
    private static final long DELAY_REINTENTO = 2000; // 2 segundos

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String para, String asunto, String contenidoHtml) {
        validateEmailInputs(para, asunto, contenidoHtml);
        
        int intentos = 0;
        Exception ultimaExcepcion = null;
        
        while (intentos < MAX_REINTENTOS) {
            try {
                intentos++;
                enviarEmailInterno(para, asunto, contenidoHtml);
                log.info("Correo enviado exitosamente a {} en el intento {}", para, intentos);
                return;
                
            } catch (MessagingException e) {
                ultimaExcepcion = e;
                log.warn("Error al enviar correo a {} (intento {}): {}", para, intentos, e.getMessage());
                
                if (intentos < MAX_REINTENTOS) {
                    try {
                        Thread.sleep(DELAY_REINTENTO);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new NotificacionException("Proceso interrumpido durante reintento de envío", ie);
                    }
                }
            }
        }
        
        log.error("Falló el envío de correo a {} después de {} intentos", para, MAX_REINTENTOS);
        throw new NotificacionException("Error al enviar correo después de " + MAX_REINTENTOS + " intentos", ultimaExcepcion);
    }
    
    private void validateEmailInputs(String para, String asunto, String contenidoHtml) {
        if (para == null || para.trim().isEmpty()) {
            throw new NotificacionException("El destinatario del correo no puede estar vacío");
        }
        if (asunto == null || asunto.trim().isEmpty()) {
            throw new NotificacionException("El asunto del correo no puede estar vacío");
        }
        if (contenidoHtml == null || contenidoHtml.trim().isEmpty()) {
            throw new NotificacionException("El contenido del correo no puede estar vacío");
        }
        
        // Validación básica del formato de email
        if (!para.contains("@") || !para.contains(".")) {
            throw new NotificacionException("El formato del email no es válido: " + para);
        }
    }
    
    private void enviarEmailInterno(String para, String asunto, String contenidoHtml) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("no-reply@bancolombia.com.co");
        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true); // true indica que es HTML

        mailSender.send(message);
    }
}
