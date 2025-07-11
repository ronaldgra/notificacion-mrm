package com.bancolombia.mrm.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UsuarioDto {
    private String id;
    private String email;
    private String nombre;
    private List<Long> planesAsociados;
}
