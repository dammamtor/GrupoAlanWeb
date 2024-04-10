package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
