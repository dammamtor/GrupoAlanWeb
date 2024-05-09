package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.model.response.makito.ColorsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
import grupoalan.backendgalan.repository.ColorRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ColorService {
    static final Logger logger = LoggerFactory.getLogger(ColorService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/colors";
    private static final String API_URL_ROLY_ES = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=es-ES&brand=roly";
    private static final String API_URL_ROLY_ENG = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=en-GB&brand=roly";

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public void saveColor(Colors color) {
        colorRepository.save(color);
    }

    public Colors getColorById(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

    // Otros métodos de servicio para operaciones relacionadas con colores
    public boolean makitoColorsFromApi(String apiToken) {
        logger.info("ESTAS EN EL COLOR  SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            List<ColorsMakito> colorsMakitos = statusCode.getColors();
            List<Colors> colorsList = new ArrayList<>();

            logger.info("LISTA DE COLORES: " + colorsMakitos);

            colorRepository.deleteAll();

            for (ColorsMakito makito : colorsMakitos) {
                if (makito.getLang() == 1 || makito.getLang() == 2) { // Solo guarda colores con lang igual a 1 o 2
                    Colors color1 = new Colors();
                    color1.setCode(makito.getColor_code());
                    color1.setName(makito.getName());
                    color1.setUrl(makito.getUrl());
                    color1.setLang(makito.getLang());
                    color1 = colorRepository.save(color1);

                    colorsList.add(color1);
                }
            }
            logger.info("Colores obtenidas de la API: " + colorsList);
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }

    public List<String> listaColoresUnicos() {
        List<Colors> colors = colorRepository.findAll();
        return colors.stream()
                .filter(color -> color.getLang() != null && color.getLang() == 1) // Filtrar por lang igual a 1
                .map(Colors::getName)
                .distinct() // Filtrar elementos duplicados
                .collect(Collectors.toList());
    }

    public boolean rolyColorsFromApi(String apiToken) {
        logger.info("ESTAS EN EL COLOR SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Para obtener los productos en español
        try {
            ResponseEntity<Items> responseES = restTemplate.exchange(
                    API_URL_ROLY_ES, HttpMethod.GET, requestEntity, Items.class);

            Items itemsES = responseES.getBody();

            // Procesar productos en español
            processProducts(itemsES, "español", new HashSet<>());
        } catch (Exception e) {
            logger.error("Error al obtener productos en español: " + e.getMessage());
            return false;
        }

        // Para obtener los productos en inglés
        try {
            ResponseEntity<Items> responseENG = restTemplate.exchange(
                    API_URL_ROLY_ENG, HttpMethod.GET, requestEntity, Items.class);

            Items itemsENG = responseENG.getBody();

            // Procesar productos en inglés
            processProducts(itemsENG, "inglés", new HashSet<>());
        } catch (Exception e) {
            logger.error("Error al obtener productos en inglés: " + e.getMessage());
            return false;
        }

        return true;
    }


    private void processProducts(Items items, String language, Set<String> colorCodes) {
        if (items != null && items.getItem() != null) {
            List<ProductsRoly> products = items.getItem();
            for (ProductsRoly product : products) {
                // Verificar si el código de color ya ha sido procesado
                if (!colorCodes.contains(product.getColorcode())) {
                    // Si no se ha procesado, guardar el color en la base de datos
                    Colors color = new Colors();
                    color.setCode(product.getColorcode());
                    color.setName(product.getColorname());
                    colorRepository.save(color);

                    // Registrar el código de color para evitar repeticiones
                    colorCodes.add(product.getColorcode());

                    logger.info("Añadido el color " + product.getColorname() + " (" + product.getColorcode() + ") en " + language);
                }
            }
        }
    }
}
