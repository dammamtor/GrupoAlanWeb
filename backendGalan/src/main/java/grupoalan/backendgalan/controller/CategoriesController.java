package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde localhost:4200
public class CategoriesController {
    static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @Autowired
    private CategoriesService categoriesService;
    @GetMapping("grupoalan/obtener-categorias")
    public ResponseEntity<List<Categories>> obtenerCategoriasEnBD(){
        logger.info("HORA DE OBTENER LAS DESCRIPCIONES DE NUESTRA BD");
        List<Categories> categories = categoriesService.listaCategories();
        if (categories == null) {
            logger.error("No se pudieron obtener las categorias de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("grupoalan/obtener-categorias/{id}")
    public ResponseEntity<Categories> obtenerCategoriaPorId(@PathVariable Long id) {
        logger.info("Obteniendo categoría con ID: {}", id);
        Categories category = categoriesService.getCategoryByID(id);
        if (category == null) {
            logger.error("No se encontró ninguna categoría con el ID proporcionado: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @GetMapping("grupoalan/obtener-categorias-unicas")
    public ResponseEntity<List<String>> obtenerCategoriasUnicasEnBD() {
        logger.info("Obteniendo lista de categorías únicas con conteo desde la BD");
        List <String> categoriasConConteo = categoriesService.listaCategoriasUnicasConConteo();

        return new ResponseEntity<>(categoriasConConteo, HttpStatus.OK);
    }

}
