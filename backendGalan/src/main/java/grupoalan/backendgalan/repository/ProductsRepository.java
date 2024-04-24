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
    Products findByRef(String ref); // Cambiar el retorno a List<Products>
    //    Products findByRef(String number);

    // Método para buscar productos por una lista de nombres
    @Query("SELECT p FROM Products p WHERE p.name IN :names")
    List<Products> findByNameIn(@Param("names") List<String> names);
}
