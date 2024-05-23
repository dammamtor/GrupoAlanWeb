package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.model.MarkingTechniques;
import grupoalan.backendgalan.model.Variants;
import grupoalan.backendgalan.model.response.makito.MarkingTechniquesMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.makito.VariantsMakito;
import grupoalan.backendgalan.repository.ColorRepository;
import grupoalan.backendgalan.repository.VariantsRepository;
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
public class VariantsService {
    static final Logger logger = LoggerFactory.getLogger(VariantsService.class);
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://data.makito.es/api/variants";
    @Autowired
    private VariantsRepository variantsRepository;
    @Autowired
    private ColorRepository colorRepository;

    // Método para encontrar una variante por su ID
    public Variants getVariantByID(Long id){
        return variantsRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las variantes
    public List<Variants> getAllVariants(){
        return variantsRepository.findAll();
    }

    // Método para guardar una nueva variante
    public Variants saveVariant(Variants variant){
        return variantsRepository.save(variant);
    }

    // Método para eliminar una variante por su ID
    public void deleteVariantByID(Long id){
        variantsRepository.deleteById(id);
    }

    //OTROS METODOS
    public boolean makitoVariantsTechniquesFromApi(String apiToken) {
        logger.info("ESTAS EN EL MARKING TECHNIQUES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            List<VariantsMakito> variantsMakitos = statusCode.getVariants();
            List<Variants> variantsGalan = new ArrayList<>();

            for (VariantsMakito makito : variantsMakitos) {
                Variants galan = variantsRepository.findByUniqueRef(makito.getUnique_ref());
                if (galan == null) {
                    galan = new Variants();
                }
                galan.setMatnr(makito.getMatnr());
                galan.setRef(makito.getRef());
                galan.setUnique_ref(makito.getUnique_ref());
                galan.setColor(makito.getColor());
                galan.setSize(makito.getSize());
                galan.setImg100(makito.getImg100());

                logger.info("Variante de producto: " + galan.getRef() + " incluida o actualizada");
                galan = variantsRepository.save(galan);
                variantsGalan.add(galan);
            }

            addColorsToVariants(variantsGalan);

            logger.info("Products obtenidas de la API: " + variantsGalan);
            logger.info("Actualización de la lista de variants completada");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }

    private void addColorsToVariants(List<Variants> variants) {
        logger.info("Iniciando addColorsToVariants");

        for (Variants variant : variants) {
            List<Colors> colorsList = colorRepository.findByCode(variant.getColor());
            if (!colorsList.isEmpty()) {
                Colors selectedColor = colorsList.get(0);  // Seleccionar el primer color encontrado, o agregar lógica adicional si es necesario
                variant.setColorSet(selectedColor);
                variantsRepository.save(variant);
                logger.info("Color asociado a la variante {}: {}", variant.getRef(), selectedColor.getName());
            } else {
                logger.warn("No se encontró el color para la variante: {}", variant.getRef());
            }
        }

        logger.info("Finalizando addColorsToVariants");
    }

}
