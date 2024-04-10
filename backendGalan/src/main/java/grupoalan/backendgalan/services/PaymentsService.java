package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Payments;
import grupoalan.backendgalan.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {
    private final PaymentsRepository paymentRepository;

    @Autowired
    public PaymentsService(PaymentsRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void savePayment(Payments payment) {
        paymentRepository.save(payment);
    }

    public Payments getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    // Otros métodos de servicio para operaciones relacionadas con pagos
}
