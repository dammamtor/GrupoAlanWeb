package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Colors, Long> {
    @Query("SELECT c.name, COUNT(v) " +
            "FROM Colors c " +
            "LEFT JOIN c.variants v " +
            "WHERE c.lang = 1 " +  // Filtro por el valor de lang
            "GROUP BY c.name")
    List<Object[]> findColorsWithProductCount();

    List<Colors> findByCode(String code);
}
