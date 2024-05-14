package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.MarkingTechniques;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkingTechniquesRepository extends JpaRepository<MarkingTechniques, Long> {
    List<MarkingTechniques> findByRef(String ref);
}
