package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Images;
import grupoalan.backendgalan.model.response.makito.ImagesMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.ImagesRepository;
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
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {
    static final Logger logger = LoggerFactory.getLogger(ImagesService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImagesRepository imagesRepository;

    private static final String API_URL = "https://data.makito.es/api/images";

    public boolean makitoImagesFromApi(String apiToken) {
        logger.info("ESTAS EN EL IMAGES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            List<ImagesMakito> imagesMakitoList = statusCode.getImages();
            List<Images> imagesGAlan = new ArrayList<>();

            // Eliminar imágenes existentes antes de guardar nuevas imágenes
            imagesRepository.deleteAll(); // Esto borra todas las imágenes de la base de datos

            for (ImagesMakito image : imagesMakitoList) {
                Images image1 = new Images();

                image1.setRef(image.getRef());
                image1.setImg_min(image.getImg_min());
                image1.setImg_max(image.getImg_max());

                image1 = imagesRepository.save(image1);
                imagesGAlan.add(image1);
            }

            logger.info("Products obtenidas de la API: " + imagesGAlan);
            logger.info("Actualización de la lista de productos completada");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }
}
