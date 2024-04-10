package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.MarkingTechniques;
import grupoalan.backendgalan.repository.MarkingTechniquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkingTechniquesService {
    @Autowired
    private MarkingTechniquesRepository markingTechniquesRepository;

    // Método para encontrar una tecnica por su ID
    public MarkingTechniques getTechniqueByID(Long id){
        return markingTechniquesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las tecnicas
    public List<MarkingTechniques> listaTecnicas(){
        return markingTechniquesRepository.findAll();
    }

    // Método para guardar una nueva tecnica
    public MarkingTechniques guardarTecnica(MarkingTechniques techniqueSave){
        return markingTechniquesRepository.save(techniqueSave);
    }

    // Método para eliminar una tecnica por su ID
    void deleteById(Long id){
        markingTechniquesRepository.deleteById(id);
    };
}
