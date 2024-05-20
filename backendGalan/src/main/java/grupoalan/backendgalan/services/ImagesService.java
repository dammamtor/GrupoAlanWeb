package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Images;
import grupoalan.backendgalan.model.response.makito.ImagesMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
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
    private static final String API_URL_ROLY = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=es-ES&brand=roly";

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
                image1.setImgMin(image.getImg_min());
                image1.setImgMax(image.getImg_max());
                image1.setMain(image.getMain());

                logger.info("Producto : " + image.getRef() + " , campo main: " + image.getMain());
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
    public boolean rolyImagesFromApi(String apiToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Items> responseES = restTemplate.exchange(
                    API_URL_ROLY, HttpMethod.GET, requestEntity, Items.class);

            Items items = responseES.getBody();

            if (items != null && items.getItem() != null) {
                List<ProductsRoly> products = items.getItem();
                for (ProductsRoly product : products) {
                    String ref = product.getItemcode();

                    // Verificar si existe una imagen con el mismo 'ref'
                    List<Images> existingImages = imagesRepository.findByRef(ref);
                    if (!existingImages.isEmpty()) {
                        // Si existe, obtenemos la primera imagen de la lista y la eliminamos
                        Images existingImage = existingImages.get(0);
                        imagesRepository.delete(existingImage);
                        logger.info("Imagen del producto " + ref + " eliminada antes de guardar la nueva.");
                    }


                    // Crear y guardar la nueva imagen
                    Images image = new Images();
                    image.setRef(ref);
                    image.setRef(product.getItemcode());
                    image.setProductImage(product.getProductimage());
                    image.setModelImage(product.getModelimage());
                    image.setChildImage(product.getChildimage());
                    image.setDetailsImages(product.getDetailsimages());
                    image.setViewsImages(product.getViewsimages());
                    image.setOtherImages(product.getOtherimages());

                    imagesRepository.save(image);
                    logger.info("Imagenes del producto " + image.getRef() + ", guardadas");
                }
            }
            logger.info("LISTA DE IMAGENES DE ROLU OK");
            return true;
        } catch (Exception e) {
            logger.error("Error al obtener las imagenes de Roly: " + e.getMessage());
            return false;
        }
    }
}
