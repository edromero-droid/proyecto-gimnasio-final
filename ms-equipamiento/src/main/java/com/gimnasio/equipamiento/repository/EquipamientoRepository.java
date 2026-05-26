package com.gimnasio.equipamiento.repository;
import com.gimnasio.equipamiento.entity.Equipamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipamientoRepository extends JpaRepository<Equipamiento, Long> {
    List<Equipamiento> findByEstado(String estado);
    Optional<Equipamiento> findByCodigo(String codigo);
}
