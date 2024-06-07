package grupoalan.backendgalan.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.Products;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoriesService {
    static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String API_URL = "https://data.makito.es/api/categories";

    private static final String API_URL_ROLY_ES = "https://clientsws.gorfactory.es:2096/api/v1.0/item/categories?lang=es-ES&brand=roly";
    private static final String API_URL_ROLY_EN = "https://clientsws.gorfactory.es:2096/api/v1.0/item/categories/tree?lang=en-GB&brand=roly";


    public boolean makitoCategoriesFromApi(String apiToken) {
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
            List<Categories> categories = new ArrayList<>();
            logger.info("FUNKA");

            categoriesRepository.deleteAll();

            for (CategoryResponse categoryName : categoriesList) {
                if (categoryName.getLang() == 1 || categoryName.getLang() == 2) {
                    Categories category = new Categories();
                    category.setRef(categoryName.getRef());
                    category.setCategory(categoryName.getCategory());
                    category.setLang(categoryName.getLang());
                    category = categoriesRepository.save(category);

                    String langString = (category.getLang() == 1) ? "español" : "inglés";
                    logger.info("Categoría para producto " + category.getRef() + " en " + langString);
                    categories.add(category);
                }


            }
            logger.info("Categories obtenidas de la API: " + categories);
            return true;
        } else {
            System.err.println("No se pudo obtener el objeto StatusCode de la respuesta.");
            return false;
        }
    }

    public List<String> listaCategoriasUnicasConConteo() {
        List<Categories> categories = categoriesRepository.findAllByLang(1);
        List<String> uniqueCategories = new ArrayList<>();

        for (Categories category : categories) {
            if (!uniqueCategories.contains(category.getCategory())) {
                uniqueCategories.add(category.getCategory());
            }
        }

        logger.info("Categorias en español: " + uniqueCategories.size());
        return uniqueCategories;
    }




    //METODO DE OBTENCION DE CATEGORIAS DE ROLY
//    public List<Categories> rolyCategoriesFromApi(String apiToken) {
//        logger.info("ESTAS EN EL ROLY SERVICE");
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiToken);
//
//        logger.info("apiTokenRoly: " + apiToken);
//
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<CategoriesRoly[]> response = restTemplate.exchange(
//                API_URL_ROLY, HttpMethod.GET, requestEntity, CategoriesRoly[].class);
//
//        CategoriesRoly[] categoriesRolyArray = response.getBody();
//
//        if (categoriesRolyArray != null) {
//            Set<String> uniqueCategoryNames = new HashSet<>();
//            List<Categories> convertedCategories = new ArrayList<>();
//
//            for (CategoriesRoly categoriesRoly : categoriesRolyArray) {
//                String categoryName = categoriesRoly.getParentCategory();
//                if (categoryName == null || categoryName.isEmpty()) {
//                    categoryName = categoriesRoly.getCategory();
//                }
//                // Verificar si la categoría ya ha sido agregada
//                if (!uniqueCategoryNames.contains(categoryName)) {
//                    Categories category = new Categories();
//                    category.setName(categoryName);
//                    category.setDescription(""); // Puedes asignar una descripción si tienes esa información disponible
//                    // Guardar la categoría en la base de datos para obtener el category_id
//                    category = categoriesRepository.save(category);
//                    // Agregar la categoría convertida a la lista
//                    convertedCategories.add(category);
//                    // Agregar el nombre de la categoría al conjunto de nombres únicos
//                    uniqueCategoryNames.add(categoryName);
//                }
//            }
//
//            // Loggear las categorías obtenidas de la API
//            logger.info("Categorías obtenidas de la API: " + convertedCategories);
//
//            return convertedCategories;
//        } else {
//            logger.error("La respuesta de la API no contiene categorías.");
//            return Collections.emptyList();
//        }
//    }

    //METODO DE OBTENCION DE CATEGORIAS DE ROLY
    @Transactional
    public boolean rolyCategoriesFromApi(String apiToken) {
        logger.info("ESTAS EN EL CATEGORIES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Para obtener los productos en español
        try {
            ResponseEntity<CategoriesRoly[]> response = restTemplate.exchange(
                    API_URL_ROLY_ES, HttpMethod.GET, requestEntity, CategoriesRoly[].class);
            CategoriesRoly[] categoriesRolyArray = response.getBody();

            logger.info("Categorías recibidas de la API: " + Arrays.toString(categoriesRolyArray));

//            categoriesRepository.deleteAll();
//
//            for (CategoriesRoly categoryRoly : categoriesRolyArray) {
//                logger.info("Procesando categoría: " + categoryRoly.getCategory());
//
//                Categories category = new Categories();
//                category.setRef(categoryRoly.getId()); // Establece el ID como referencia
//                category.setCategory(categoryRoly.getCategory());
//
//                List<Categories> subcategories = new ArrayList<>();
//                if (categoryRoly.getSubcategories() != null) {
//                    for (CategoriesRoly subcategoryRoly : categoryRoly.getSubcategories()) {
//                        Categories subcategory = new Categories();
//                        subcategory.setRef(subcategoryRoly.getId());
//                        subcategory.setCategory(subcategoryRoly.getCategory());
//                        subcategories.add(subcategory);
//
//                        logger.info("Subcategoria: " + subcategories);
//                    }
//                }
//                category.setSubcategories(subcategories);
//
//                logger.info("Guardando categoría en la base de datos: " + category.getCategory());
//                logger.info("Categoria: " + category);
//                categoriesRepository.save(category); // Guarda la categoría principal
//            }
        } catch (Exception e) {
            logger.error("Error al obtener las categorías en español: " + e.getMessage());
            return false;
        }

        return true;
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
