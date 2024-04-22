package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Optional<Products> findByName(String name);

    Products findByRef(String number);
}
