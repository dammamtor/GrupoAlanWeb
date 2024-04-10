package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Variants;
import grupoalan.backendgalan.repository.VariantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantsService {
    @Autowired
    private VariantsRepository variantsRepository;

    // Método para encontrar una variante por su ID
    public Variants getVariantByID(Long id){
        return variantsRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las variantes
    public List<Variants> getAllVariants(){
        return variantsRepository.findAll();
    }

    // Método para guardar una nueva variante
    public Variants saveVariant(Variants variant){
        return variantsRepository.save(variant);
    }

    // Método para eliminar una variante por su ID
    public void deleteVariantByID(Long id){
        variantsRepository.deleteById(id);
    }
}
