package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    List<Categories> findByRef(String ref);

    List<Categories> findAllByLang(Integer i);
}
