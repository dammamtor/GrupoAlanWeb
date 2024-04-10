package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Markings;
import grupoalan.backendgalan.repository.MarkingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkingService {

    private final MarkingsRepository markingRepository;

    @Autowired
    public MarkingService(MarkingsRepository markingRepository) {
        this.markingRepository = markingRepository;
    }

    public void saveMarking(Markings marking) {
        markingRepository.save(marking);
    }

    public Markings getMarkingById(Long id) {
        return markingRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con marcados
}
