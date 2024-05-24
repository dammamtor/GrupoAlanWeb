package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.*;
import grupoalan.backendgalan.model.response.makito.MarkingsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.MarkingTranslationsRepository;
import grupoalan.backendgalan.repository.MarkingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MarkingTranslationsService {
    static final Logger logger = LoggerFactory.getLogger(MarkingService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/markingsTranslations";

    @Autowired
    private MarkingTranslationsRepository markingTranslationsRepository;
    @Autowired
    private MarkingsRepository markingsRepository;

    public boolean makitoMarkingTranslationsFromApi(String apiToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("Iniciando llamada a la API con apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<StatusCode> response = restTemplate.exchange(
                    API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

            StatusCode statusCode = response.getBody();

            if (statusCode != null) {
                logger.info("Se recibió correctamente el StatusCode de la API");

                List<MarkingsMakito> markingsMakitos = statusCode.getMarkings();

                logger.info("Número de marcajes recibidos de la API: " + markingsMakitos.size());

                markingTranslationsRepository.deleteAll();
                logger.info("Se eliminaron todas las traducciones de marcajes existentes en la base de datos");

                List<MarkingsTranslations> markingsTranslationsList = new ArrayList<>();

                for (MarkingsMakito makito : markingsMakitos) {
                    if (makito.getLang() == 1 || makito.getLang() == 2) {
                        MarkingsTranslations marking = new MarkingsTranslations();
                        marking.setPrint_area_id(makito.getPrint_area_id());
                        marking.setLang(makito.getLang());
                        marking.setTxt(makito.getTxt());

                        markingsTranslationsList.add(marking);

                        logger.info("Se añadió traducción de marcaje con print_area_id: " + makito.getPrint_area_id() + ", lang: " + makito.getLang() + ", txt: " + makito.getTxt());
                    }
                }

                // Save all the generated MarkingsTranslations to the database
                markingTranslationsRepository.saveAll(markingsTranslationsList);
                logger.info("Se guardaron todas las nuevas traducciones de marcajes en la base de datos");

                // Call addMarkingsToTranslations to associate markings with translations
                addMarkingsToTranslations(markingsTranslationsList);

                logger.info("Se terminó de procesar las traducciones de marcajes de la API");
                return true;
            } else {
                logger.error("Error: el objeto StatusCode es nulo en la respuesta de la API");
                return false;
            }
        } catch (Exception e) {
            logger.error("Ocurrió una excepción mientras se llamaba a la API para obtener las traducciones de marcajes", e);
            return false;
        }
    }

    private void addMarkingsToTranslations(List<MarkingsTranslations> markingsTranslationsList) {
        logger.info("Iniciando addMarkingsToTranslations");

        for (MarkingsTranslations markingTranslation : markingsTranslationsList) {
            // Busca los Markings correspondientes por print_area_id
            List<Markings> matchingMarkings = markingsRepository.findByPrintAreaId(markingTranslation.getPrint_area_id());
            if (!matchingMarkings.isEmpty()) {
                // Convierte la lista de Markings a un conjunto
                Set<Markings> matchingMarkingsSet = new HashSet<>(matchingMarkings);
                // Establece la relación entre MarkingsTranslations y Markings
                markingTranslation.setMarkings(matchingMarkingsSet);
                // Guarda la relación en la base de datos
                markingTranslationsRepository.save(markingTranslation);
                logger.info("Se asociaron los markings con print_area_id: {} a la traducción de marcaje existente en la base de datos", markingTranslation.getPrint_area_id());

                // Establecer la relación inversa en los Markings
                for (Markings marking : matchingMarkings) {
                    // Obtener el MarkingsTranslations asociado a este Markings
                    marking.setMarkingsTranslations(markingTranslation);
                    logger.info("Se asoció la traducción de marcaje con print_area_id: {} al marking existente en la base de datos", markingTranslation.getPrint_area_id());
                }
            } else {
                logger.warn("No se encontraron markings correspondientes al print_area_id: {}", markingTranslation.getPrint_area_id());
            }
        }

        logger.info("Finalizando addMarkingsToTranslations");
    }

}
