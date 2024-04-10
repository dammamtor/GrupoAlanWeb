package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.ProductTypes;
import grupoalan.backendgalan.repository.ProductTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypesService {
    @Autowired
    private ProductTypesRepository productTypesRepository;

    // Método para encontrar un tipo de producto por su ID
    public ProductTypes getProductTypeByID(Long id){
        return productTypesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todos los tipos de producto
    public List<ProductTypes> getAllProductTypes(){
        return productTypesRepository.findAll();
    }

    // Método para guardar un nuevo tipo de producto
    public ProductTypes saveProductType(ProductTypes productType){
        return productTypesRepository.save(productType);
    }

    // Método para eliminar un tipo de producto por su ID
    public void deleteProductTypeByID(Long id){
        productTypesRepository.deleteById(id);
    }
}
