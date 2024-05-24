package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Images;
import grupoalan.backendgalan.model.Markings;
import grupoalan.backendgalan.model.response.makito.DescriptionsMakito;
import grupoalan.backendgalan.model.response.makito.MarkingsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.ImagesRepository;
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
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkingService {
    static final Logger logger = LoggerFactory.getLogger(MarkingService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/markings";
    private static final String API_URL_2 = "https://data.makito.es/api/markingsTranslations";

    @Autowired
    private MarkingsRepository markingRepository;
    @Autowired
    private ImagesRepository imagesRepository;

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

            markingRepository.deleteAll();
            List<Markings> markingsList = new ArrayList<>();

            for (MarkingsMakito makito : markingsMakitos) {
                String areaImg = makito.getArea_img();
                logger.info("Procesando areaImg: " + areaImg);

                if (areaImg != null && !areaImg.isEmpty()) {
                    String imageName = areaImg.substring(areaImg.lastIndexOf("/") + 1); // Obtener el nombre de la imagen con la extensión
                    logger.info("Buscando imagen con nombre exacto: " + imageName);

                    // Buscar imagen en la base de datos que termine exactamente con el nombre de archivo extraído
                    Images image = imagesRepository.findByImgMaxEndingWithExact(imageName);
                    if (image != null) {
                        // Crear un nuevo Markings basado en MarkingsMakito y la imagen encontrada
                        Markings marking = new Markings();
                        marking.setRef(makito.getRef());
                        marking.setTechniqueRef(makito.getTechnique_ref());
                        marking.setPrintAreaId(makito.getPrint_area_id());
                        marking.setMax_colors(makito.getMax_colors());
                        marking.setPosition(makito.getPosition());
                        marking.setWidth(makito.getWidth());
                        marking.setHeight(makito.getHeight());
                        marking.setArea_img(image.getImgMax());

                        markingsList.add(marking);

                        logger.info("Imagen encontrada en la base de datos: " + marking.getArea_img());
                    } else {
                        logger.error("No se encontró una imagen en la base de datos para: " + imageName);
                    }
                } else {
                    logger.error("El campo area_img está vacío o es nulo para el objeto: " + makito);
                }
            }

            // Guardar los Markings generados en la base de datos
            markingRepository.saveAll(markingsList);
            logger.info("TERMINADO DE OBTENER LOS MARKINGS DE MAKITO");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }

    //AÑADIR ELEMENTOS DEL MARKING TRANSLATIONS A MARKINGS GALAN
//    public void addMarkingsTranslations(String apiToken, List<Markings> markingsList) {
//        logger.info("Hora de añadir los elementos faltantes de markings");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiToken);
//
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<StatusCode> response = restTemplate.exchange(
//                    API_URL_2, HttpMethod.GET, requestEntity, StatusCode.class);
//
//            StatusCode statusCode = response.getBody();
//            if (statusCode != null) {
//                List<MarkingsMakito> markingsMakitosList = statusCode.getMarkings();
//                if (markingsMakitosList != null) {
//                    // Iterar sobre las descripciones almacenadas
//                    for (Markings galan : markingsList) {
//                        int printAreaId = galan.getPrint_area_id();
//                        // Buscar el material con el ref correspondiente
//                        for (MarkingsMakito makito : markingsMakitosList) {
//                            if (printAreaId == makito.getPrint_area_id()) {
//                                galan.setLang(makito.getLang());
//                                galan.setTxt(makito.getTxt());
//
//                                markingRepository.save(galan);
//                                break; // No es necesario continuar buscando para esta descripción
//                            }
//                        }
//                    }
//                    logger.info("TERMINADO");
//                } else {
//                    logger.error("La lista markingsMakitosList es nula");
//                }
//            } else {
//                logger.error("Error al obtener el objeto StatusCode de la respuesta");
//            }
//        } catch (Exception e) {
//            logger.error("Error al llamar a la API para obtener las composiciones", e);
//        }
//    }

}