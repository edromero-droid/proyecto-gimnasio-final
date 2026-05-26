package com.gimnasio.socios.repository;

import com.gimnasio.socios.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    Optional<Socio> findByEmail(String email);

    List<Socio> findByEstado(String estado);

    boolean existsByEmail(String email);
}
