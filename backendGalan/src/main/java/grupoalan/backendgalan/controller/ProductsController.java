package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde localhost:4200
@RequestMapping("/grupo-alan/productos")
public class ProductsController {
    static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductsService productsService;

    @GetMapping("grupoalan/obtener-productos")
    public ResponseEntity<List<Products>> obtenerProductosBD() {
        logger.info("HORA DE OBTENER LOS PRODUCTOS DE NUESTRA BD");
        List<Products> products = productsService.getAllProducts();
        if (products == null) {
            logger.error("No se pudieron obtener los productos de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("LISTA DEVUELTA: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/obtener-producto/{ref}")
    public ResponseEntity<Products> obtenerProductoPorRef(
            @PathVariable("ref") String ref
    ) {
        logger.info("HORA DE OBTENER UN PRODUCTO DE NUESTRA BD POR SU REFERENCIA");
        Products product = productsService.getProductByRef(ref);
        if (product == null) {
            logger.error("No se pudo encontrar el producto con la referencia: " + ref);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PRODUCTO DEVUELTO: " + product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
