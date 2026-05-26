package com.gimnasio.asistencia.repository;
import com.gimnasio.asistencia.entity.RegistroAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistroAccesoRepository extends JpaRepository<RegistroAcceso, Long> {
    List<RegistroAcceso> findBySocioIdOrderByTimestampEventoDesc(Long socioId);
    List<RegistroAcceso> findByPermitidoFalseOrderByTimestampEventoDesc();
}
