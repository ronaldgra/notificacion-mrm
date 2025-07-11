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
@Table(name = "planes_accion") // Nombre de la tabla en la BD
@Data // Lombok para getters, setters, toString, etc.
public class PlanAccion {
    @Id
    private Long idPlanAccion;
    private String tituloPlanAcc;
    private String prioridadPlanAcc;
    private String estadoPlanAcc;
    private LocalDate fVencimientoPlanAcc;
    private LocalDate fCierrePlanAcc;
    private boolean usuarioActivoUpdatePlanAcc;
    // Otros campos relevantes...
}
