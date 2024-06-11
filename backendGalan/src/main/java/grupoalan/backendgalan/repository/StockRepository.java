package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByUniqueRefAndMatnr(String uniqueRef, String matnr);
}
