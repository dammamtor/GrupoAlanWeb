package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
import grupoalan.backendgalan.repository.DescriptionRepository;
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
import java.util.*;

@Service
public class ProductsService {
    static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    private RestTemplate restTemplate;


    private static final String API_URL = "https://data.makito.es/api/products";

    private static final String API_URL_ROLY = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=es-ES&brand=roly";

    public boolean makitoProductsFromApi(String apiToken){
        logger.info("ESTAS EN EL PRODUCTS SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiToken: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StatusCode> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

        StatusCode statusCode = response.getBody();

        //logger.info("LISTA DE PRODUCTOS: " + statusCode);

        //FUNCIONA, VAMOS A RELLENARLO

        if (statusCode != null) {
            List<ProductsMakito> productsMakitos = statusCode.getProducts();
            logger.info("FUNKA");

            // Crear una lista para almacenar las categorías convertidas sin nombres duplicados
            List<Products> convertedProducts = new ArrayList<>();

            // Iterar sobre los productos obtenidos de la API
            for (ProductsMakito productData : productsMakitos) {
                // Verificar si el producto ya existe en la base de datos
                Optional<Products> existingProductOptional = productsRepository.findByName(productData.getName());

                if (existingProductOptional.isPresent()) {
                    // Si el producto ya existe, eliminarlo de la base de datos
                    productsRepository.delete(existingProductOptional.get());
                    logger.info("Producto existente eliminado: " + existingProductOptional.get().getName());
                }

                // Crear un nuevo objeto Products y guardar en la base de datos
                Products product = new Products();
                product.setName(productData.getName());
                product.setRef(productData.getRef());
                product.setWeight(productData.getWeight());
                product.setLength(productData.getLength());
                product.setWidth(productData.getWidth());
                product.setHeight(productData.getHeight());
                product.setColors(productData.getColors());

                product = productsRepository.save(product);
                convertedProducts.add(product);
            }

            logger.info("Products obtenidas de la API: " + convertedProducts);

            logger.info("Actualización de la lista de productos completada");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }

    public boolean rolyProductsFromApi(String apiToken){
        logger.info("ESTAS EN EL PRODUCTS SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        logger.info("apiTokenRoly: " + apiToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Items> response = restTemplate.exchange(
                API_URL_ROLY, HttpMethod.GET, requestEntity, Items.class);

        Items items = response.getBody();

        if (items != null) {
            List<ProductsRoly> productsRolyList = items.getItem();
            logger.info("FUNKA");

            // Crear una lista para almacenar las categorías convertidas sin nombres duplicados
            List<Products> convertedProducts = new ArrayList<>();

            // Iterar sobre los productos obtenidos de la API
            for (ProductsRoly productData : productsRolyList) {
                // Verificar si el producto ya existe en la base de datos
                Optional<Products> existingProductOptional = productsRepository.findByName(productData.getItemname());

                if (existingProductOptional.isPresent()) {
                    // Si el producto ya existe, eliminarlo de la base de datos
                    productsRepository.delete(existingProductOptional.get());
                    logger.info("Producto existente eliminado: " + existingProductOptional.get().getName());
                }

                // Crear un nuevo objeto Products y guardar en la base de datos
                Products product = new Products();
                product.setName(productData.getItemname());
                product.setRef(productData.getItemcode());
                product.setWeight(productData.getWeight());
                product.setMeasures(productData.getMeasures());
                product.setColors(productData.getColorname());

                product = productsRepository.save(product);
                convertedProducts.add(product);
            }

            logger.info("Products obtenidas de la API: " + convertedProducts);

            logger.info("Actualización de la lista de productos completada");
            return true;
        } else {
            logger.error("Error al obtener el objeto StatusCode de la respuesta");
            return false;
        }
    }



    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private DescriptionRepository descriptionsRepository;

    public List<Products> getAllProductsWithDescriptions() {
        List<Products> productsList = productsRepository.findAll();

        for (Products product : productsList) {
            Optional<Descriptions> descriptions = descriptionsRepository.findByRef(product.getRef());

            // Creamos un conjunto para almacenar las descripciones relacionadas con este producto
            Set<Descriptions> relatedDescriptions = new HashSet<>();

            if(descriptions.isPresent()){
                Descriptions description = descriptions.get();
                if (description.getRef().equals(product.getRef())) {
                    relatedDescriptions.add(description);
                }
            }

            // Establecemos las descripciones relacionadas para el producto
            product.setDescriptions(relatedDescriptions);
        }

        return productsList;
    }





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
