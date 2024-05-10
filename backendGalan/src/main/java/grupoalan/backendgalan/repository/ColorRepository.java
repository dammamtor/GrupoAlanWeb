package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Colors, Long> {
    List<Colors> findByCode(String code);
    Colors findByNameAndLang(String name, Integer lang);
}
