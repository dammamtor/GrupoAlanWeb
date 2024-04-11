package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class CategoriesController {
    static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/sync/categories")
    public ResponseEntity<List<Categories>> syncCategories() {
        logger.info("HAZ HECHO UNA PETICION A LISTA DE CATEGORIAS");
        List<Categories> categories = categoriesService.syncCategoriesFromApi();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
