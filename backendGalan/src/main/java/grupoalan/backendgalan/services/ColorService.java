package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public void saveColor(Colors color) {
        colorRepository.save(color);
    }

    public Colors getColorById(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con colores
}
