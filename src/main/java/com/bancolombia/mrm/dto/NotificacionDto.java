// =================================================================================
// PAQUETE: com.bancolombia.mrm.dto
// DESCRIPCIÓN: Data Transfer Objects para la comunicación entre capas.
// =================================================================================
package com.bancolombia.mrm.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class NotificacionDto {
    private String destinatarioEmail;
    private String asunto;
    private List<PlanAccionDto> planesProximos;
    private List<PlanAccionDto> planesVencidos;
    private List<PlanAccionDto> planesInconsistentes;
    private Map<String, Object> templateModel;
}
