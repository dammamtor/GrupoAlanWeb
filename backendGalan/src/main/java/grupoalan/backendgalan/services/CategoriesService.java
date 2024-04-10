package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.response.CategoryResponse;
import grupoalan.backendgalan.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.token}")
    private String apiToken;

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String API_URL = "https://data.makito.es/api/categories";

    public List<Categories> syncCategoriesFromApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        // Realizar la llamada a la API externa
        CategoryResponse[] responses = restTemplate.getForObject(API_URL, CategoryResponse[].class);

        // Procesar las respuestas y guardarlas en la base de datos
        List<Categories> categoriesList = new ArrayList<>();
        for (CategoryResponse response : responses) {
            Categories category = new Categories();
            category.setName(response.getCategory());
            // Agregar la categoría a la lista
            categoriesList.add(category);
        }
        // Guardar todas las categorías en la base de datos
        return categoriesRepository.saveAll(categoriesList);
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
