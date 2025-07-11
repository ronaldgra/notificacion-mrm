package com.bancolombia.mrm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanAccionDto {
    private Long id;
    private String titulo;
    private String idModelo;
    private String prioridad;
    private String estadoPlan;
    private String realizado;
    private String estadoVencimiento;
    private String alerta;
    private String aprobadores;
    private String propietarios;
    private String responsables;
}
