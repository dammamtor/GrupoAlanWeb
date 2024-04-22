package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByRef(String number);
}
