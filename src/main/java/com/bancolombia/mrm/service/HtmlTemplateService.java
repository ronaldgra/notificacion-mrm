package com.bancolombia.mrm.service;

import com.bancolombia.mrm.dto.NotificacionDto;
import com.bancolombia.mrm.exception.NotificacionException;
import com.bancolombia.mrm.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class HtmlTemplateService {

    private final TemplateEngine templateEngine;

    public HtmlTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generarEmail(NotificacionDto dto) {
        try {
            Context context = new Context();
            
            // Agregar logo en base64
            String logoBase64 = ImageUtil.encodeImageToBase64("src/main/resources/static/images/bancolombia-logo.png");
            context.setVariable("logoBase64", logoBase64);
            
            // Agregar datos del DTO
            context.setVariable("planesVencidos", dto.getPlanesVencidos());
            context.setVariable("planesProximos", dto.getPlanesProximos());
            context.setVariable("planesInconsistentes", dto.getPlanesInconsistentes());
            
            // Variables adicionales para el template
            context.setVariable("fechaGeneracion", dto.getTemplateModel().get("fechaGeneracion"));
            context.setVariable("totalPlanes", dto.getTemplateModel().get("totalPlanes"));
            
            return templateEngine.process("email-template", context);
        } catch (Exception e) {
            log.error("Error al generar template HTML: {}", e.getMessage());
            throw new NotificacionException("Error al generar contenido del correo", e);
        }
    }
}
