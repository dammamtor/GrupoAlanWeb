package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Stock;
import grupoalan.backendgalan.model.Variants;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.makito.StockMakito;
import grupoalan.backendgalan.model.response.makito.VariantsMakito;
import grupoalan.backendgalan.repository.StockRepository;
import grupoalan.backendgalan.repository.VariantsRepository;
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

@Service
public class StockService {
    static final Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private VariantsRepository variantsRepository;

    private static final String API_URL = "https://data.makito.es/api/stock";

    public boolean makitoStockFromApi(String apiToken) {
        logger.info("Entrando en el servicio de stock");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        logger.info("Token de API: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        logger.info("Enviando solicitud a la API de Makito");
        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        logger.info("Respuesta recibida de la API");
        StatusCode statusCode = response.getBody();

        if (statusCode != null) {
            logger.info("StatusCode recibido de la API: " + statusCode);

            List<StockMakito> stockMakito = statusCode.getStock();
            List<Stock> stockGalan = new ArrayList<>();

            logger.info("Procesando el stock recibido");
            for (StockMakito makito : stockMakito) {
                logger.info("Procesando el artículo: " + makito.getUnique_ref());

                Stock galan = stockRepository.findByUniqueRefAndMatnr(makito.getUnique_ref(), makito.getMatnr());
                if (galan == null) {
                    galan = new Stock();
                    logger.info("Nuevo stock creado para el artículo: " + makito.getUnique_ref());
                }

                // Establecer detalles del stock
                galan.setMatnr(makito.getMatnr());
                galan.setRef(makito.getRef());
                galan.setUniqueRef(makito.getUnique_ref());
                galan.setStockAvailability(makito.getStock_availability());
                galan.setStock(makito.getStock());
                galan.setDate(makito.getDate());

                // Obtener la variante correspondiente
                Variants variant = variantsRepository.findByRefAndMatnr(makito.getRef(), makito.getMatnr());
                if (variant != null) {
                    // Establecer las relaciones
                    galan.setVariants(variant);
                    variant.setStock(galan);

                    // Guardar el stock
                    galan = stockRepository.save(galan);
                    stockGalan.add(galan);
                    logger.info("Stock guardado para la referencia: " + makito.getRef() + " y matnr: " + makito.getMatnr());
                } else {
                    logger.error("Variante no encontrada para la referencia: " + makito.getRef() + " y matnr: " + makito.getMatnr());
                }
            }

            logger.info("Stock obtenido de la API: " + stockGalan);
            logger.info("Actualizando el stock obtenido de Makito");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }

}
