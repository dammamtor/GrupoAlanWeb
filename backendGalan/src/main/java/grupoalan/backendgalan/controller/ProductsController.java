package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductsController {
    static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    ProductsService productsService;
    @GetMapping("/makito/products")
    public ResponseEntity<List<Products>> makitoCategories() {
        logger.info("HAZ HECHO UNA PETICION A LISTA DE CATEGORIAS");
        List<Products> products = productsService.makitoProductsFromApi();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}