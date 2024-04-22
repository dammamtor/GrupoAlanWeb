package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Descriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DescriptionRepository extends JpaRepository<Descriptions, Long> {
    List<Descriptions> findByRef(String ref);

//    List<Descriptions> findByRefAndLangIn(String number, List<String> list);
}
