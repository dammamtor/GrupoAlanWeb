package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
