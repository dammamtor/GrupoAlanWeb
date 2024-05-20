package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.MarkingTechniques;
import grupoalan.backendgalan.model.response.makito.MarkingTechniquesMakito;
import grupoalan.backendgalan.model.response.makito.MarkingsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.MarkingTechniquesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkingTechniquesService {
    static final Logger logger = LoggerFactory.getLogger(MarkingTechniquesService.class);
    @Autowired
    private RestTemplate restTemplate;
    private static final String API_URL = "https://data.makito.es/api/markingTechniques";

    @Autowired
    private MarkingTechniquesRepository markingTechniquesRepository;

    // Método para encontrar una tecnica por su ID
    public MarkingTechniques getTechniqueByID(Long id) {
        return markingTechniquesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las tecnicas
    public List<MarkingTechniques> listaTecnicas() {
        return markingTechniquesRepository.findAll();
    }

    // Método para guardar una nueva tecnica
    public MarkingTechniques guardarTecnica(MarkingTechniques techniqueSave) {
        return markingTechniquesRepository.save(techniqueSave);
    }

    // Método para eliminar una tecnica por su ID
    void deleteById(Long id) {
        markingTechniquesRepository.deleteById(id);
    }

    ;

    //OTROS METODOS
    public boolean makitoMarkingTechniquesFromApi(String apiToken) {
        logger.info("ESTAS EN EL MARKING TECHNIQUES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            List<MarkingTechniquesMakito> markingTechniquesMakitosList = statusCode.getTechniques();
            logger.info("FUNKA LA LISTA");

            markingTechniquesRepository.deleteAll();

            for (MarkingTechniquesMakito makito : markingTechniquesMakitosList) {
                MarkingTechniques markingTechnique1 = new MarkingTechniques();
                markingTechnique1.setName(makito.getName());
                markingTechnique1.setRef(makito.getRef());
                markingTechnique1.setTechnique_ref(markingTechnique1.getTechnique_ref());
                markingTechniquesRepository.save(markingTechnique1);
            }
            // Agregar un registro de la lista guardada
            List<MarkingTechniques> savedMarkingTechniques = markingTechniquesRepository.findAll();
            for (MarkingTechniques savedTechnique : savedMarkingTechniques) {
                logger.info("Técnica de marcaje guardada: " + savedTechnique);
            }

            logger.info("Terminado el proceso de recoleccion");
            return true;
        } else {
            System.err.println("No se pudo obtener el objeto StatusCode de la respuesta.");
            return false;
        }
    }

    //MARKING TECHNIQUES LISTA
    public List<String> listaTecnicaMarcajeUnico() {
        List<MarkingTechniques> markingTechniquesList = markingTechniquesRepository.findAll();
        List<String> markingTechniquesUnique = new ArrayList<>();

        for (MarkingTechniques markingTechnique1 : markingTechniquesList) {
            String name = markingTechnique1.getName();
            String firstWord = name.split(" ")[0]; // Obtiene la primera palabra de la cadena
            // Elimina caracteres especiales como "-"
            firstWord = firstWord.replaceAll("[^a-zA-Z]", "");
            if (!markingTechniquesUnique.contains(firstWord)) { // Verifica si ya está en la lista
                markingTechniquesUnique.add(firstWord);
            }
        }

        return markingTechniquesUnique;
    }


}