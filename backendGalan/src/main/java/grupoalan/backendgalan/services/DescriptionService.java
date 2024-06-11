package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.makito.DescriptionsMakito;
import grupoalan.backendgalan.model.response.makito.DescriptionsMaterial;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DescriptionService {
    static final Logger logger = LoggerFactory.getLogger(DescriptionService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/descriptionext";
    private static final String API_URL_2 = "https://data.makito.es/api/descriptions";
    private static final String API_URL_ROLY_ES = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=es-ES&brand=roly";
    private static final String API_URL_ROLY_ENG = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=en-GB&brand=roly";

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

    public List<Descriptions> getAllDescripctions() {
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
                        description.setLang(descriptionsData.getLang());
                        description = descriptionRepository.save(description);
                        descriptionsList.add(description);
                        logger.info("Descripción añadida: {}", description);
                    }
                }

                logger.info("Descripciones obtenidas de la API (lang 1 o 2): {}", descriptionsList);

                // Llamar al método addDescriptionsComposition después de haber almacenado todas las descripciones
                addDescriptionsComposition(apiToken, descriptionsList);

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

    //ESTE METODO ES PARA AÑADIR TANTO COMP COMO TYPE
    public void addDescriptionsComposition(String apiToken, List<Descriptions> descriptionsList) {
        logger.info("HORA DE AÑADIR COMP");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<StatusCode> response = restTemplate.exchange(
                    API_URL_2, HttpMethod.GET, requestEntity, StatusCode.class);

            StatusCode statusCode = response.getBody();
            if (statusCode != null) {
                List<DescriptionsMakito> descriptionsMaterialList = statusCode.getDescriptions();
                // Iterar sobre las descripciones almacenadas
                for (Descriptions description : descriptionsList) {
                    String ref = description.getRef();
                    // Buscar el material con el ref correspondiente
                    for (DescriptionsMakito material : descriptionsMaterialList) {
                        if (ref.equals(material.getRef())) {
                            // Si se encuentra el ref, establecer la composición en la descripción
                            description.setComp(material.getComp());
                            description.setType(material.getType());
                            // Guardar la descripción actualizada en la base de datos
                            descriptionRepository.save(description);
                            logger.info("Composición añadida a la descripción: {}", description);
                            logger.info("Type añadido a la descripción: {}", description);
                            break; // No es necesario continuar buscando para esta descripción
                        }
                    }
                }
                logger.info("TERMINADO");
            } else {
                logger.error("Error al obtener el objeto StatusCode de la respuesta");
            }
        } catch (Exception e) {
            logger.error("Error al llamar a la API para obtener las composiciones", e);
        }
    }

    public List<String> listaMateriales() {
        List<Descriptions> listaMateriales = descriptionRepository.findAll();
        Set<String> materiales = new HashSet<>();

        for (Descriptions descripcion : listaMateriales) {
            String type = descripcion.getType();
            if (!type.isEmpty()) {
                materiales.add(type); // Agregar la descripción completa al conjunto
            }
        }

        return new ArrayList<>(materiales);
    }

    public List<String> listaTipos() {
        List<Descriptions> listaTipos = descriptionRepository.findAll();
        List<String> listaTiposUnicos = new ArrayList<>();

        for (Descriptions descripcion : listaTipos) {
            String type = descripcion.getType();
            if (type != null) {
                if (!listaTiposUnicos.contains(type)) {
                    listaTiposUnicos.add(type);
                }
            }
        }
        return listaTiposUnicos;
    }

    public boolean rolyDescriptionsFromApi(String apiToken) {
        logger.info("ESTAS EN EL PRODUCTS SERVICE");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // Para obtener los productos en español
            ResponseEntity<Items> responseES = restTemplate.exchange(
                    API_URL_ROLY_ES, HttpMethod.GET, requestEntity, Items.class);

            Items itemsES = responseES.getBody();

            // Para obtener los productos en inglés
            ResponseEntity<Items> responseENG = restTemplate.exchange(
                    API_URL_ROLY_ENG, HttpMethod.GET, requestEntity, Items.class);

            Items itemsENG = responseENG.getBody();

            // Eliminar todas las descripciones existentes antes de almacenar las nuevas
            descriptionRepository.deleteAll();

            // Procesamiento tanto para productos en español como en inglés
            processProducts(itemsES, "español");
            processProducts(itemsENG, "inglés");

            return true;
        } catch (RestClientException ex) {
            logger.error("Error al llamar a la API: " + ex.getMessage());
            return false;
        }
    }

    // Método para procesar los productos
    private void processProducts(Items items, String language) {
        if (items != null && items.getItem() != null) {
            List<ProductsRoly> products = items.getItem();
            for (ProductsRoly product : products) {
                Descriptions descriptions = new Descriptions();
                descriptions.setRef(product.getItemcode());
                descriptions.setDetails(product.getDescription());

                descriptionRepository.save(descriptions);
                logger.info("Añadida la descripción al producto " + descriptions.getRef() + " en " + language + ": " + descriptions.getDetails());
            }
        }
    }
}
