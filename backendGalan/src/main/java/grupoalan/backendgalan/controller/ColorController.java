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

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/grupo-alan/colors")
public class ColorController {
    static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired
    private ColorService colorService;

    @GetMapping("/list-colors")
    public ResponseEntity<List<String>> obtenerColoresenBD() {
        logger.info("HORA DE OBTENER LAS DESCRIPCIONES DE NUESTRA BD");
        List<String> colors = colorService.listaColoresUnicos();
        if (colors.isEmpty()) {
            logger.error("No se pudieron obtener los colores de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }


}
