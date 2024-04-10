package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    // Método para encontrar un producto por su ID
    public Products getProductByID(Long id){
        return productsRepository.findById(id).orElse(null);
    }

    // Método para encontrar todos los productos
    public List<Products> getAllProducts(){
        return productsRepository.findAll();
    }

    // Método para guardar un nuevo producto
    public Products saveProduct(Products product){
        return productsRepository.save(product);
    }

    // Método para eliminar un producto por su ID
    public void deleteProductByID(Long id){
        productsRepository.deleteById(id);
    }
}
