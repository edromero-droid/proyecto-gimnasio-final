package com.gimnasio.clases.repository;
import com.gimnasio.clases.entity.SesionClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SesionClaseRepository extends JpaRepository<SesionClase, Long> {
    List<SesionClase> findByEstado(String estado);
}
