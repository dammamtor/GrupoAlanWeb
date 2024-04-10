package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/sync/categories")
    public ResponseEntity<List<Categories>> syncCategories() {
        List<Categories> categories = categoriesService.syncCategoriesFromApi();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
