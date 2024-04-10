package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
