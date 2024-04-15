package grupoalan.backendgalan.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class APITokenService {
    static final Logger logger = LoggerFactory.getLogger(APITokenService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public String getApiToken() {
        logger.info("Obteniendo token de la API externa...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "ventas@serigrafiaensevilla.com");
        requestBody.put("password", "Mk2431169to");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://data.makito.es/api/login", HttpMethod.POST, requestEntity, String.class);

        // Verificar si la solicitud fue exitosa y obtener el token del cuerpo de la respuesta
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Token obtenido correctamente.");
            String responseBody = response.getBody();
            logger.info("Respuesta del login: " + responseBody);

            JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(responseBody);
                int status = rootNode.get("status").asInt();
                if (status == 1) {
                    String token = rootNode.get("msg").asText();
                    return token;
                } else {
                    logger.error("La solicitud para obtener el token falló con el mensaje: " + rootNode.get("msg").asText());
                    return null;
                }
            } catch (JsonProcessingException e) {
                logger.error("Error al procesar la respuesta JSON para obtener el token", e);
                return null;
            }
        } else {
            logger.error("La solicitud para obtener el token falló con el código de estado: " + response.getStatusCodeValue());
            return null;
        }
    }

    public String getApiRolyToken() {
        logger.info("Obteniendo token de la API externa...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", "ventas@serigrafiaensevilla.com");
        requestBody.add("password", "Luneando1978@");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://clientsws.gorfactory.es:2096/api/v1.0/login", HttpMethod.POST, requestEntity, String.class);

        // Verificar si la solicitud fue exitosa y obtener el token del cuerpo de la respuesta
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Token obtenido correctamente.");

            // Parsear el token del cuerpo de la respuesta
            String responseBody = response.getBody();
            JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(responseBody);
                String token = rootNode.get("token").asText();
                return token;
            } catch (JsonProcessingException e) {
                logger.error("Error al procesar la respuesta JSON para obtener el token", e);
                return null;
            }
        } else {
            logger.error("La solicitud para obtener el token falló con el código de estado: " + response.getStatusCodeValue());
            return null;
        }
    }

}
