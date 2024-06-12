package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> findByName(String name);

    Optional<Products> findByProductId(Long id);

    Products findByRef(String ref); // Cambiar el retorno a List<Products>
    //    Products findByRef(String number);

    // Método para buscar productos por una lista de nombres
    @Query("SELECT p FROM Products p WHERE p.name IN :names")
    List<Products> findByNameIn(@Param("names") List<String> names);

    @Query("SELECT DISTINCT p FROM Products p " +
            "LEFT JOIN p.descriptions d " +
            "LEFT JOIN p.variants v " +
            "LEFT JOIN v.colorSet c " +
            "LEFT JOIN p.categories cat " +
            "WHERE (:categorias IS NULL OR cat.category IN :categorias) " +
            "AND (:colores IS NULL OR c.name IN :colores) " +
            "AND (:tipos IS NULL OR d.type IN :tipos)")
    List<Products> findByCategoriasAndColoresAndTipos(@Param("categorias") List<String> categorias,
                                                      @Param("colores") List<String> colores,
                                                      @Param("tipos") List<String> tipos);


    @Query("SELECT DISTINCT p FROM Products p " +
            "LEFT JOIN p.categories cat " +
            "LEFT JOIN p.variants v " +
            "LEFT JOIN v.stock s " +
            "WHERE (:categorias IS NULL OR cat.category IN :categorias) " +
            "AND (:unidadesMin IS NULL OR :unidadesMax IS NULL OR (s.stock BETWEEN :unidadesMin AND :unidadesMax))")
    List<Products> findByCategoriasAndUnidades(@Param("categorias") List<String> categorias,
                                               @Param("unidadesMin") float unidadesMin,
                                               @Param("unidadesMax") float unidadesMax);


}
