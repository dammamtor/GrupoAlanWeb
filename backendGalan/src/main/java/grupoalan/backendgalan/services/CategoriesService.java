package grupoalan.backendgalan.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.controller.CategoriesController;
import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.response.CategoryResponse;
import grupoalan.backendgalan.repository.CategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoriesService {
    static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.token}")
    private String apiToken;
    private final ObjectMapper objectMapper;

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String API_URL = "https://data.makito.es/api/categories";

    public CategoriesService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Categories> syncCategoriesFromApi() {
        logger.info("ESTAS EN EL CATEGORIES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, String.class, (Object) null);

        logger.info("response: " + response.getBody());
        
        return null;
    }



    // Método para encontrar una categoría por su ID
    public Categories getCategoryByID(Long id){
        return categoriesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las categorías
    public List<Categories> listaCategories(){
        return categoriesRepository.findAll();
    }

    // Método para guardar una nueva categoría
    public Categories guardarCategoria(Categories categoriaSave){
        return categoriesRepository.save(categoriaSave);
    }

    // Método para eliminar una categoría por su ID
    void deleteById(Long id){
        categoriesRepository.deleteById(id);
    };

}
