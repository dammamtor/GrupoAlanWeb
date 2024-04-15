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
    public MarkingTechniques getTechniqueByID(Long id){
        return markingTechniquesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las tecnicas
    public List<MarkingTechniques> listaTecnicas(){
        return markingTechniquesRepository.findAll();
    }

    // Método para guardar una nueva tecnica
    public MarkingTechniques guardarTecnica(MarkingTechniques techniqueSave){
        return markingTechniquesRepository.save(techniqueSave);
    }

    // Método para eliminar una tecnica por su ID
    void deleteById(Long id){
        markingTechniquesRepository.deleteById(id);
    };

    //OTROS METODOS
    public List<MarkingTechniques> makitoMarkingTechniquesFromApi(String apiToken){
        logger.info("ESTAS EN EL MARKING TECHNIQUES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode!= null){
            List<MarkingTechniquesMakito> markingTechniquesMakitos = statusCode.getTechniques();
            logger.info("LISTA DE MARKING TECHNIQUES DE MAKITO: " + markingTechniquesMakitos);
        }
        return null;
    }
}
