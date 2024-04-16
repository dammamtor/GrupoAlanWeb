package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Descriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DescriptionRepository extends JpaRepository<Descriptions, Long> {
    Optional<Descriptions> findByRef(String ref);
}
