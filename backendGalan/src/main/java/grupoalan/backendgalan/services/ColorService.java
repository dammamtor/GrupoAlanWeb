package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.model.response.makito.ColorsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
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
import java.util.List;

@Service
public class ColorService {
    static final Logger logger = LoggerFactory.getLogger(ColorService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/colors";

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
                Colors color1 = new Colors();
                color1.setCode(makito.getColor_code());
                color1.setName(makito.getName());
                color1.setUrl(makito.getUrl());
                color1 = colorRepository.save(color1);

                colorsList.add(color1);
            }
            logger.info("Colores obtenidas de la API: " + colorsList);
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }
}
