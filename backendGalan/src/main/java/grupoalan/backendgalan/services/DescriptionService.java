package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public void saveDescription(Descriptions description) {
        descriptionRepository.save(description);
    }

    public Descriptions getDescriptionById(Long id) {
        return descriptionRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con descripciones
}
