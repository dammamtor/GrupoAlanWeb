package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Colors;
import grupoalan.backendgalan.model.Descriptions;
import grupoalan.backendgalan.model.Images;
import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.makito.*;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
import grupoalan.backendgalan.repository.ColorRepository;
import grupoalan.backendgalan.repository.DescriptionRepository;
import grupoalan.backendgalan.repository.ImagesRepository;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private DescriptionRepository descriptionsRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ColorRepository colorRepository;
    private static final String API_URL = "https://data.makito.es/api/products";

    private static final String API_URL_ROLY = "https://clientsws.gorfactory.es:2096/api/v1.1/item/getcatalog?lang=es-ES&brand=roly";

    public boolean makitoProductsFromApi(String apiToken) {
        logger.info("ESTAS EN EL PRODUCTS SERVICE");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<StatusCode> response = restTemplate.exchange(
                    API_URL, HttpMethod.GET, requestEntity, StatusCode.class);

            StatusCode statusCode = response.getBody();

            if (statusCode != null && statusCode.getProducts() != null) {
                List<ProductsMakito> productsMakitos = statusCode.getProducts();
                logger.info("FUNCIONA");

                for (ProductsMakito productData : productsMakitos) {
                    // Buscar todos los productos con el mismo nombre
                    List<Products> existingProducts = productsRepository.findByName(productData.getName());

                    if (!existingProducts.isEmpty()) {
                        // Si existen productos con el mismo nombre, seleccionar el primero
                        Products existingProduct = existingProducts.get(0);

                        // Obtener y agregar descripciones al producto utilizando el método personalizado
                        addDescriptionsToProduct(existingProduct, productData);
                        addImagesToProduct(existingProduct, productData);
                        addColorsToProduct(existingProduct, productData);

                        existingProduct = productsRepository.save(existingProduct);

                        logger.info("Updated producto: " + existingProduct);
                    } else {
                        // Si no existe un producto con el mismo nombre, crear uno nuevo
                        Products newProduct = new Products();
                        newProduct.setName(productData.getName());
                        newProduct.setRef(productData.getRef());
                        newProduct.setWeight(productData.getWeight());
                        newProduct.setLength(productData.getLength());
                        newProduct.setWidth(productData.getWidth());
                        newProduct.setHeight(productData.getHeight());
                        newProduct.setColors(productData.getColors());

                        // Obtener y agregar descripciones al nuevo producto utilizando el método personalizado
                        addDescriptionsToProduct(newProduct, productData);
                        addImagesToProduct(newProduct, productData);
                        addColorsToProduct(newProduct, productData);
                        // Guardar el nuevo producto en la base de datos
                        newProduct = productsRepository.save(newProduct);

                        logger.info("Nuevo producto: " + newProduct);
                    }
                }

                logger.info("Actualización de la lista de productos completada");
                return true;
            } else {
                logger.error("Error al obtener el objeto StatusCode de la respuesta");
                return false;
            }
        } catch (RestClientException ex) {
            logger.error("Error al llamar a la API: " + ex.getMessage());
            return false;
        }
    }

