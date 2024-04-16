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
    private ProductsService productsService;

    @GetMapping("grupoalan/obtener-productos")
    public ResponseEntity<List<Products>> obtenerProductosBD() {
        logger.info("HORA DE OBTENER LOS PRODUCTOS DE NUESTRA BD");
        List<Products> products = productsService.getAllProductsWithDescriptions();
        if (products == null) {
            logger.error("No se pudieron obtener los productos de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
