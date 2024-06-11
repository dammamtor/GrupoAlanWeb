package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Variants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantsRepository extends JpaRepository<Variants, Long> {
    List<Variants> findByRef(String ref);

    Variants findByUniqueRef(String uniqueRef);

    Variants findByRefAndMatnr(String ref, String matnr);
}
