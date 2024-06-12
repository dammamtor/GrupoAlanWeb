package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Prices;
import grupoalan.backendgalan.model.Stock;
import grupoalan.backendgalan.model.Variants;
import grupoalan.backendgalan.model.response.makito.PricesMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.makito.StockMakito;
import grupoalan.backendgalan.repository.PricesRepository;
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
public class PricesService {
    static final Logger logger = LoggerFactory.getLogger(PricesService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PricesRepository pricesRepository;

    private static final String API_URL = "https://data.makito.es/api/prices";

    public boolean makitoPricesFromApi(String apiToken) {
        logger.info("Entrando en el servicio de precios");

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
            pricesRepository.deleteAll();

            List<PricesMakito> pricesMakito = statusCode.getTarifs();
            List<Prices> pricesGalan = new ArrayList<>();

            logger.info("Procesando los precios recibidos");

            for (PricesMakito makito : pricesMakito) {
                Prices galan = new Prices();

                galan.setRef(makito.getRef());
                galan.setSection1(makito.getSection_1());
                galan.setPrice1(makito.getPrice_1());
                galan.setSection2(makito.getSection_2());
                galan.setPrice2(makito.getPrice_2());
                galan.setSection3(makito.getSection_3());
                galan.setPrice3(makito.getPrice_3());
                galan.setSection4(makito.getSection_4());
                galan.setPrice4(makito.getPrice_4());
                galan.setSection5(makito.getSection_5());
                galan.setPrice5(makito.getPrice_5());
                galan.setSection6(makito.getSection_6());
                galan.setPrice6(makito.getPrice_6());

                galan = pricesRepository.save(galan);
                pricesGalan.add(galan);

                // Registro de cada iteración
                logger.info("Precio procesado y guardado: " + galan);
            }

            logger.info("Precios obtenidos de la API: " + pricesGalan);
            logger.info("Actualizado los precios de Makito");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }


}
