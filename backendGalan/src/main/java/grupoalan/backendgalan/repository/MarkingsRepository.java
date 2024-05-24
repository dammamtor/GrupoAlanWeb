package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Markings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkingsRepository extends JpaRepository<Markings, Long> {
    List<Markings> findByRef(String ref);

    List<Markings> findAllByRefAndTechniqueRef(String ref, String techniqueRef);

    List<Markings> findByPrintAreaId(Integer printAreaId);
}
