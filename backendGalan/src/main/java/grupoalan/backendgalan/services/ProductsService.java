package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Categories;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.makito.CategoryResponse;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.repository.ProductsRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductsService {
    static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductsRepository repository;

    @Value("${api.token}")
    private String apiToken;

    @Value("${api.token.roly}")
    private String apiTokenRoly;

    private static final String API_URL = "https://data.makito.es/api/products";

    private static final String API_URL_ROLY = "https://clientsws.gorfactory.es:2096/api/v1.0/item/categories?lang=es-ES&brand=roly";

    public List<Products> makitoProductsFromApi(){
        logger.info("ESTAS EN EL CATEGORIES SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        logger.info("LISTA DE PRODUCTOS: " + statusCode);

        //FUNCIONA, VAMOS A RELLENARLO
        //TERRENO PANTANOSO, CHEMA
        if (statusCode != null) {
            List<ProductsMakito> productsMakitos = statusCode.getProducts();
            logger.info("FUNKA");

            // Crear una lista para almacenar las categorías convertidas sin nombres duplicados
            List<Products> convertedProducts = new ArrayList<>();

            // Iterar sobre los nombres únicos de las categorías y crear objetos Categories
            for (ProductsMakito productData : productsMakitos) {
                Products product = new Products();
                product.setName(productData.getName());
                product.setDescription("");
                product.setAvailable(false);
                product.setPrice(BigDecimal.ZERO);

                product = productsRepository.save(product);

                convertedProducts.add(product);
            }

            logger.info("Products obtenidas de la API: " + convertedProducts);

            return convertedProducts;
        } else {
            System.err.println("No se pudo obtener el objeto StatusCode de la respuesta.");
            return null;
        }
    }





    @Autowired
    private ProductsRepository productsRepository;

    // Método para encontrar un producto por su ID
    public Products getProductByID(Long id){
        return productsRepository.findById(id).orElse(null);
    }

    // Método para encontrar todos los productos
    public List<Products> getAllProducts(){
        return productsRepository.findAll();
    }

    // Método para guardar un nuevo producto
    public Products saveProduct(Products product){
        return productsRepository.save(product);
    }

    // Método para eliminar un producto por su ID
    public void deleteProductByID(Long id){
        productsRepository.deleteById(id);
    }
}
