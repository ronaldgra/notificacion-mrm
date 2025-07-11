// =================================================================================
// PAQUETE: com.bancolombia.mrm.entity
// DESCRIPCIÓN: Entidades JPA que mapean a las tablas de la base de datos.
// =================================================================================
package com.bancolombia.mrm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Table(name = "usuarios_planes")
@Data
public class UsuarioPlan {
    @Id // Se asume una clave primaria compuesta o un ID único
    private Long id; 
    private Long idPlanAccion;
    private String usuarioRelPlanAcc;
    private String nombreUsuarioRelPlanAcc;
    private String emailUsuarioRelPlanAcc;
    private String tipoRelacionUsuario;
    private boolean usuarioActivoRelPlanAcc;
}