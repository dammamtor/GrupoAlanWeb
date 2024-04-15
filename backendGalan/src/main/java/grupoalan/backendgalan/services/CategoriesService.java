package grupoalan.backendgalan.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.response.makito.CategoryResponse;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.CategoriesRoly;
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
import java.util.*;

@Service
public class CategoriesService {
    static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.token}")
    private String apiToken;

    @Value("${api.token.roly}")
    private String apiTokenRoly;

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String API_URL = "https://data.makito.es/api/categories";

    private static final String API_URL_ROLY = "https://clientsws.gorfactory.es:2096/api/v1.0/item/categories?lang=es-ES&brand=roly";


    public List<Categories> makitoCategoriesFromApi() {
        logger.info("ESTAS EN EL CATEGORIES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();
        if (statusCode != null) {
            List<CategoryResponse> categoriesList = statusCode.getCategories();
            logger.info("FUNKA");
            // Crear un conjunto para almacenar los nombres de las categorías sin duplicados
            Set<String> uniqueCategoryNames = new HashSet<>();

            // Iterar sobre la lista de CategoryResponse para obtener los nombres de las categorías únicas
            for (CategoryResponse categoryResponse : categoriesList) {
                // Verificar si el campo lang es 1 o 2
                if (categoryResponse.getLang() == 1 || categoryResponse.getLang() == 2) {
                    uniqueCategoryNames.add(categoryResponse.getCategory());
                }
            }

            // Crear una lista para almacenar las categorías convertidas sin nombres duplicados
            List<Categories> convertedCategories = new ArrayList<>();

            // Iterar sobre los nombres únicos de las categorías y crear objetos Categories
            for (String categoryName : uniqueCategoryNames) {
                Categories category = new Categories();
                category.setName(categoryName);
                category.setDescription(""); // Puedes asignar una descripción si tienes esa información disponible
                // Guardar la categoría en la base de datos para obtener el category_id
                category = categoriesRepository.save(category);
                // Agregar la categoría convertida a la lista
                convertedCategories.add(category);
            }

            // Loggear las categorías obtenidas de la API
            logger.info("Categorías obtenidas de la API: " + convertedCategories);

            return convertedCategories;
        } else {
            System.err.println("No se pudo obtener el objeto StatusCode de la respuesta.");
            return null;
        }
    }

    //METODO DE OBTENCION DE CATEGORIAS DE ROLY
    public List<Categories> rolyCategoriesFromApi() {
        logger.info("ESTAS EN EL ROLY SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiTokenRoly);

        logger.info("apiTokenRoly: " + apiTokenRoly);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<CategoriesRoly[]> response = restTemplate.exchange(
                API_URL_ROLY, HttpMethod.GET, requestEntity, CategoriesRoly[].class);

        CategoriesRoly[] categoriesRolyArray = response.getBody();

        if (categoriesRolyArray != null) {
            Set<String> uniqueCategoryNames = new HashSet<>();
            List<Categories> convertedCategories = new ArrayList<>();

            for (CategoriesRoly categoriesRoly : categoriesRolyArray) {
                String categoryName = categoriesRoly.getParentCategory();
                if (categoryName == null || categoryName.isEmpty()) {
                    categoryName = categoriesRoly.getCategory();
                }
                // Verificar si la categoría ya ha sido agregada
                if (!uniqueCategoryNames.contains(categoryName)) {
                    Categories category = new Categories();
                    category.setName(categoryName);
                    category.setDescription(""); // Puedes asignar una descripción si tienes esa información disponible
                    // Guardar la categoría en la base de datos para obtener el category_id
                    category = categoriesRepository.save(category);
                    // Agregar la categoría convertida a la lista
                    convertedCategories.add(category);
                    // Agregar el nombre de la categoría al conjunto de nombres únicos
                    uniqueCategoryNames.add(categoryName);
                }
            }

            // Loggear las categorías obtenidas de la API
            logger.info("Categorías obtenidas de la API: " + convertedCategories);

            return convertedCategories;
        } else {
            logger.error("La respuesta de la API no contiene categorías.");
            return Collections.emptyList();
        }
    }


    // Método para encontrar una categoría por su ID
    public Categories getCategoryByID(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    // Método para encontrar todas las categorías
    public List<Categories> listaCategories() {
        return categoriesRepository.findAll();
    }

    // Método para guardar una nueva categoría
    public Categories guardarCategoria(Categories categoriaSave) {
        return categoriesRepository.save(categoriaSave);
    }

    // Método para eliminar una categoría por su ID
    void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }

    ;

}
