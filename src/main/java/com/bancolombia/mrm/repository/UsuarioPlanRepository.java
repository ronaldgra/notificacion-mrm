package com.bancolombia.mrm.repository;

import com.bancolombia.mrm.entity.UsuarioPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioPlanRepository extends JpaRepository<UsuarioPlan, Long> {
    List<UsuarioPlan> findByUsuarioActivoRelPlanAccAndTipoRelacionUsuarioIn(boolean activo, List<String> roles);
}
