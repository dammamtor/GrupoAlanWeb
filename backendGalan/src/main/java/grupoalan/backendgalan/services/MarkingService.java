package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Markings;
import grupoalan.backendgalan.model.response.makito.DescriptionsMakito;
import grupoalan.backendgalan.model.response.makito.MarkingsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.MarkingsRepository;
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
public class MarkingService {
    static final Logger logger = LoggerFactory.getLogger(MarkingService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/markings";

    private final MarkingsRepository markingRepository;

    @Autowired
    public MarkingService(MarkingsRepository markingRepository) {
        this.markingRepository = markingRepository;
    }

    public void saveMarking(Markings marking) {
        markingRepository.save(marking);
    }

    public Markings getMarkingById(Long id) {
        return markingRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con marcados
    public boolean makitoMarkingsFromApi(String apiToken) {
        logger.info("ESTAS EN EL MARKINGS SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            List<MarkingsMakito> markingsMakitos = statusCode.getMarkings();
            logger.info("LISTA DE MARKINGS DE MAKITO: " + markingsMakitos);

            markingRepository.deleteAll();
            List<Markings> markingsList = new ArrayList<>();

            for (MarkingsMakito makito : markingsMakitos) {
                //esto mejor verlo el viernes
            }

            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }
}