//    private void addDescriptionsToProduct(Products product, List<DescriptionsMakito> descriptionsMakitos) {
//        // Obtener el conjunto de descripciones del producto (asegurándose de que no sea null)
//        Set<Descriptions> productDescriptions = product.getDescriptions();
//        if (productDescriptions == null) {
//            productDescriptions = new HashSet<>();
//            product.setDescriptions(productDescriptions);
//        } else {
//            // Limpiar las descripciones existentes del producto
//            productDescriptions.clear();
//        }
//
//        // Filtrar y agregar las descripciones que cumplan con el criterio de idioma (lang = 1 para español, lang = 2 para inglés)
//        for (DescriptionsMakito descriptionsMakito : descriptionsMakitos) {
//            if ("1".equals(descriptionsMakito.getLang()) || "2".equals(descriptionsMakito.getLang())) {
//                // Crear una nueva instancia de Descriptions y asignar los valores correspondientes
//                Descriptions description = new Descriptions();
//                description.setRef(descriptionsMakito.getRef());
//                description.setDetails(descriptionsMakito.getDesc()); // Usar el campo 'desc' como 'details'
//
//                // Establecer la relación con el producto
//                description.setProduct(product);
//
//                // Agregar la descripción al conjunto de descripciones del producto
//                productDescriptions.add(description);
//            }
//        }
//
//        if (!productDescriptions.isEmpty()) {
//            logger.info("Descripciones agregadas al producto: " + product.getName());
//        } else {
//            logger.warn("No se encontraron descripciones en español o inglés para el producto: " + product.getName());
//        }
//
//        // Guardar el producto actualizado en la base de datos
//        productsRepository.save(product);
//    }

    private void addDescriptionsToProduct(Products product, ProductsMakito productData) {
        // Obtener las descripciones del producto utilizando el método personalizado
        List<Descriptions> descriptions = descriptionsRepository.findByRef(productData.getRef());

        // Limpiar las descripciones existentes del producto
        product.getDescriptions().clear();

        // Agregar las nuevas descripciones al producto solo si se encontraron algunas
        if (!descriptions.isEmpty()) {
            // Agregar las nuevas descripciones al producto
            product.getDescriptions().addAll(descriptions);

            logger.info("Descripciones agregadas al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron descripciones para el producto: " + product.getName());
        }

        // Guardar el producto actualizado en la base de datos
//        productsRepository.save(product);
    }

    private void addImagesToProduct(Products product, ProductsMakito productData) {
        List<Images> images = imagesRepository.findByRef(productData.getRef());

        product.getImages().clear();
        logger.info("Número de imágenes después de borrar: " + product.getImages().size());

        if (!images.isEmpty()) {
            product.getImages().addAll(images);
            logger.info("Imagenes agregadas al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron imagenes para el producto: " + product.getName());
        }
    }

    private void addColorsToProduct(Products product, ProductsMakito productData) {
        product.getColorsSet().clear();

        // Obtener los códigos de colores del producto
        String colorCodes = productData.getColors();

        if (!colorCodes.isEmpty()) {
            String[] colorArray = colorCodes.split(",\\s*"); // Dividir los códigos por comas

            // Buscar los detalles de cada color y agregarlos al producto
            List<Colors> colorsList = new ArrayList<>();
            for (String colorCode : colorArray) {
                List<Colors> colors = colorRepository.findByCode(colorCode.trim());
                colorsList.addAll(colors);
            }

            // Agregar los colores al producto
            Set<Colors> colorsSet = new HashSet<>(colorsList);
            product.setColorsSet(colorsSet);

            // Mensaje de registro de información
            logger.info("Colores agregados al producto: " + product.getName());
        } else {
            // Mensaje de registro de advertencia si no hay códigos de colores
            logger.warn("No se encontraron colores para el producto: " + product.getName());
        }
    }



    public boolean rolyProductsFromApi(String apiToken) {
        logger.info("ESTAS EN EL PRODUCTS SERVICE");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Items> response = restTemplate.exchange(
                    API_URL_ROLY, HttpMethod.GET, requestEntity, Items.class);

            Items items = response.getBody();

            if (items != null && items.getItem() != null) {
                List<ProductsRoly> productsRolyList = items.getItem();
                logger.info("FUNKA");

                // Obtener nombres únicos de los productos obtenidos
                Set<String> productNames = productsRolyList.stream()
                        .map(ProductsRoly::getItemname)
                        .collect(Collectors.toSet());

                // Buscar productos existentes por nombre utilizando el nuevo método personalizado
                List<Products> existingProducts = productsRepository.findByNameIn(new ArrayList<>(productNames));

                Map<String, Products> existingProductsMap = existingProducts.stream()
                        .collect(Collectors.toMap(Products::getName, p -> p));

                List<Products> convertedProducts = productsRolyList.stream()
                        .map(productData -> {
                            Products existingProduct = existingProductsMap.get(productData.getItemname());
                            if (existingProduct != null) {
                                // Si el producto existe, actualizar sus atributos
                                existingProduct.setRef(productData.getItemcode());
                                existingProduct.setWeight(productData.getWeight());
                                existingProduct.setMeasures(productData.getMeasures());
                                existingProduct.setColors(productData.getColorname());
                            } else {
                                // Si el producto no existe, crear uno nuevo
                                Products newProduct = new Products();
                                newProduct.setName(productData.getItemname());
                                newProduct.setRef(productData.getItemcode());
                                newProduct.setWeight(productData.getWeight());
                                newProduct.setMeasures(productData.getMeasures());
                                newProduct.setColors(productData.getColorname());
                                existingProduct = newProduct;
                            }
                            // Guardar o actualizar el producto en la base de datos
                            Products savedProduct = productsRepository.save(existingProduct);
                            logger.info("Producto guardado en la base de datos: " + savedProduct.getName());
                            return savedProduct;
                        })
                        .collect(Collectors.toList());

                logger.info("Productos obtenidos de la API y actualizados en la base de datos: " + convertedProducts);
                logger.info("Actualización de la lista de productos completada");

                return true;
            } else {
                logger.error("Error al obtener el objeto StatusCode de la respuesta");
                return false;
            }
        } catch (RestClientException ex) {
            logger.error("Error al llamar a la API: " + ex.getMessage());
            return false;
        }
    }

    //TEST. OBTENER DATOS PRODUCTO ANTES DE PASAR A GRAN PLANO
    public Products getDataProductID() {
        String idNumber = "3403";
        Products testProduct = productsRepository.findByRef(idNumber);
        List<Descriptions> testDescriptions = descriptionsRepository.findByRef(idNumber);
        List<Images> testImages = imagesRepository.findByRef(idNumber);

        // AGREGAR LOS DATOS
        testProduct.setDescriptions(new HashSet<>(testDescriptions)); // Usar HashSet para evitar duplicados en la relación OneToMany
        testProduct.setImages(new HashSet<>(testImages));

        // Obtener los códigos de colores del producto
        String colorCodes = testProduct.getColors();

        if (!colorCodes.isEmpty()) {
            String[] colorArray = colorCodes.split(",\\s*"); // Dividir los códigos por comas

            // Buscar los detalles de cada color y agregarlos al producto
            List<Colors> testColors = new ArrayList<>();
            for (String colorCode : colorArray) {
                List<Colors> colors = colorRepository.findByCode(colorCode.trim());
                testColors.addAll(colors);
            }

            // Agregar los colores al producto
            Set<Colors> colorsSet = new HashSet<>(testColors);
            testProduct.setColorsSet(colorsSet);
        }

        // Guardar el producto actualizado en la base de datos
        productsRepository.save(testProduct);

        return testProduct; // Devolver el producto actualizado
    }


    // Método para encontrar un producto por su ID
    public Products getProductByID(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    // Método para encontrar todos los productos
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    // Método para guardar un nuevo producto
    public Products saveProduct(Products product) {
        return productsRepository.save(product);
    }

    // Método para eliminar un producto por su ID
    public void deleteProductByID(Long id) {
        productsRepository.deleteById(id);
    }
}
