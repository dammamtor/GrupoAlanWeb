package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.services.ColorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/grupo-alan/colors")
public class ColorController {
    static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired
    private ColorService colorService;

    @GetMapping("/list-colors")
    public ResponseEntity<List<String>> getColorsWithProductCount() {
        List<String> colorsWithProductCount = colorService.getColorsWithProductCount();
        return ResponseEntity.ok().body(colorsWithProductCount);
    }
}
