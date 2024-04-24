package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.makito.DescriptionsMakito;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
import grupoalan.backendgalan.repository.DescriptionRepository;
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
import java.util.Optional;

@Service
public class DescriptionService {
    static final Logger logger = LoggerFactory.getLogger(DescriptionService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/descriptionext";

    private final DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public void saveDescription(Descriptions description) {
        descriptionRepository.save(description);
    }

    public Descriptions getDescriptionById(Long id) {
        return descriptionRepository.findById(id).orElse(null);
    }

    public List<Descriptions> getAllDescripctions(){
        return descriptionRepository.findAll();
    }

    // Otros métodos de servicio para operaciones relacionadas con descripciones
    public boolean makitoDescriptionsFromApi(String apiToken) {
        logger.info("ESTAS EN EL DESCRIPTIONS SERVICE");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<StatusCode> response = restTemplate.exchange(
                    API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

            StatusCode statusCode = response.getBody();

            if (statusCode != null) {
                List<DescriptionsMakito> descriptionsMakitos = statusCode.getDescriptions();
                logger.info("LISTA DE DESCRIPTIONS DE MAKITO: {}", descriptionsMakitos);

                // Eliminar todas las descripciones existentes antes de almacenar las nuevas
                descriptionRepository.deleteAll();

                List<Descriptions> descriptionsList = new ArrayList<>();

                // Filtrar y almacenar solo las descripciones con lang igual a "1" (español) o "2" (inglés)
                for (DescriptionsMakito descriptionsData : descriptionsMakitos) {
                    String lang = descriptionsData.getLang();
                    if ("1".equals(lang) || "2".equals(lang)) {
                        Descriptions description = new Descriptions();
                        description.setRef(descriptionsData.getRef());
                        description.setDetails(descriptionsData.getDesc());

                        description = descriptionRepository.save(description);
                        descriptionsList.add(description);
                        logger.info("Descripción añadida al producto: {}", description.getRef());
                    }
                }

                logger.info("Descripciones obtenidas de la API (lang 1 o 2): {}", descriptionsList);
                logger.info("Actualización de la lista de descripciones completada");
                return true;
            } else {
                logger.error("Error al obtener el objeto StatusCode de la respuesta");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error al llamar a la API para obtener las descripciones", e);
            return false;
        }
    }
}
