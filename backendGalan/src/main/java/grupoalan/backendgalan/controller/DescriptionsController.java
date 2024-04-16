package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.services.DescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DescriptionsController {
    static final Logger logger = LoggerFactory.getLogger(DescriptionsController.class);

    @Autowired
    private DescriptionService descriptionService;

    @GetMapping("grupoalan/obtener-descripciones")
    public ResponseEntity<List<Descriptions>> obtenerDescripcionesEnBD(){
        logger.info("HORA DE OBTENER LAS DESCRIPCIONES DE NUESTRA BD");
        List<Descriptions> descriptions = descriptionService.getAllDescripctions();
        if (descriptions == null) {
            logger.error("No se pudieron obtener los descriptions de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(descriptions, HttpStatus.OK);
    }


}
