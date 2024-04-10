package grupoalan.backendgalan.services;
import grupoalan.backendgalan.model.Orders;
import grupoalan.backendgalan.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con pedidos
}