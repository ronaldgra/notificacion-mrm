// =================================================================================
// PAQUETE: com.bancolombia.mrm.repository
// DESCRIPCIÓN: Interfaces de Spring Data JPA para el acceso a datos.
// =================================================================================
package com.bancolombia.mrm.repository;

import com.bancolombia.mrm.entity.PlanAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlanAccionRepository extends JpaRepository<PlanAccion, Long> {
    // Spring Data JPA crea la implementación de esta consulta automáticamente
    List<PlanAccion> findByEstadoPlanAccNotInAndUsuarioActivoUpdatePlanAcc(List<String> estados, boolean activo);
}
