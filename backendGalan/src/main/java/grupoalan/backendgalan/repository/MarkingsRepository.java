package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Markings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkingsRepository extends JpaRepository<Markings, Long> {
    Markings findByRefAndTechniqueRef(String ref, String techniqueRef);

    List<Markings> findByRef(String ref);
}
