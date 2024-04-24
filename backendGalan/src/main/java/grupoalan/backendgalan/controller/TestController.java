package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ProductsService productsService;

    @GetMapping("test-grupoalan/obtener-productos")
    public ResponseEntity<Products> obtenerDatosProductoID(){
        logger.info("TEST. OBTENER DATOS PRODUCTO");
        Products product1 = productsService.getDataProductID();
        if (product1 == null) {
            logger.error("FALLO EL TEST");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }
}
