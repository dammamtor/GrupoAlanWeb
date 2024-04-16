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
    public boolean makitoDescriptionsFromApi(String apiToken){
        logger.info("ESTAS EN EL DESCRIPTIONS SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode!= null){
            List<DescriptionsMakito> descriptionsMakitos = statusCode.getDescriptions();
            logger.info("LISTA DE DESCRIPTIONS DE MAKITO: " + descriptionsMakitos);

            // Eliminar todas las descripciones existentes antes de almacenar las nuevas
            descriptionRepository.deleteAll();

            List<Descriptions> descriptionsList = new ArrayList<>();

            for (DescriptionsMakito descriptionsData : descriptionsMakitos) {
                Descriptions description1 = new Descriptions();
                description1.setRef(descriptionsData.getRef());
                description1.setDetails(descriptionsData.getDesc());

                description1 = descriptionRepository.save(description1);
                descriptionsList.add(description1);
            }

            logger.info("Descripciones obtenidas de la API: " + descriptionsList);

            logger.info("Actualización de la lista de descripciones completada");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }
}
