package com.gimnasio.instructores.repository;
import com.gimnasio.instructores.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByEstado(String estado);
    Optional<Instructor> findByEmail(String email);
}
