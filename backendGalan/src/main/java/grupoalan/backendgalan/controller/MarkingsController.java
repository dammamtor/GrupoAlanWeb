package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.repository.MarkingsRepository;
import grupoalan.backendgalan.services.MarkingTechniquesService;
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
@RequestMapping("/grupo-alan/markings")
public class MarkingsController {

    static final Logger logger = LoggerFactory.getLogger(MarkingsController.class);

    @Autowired
    private MarkingTechniquesService markingTechniquesService;

    @GetMapping("/list-markingtechniques")
    public ResponseEntity<List<String>> obtenerMarkingTechniquesenBD() {
        logger.info("HORA DE OBTENER LOS MARKING TECHNIQUES DE NUESTRA BD");
        List<String> markingTechniques = markingTechniquesService.listaTecnicaMarcajeUnico();

        return new ResponseEntity<>(markingTechniques, HttpStatus.OK);
    }
}
