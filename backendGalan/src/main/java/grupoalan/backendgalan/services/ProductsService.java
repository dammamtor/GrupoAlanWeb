package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.*;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.model.response.makito.StatusCode;
import grupoalan.backendgalan.model.response.roly.Items;
import grupoalan.backendgalan.model.response.roly.ProductsRoly;
import grupoalan.backendgalan.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductsService{
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
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private MarkingsRepository markingsRepository;
    @Autowired
    private VariantsRepository variantsRepository;

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
                        logger.info("Producto encontraodo: " + existingProduct);

                        // Obtener y agregar descripciones al producto utilizando el método personalizado
                        addDescriptionsToProduct(existingProduct, productData);
                        addImagesToProduct(existingProduct, productData);
                        addCategoriesToProduct(existingProduct, productData);
                        addVariantsToProduct(existingProduct, productData);
                        addMarkingsToProduct(existingProduct, productData);

                        existingProduct = productsRepository.save(existingProduct);
                        logger.info("Producto guardado: " + existingProduct);

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
                        newProduct.setPrintcode(productData.getPrintcode());
                        newProduct.setDiameter(productData.getDiameter());
                        newProduct.setIntrastat(productData.getIntrastat());
                        newProduct.setPf_type(productData.getPf_type());
                        newProduct.setPf_units(productData.getPf_units());
                        newProduct.setPf_description(productData.getPf_description());
                        newProduct.setPf_length(productData.getPf_length());
                        newProduct.setPf_height(productData.getPf_height());
                        newProduct.setPf_width(productData.getPf_width());
                        newProduct.setPf_weight(productData.getPf_weight());
                        newProduct.setPi2_type(productData.getPi2_type());
                        newProduct.setPi2_units(productData.getPi2_units());
                        newProduct.setPi2_description(productData.getPi2_description());
                        newProduct.setPi2_length(productData.getPi2_length());
                        newProduct.setPi2_height(productData.getPi2_height());
                        newProduct.setPi2_width(productData.getPi2_width());
                        newProduct.setPi2_weight(productData.getPi2_weight());
                        newProduct.setPi1_type(productData.getPi1_type());
                        newProduct.setPi1_units(productData.getPi1_units());
                        newProduct.setPi1_description(productData.getPi1_description());
                        newProduct.setPi1_length(productData.getPi1_length());
                        newProduct.setPi1_height(productData.getPi1_height());
                        newProduct.setPi1_width(productData.getPi1_width());
                        newProduct.setPi1_weight(productData.getPi1_weight());
                        newProduct.setPtc_type(productData.getPtc_type());
                        newProduct.setPtc_units(productData.getPtc_units());
                        newProduct.setPtc_description(productData.getPtc_description());
                        newProduct.setPtc_length(productData.getPtc_length());
                        newProduct.setPtc_height(productData.getPtc_height());
                        newProduct.setPtc_width(productData.getPtc_width());
                        newProduct.setPtc_wight(productData.getPtc_wight());
                        newProduct.setPtc_net_weight(productData.getPtc_net_weight());
                        newProduct.setPallet_units(productData.getPallet_units());
                        newProduct.setBundle_pallets(productData.getBundle_pallets());
                        newProduct.setPallet_weight(productData.getPallet_weight());
                        newProduct.setSizes(productData.getSizes());

                        // Obtener y agregar descripciones al nuevo producto utilizando el método personalizado
                        addDescriptionsToProduct(newProduct, productData);
                        addImagesToProduct(newProduct, productData);
                        addCategoriesToProduct(newProduct, productData);
                        addVariantsToProduct(newProduct, productData);
                        addMarkingsToProduct(newProduct, productData);

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

    private void addDescriptionsToProduct(Products product, ProductsMakito productData) {
        // Registro para indicar el inicio del método
        logger.info("Iniciando addDescriptionsToProduct para el producto: " + product.getName());

        // Obtener las descripciones del producto utilizando el método personalizado
        List<Descriptions> descriptions = descriptionsRepository.findByRef(productData.getRef());

        if (product.getDescriptions() == null) {
            product.setDescriptions(new HashSet<>());
        }
        product.getDescriptions().clear();

        // Agregar las nuevas descripciones al producto solo si se encontraron algunas
        if (!descriptions.isEmpty()) {
            // Agregar las nuevas descripciones al producto
            Set<Descriptions> descriptionsSet = new HashSet<>(descriptions);
            product.setDescriptions(descriptionsSet);

            // Establecer la relación inversa en las descripciones
            for (Descriptions description : descriptions) {
                description.setProduct(product);
            }

            logger.info("Descripciones agregadas al producto: " + product.getName());
            logger.info("Descripciones añadidas: " + product.getDescriptions());
        } else {
            logger.warn("No se encontraron descripciones para el producto: " + product.getName());
        }

        // Registro para indicar la finalización del método
        logger.info("Finalizando addDescriptionsToProduct para el producto: " + product.getName());
    }


    private void addImagesToProduct(Products product, ProductsMakito productData) {
        List<Images> images = imagesRepository.findByRef(productData.getRef());

        if (product.getImages() == null) {
            product.setImages(new HashSet<>());
        }

        product.getImages().clear();
        logger.info("Número de imágenes después de borrar: " + product.getImages().size());

        if (!images.isEmpty()) {
            Set<Images> imagesSet = new HashSet<>(images);
            product.getImages().addAll(imagesSet);

            // Establecer la relación inversa en las imagenes
            for (Images images1 : images) {
                images1.setProduct(product);
            }

            logger.info("Imagenes agregadas al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron imagenes para el producto: " + product.getName());
        }
    }


    private void addCategoriesToProduct(Products product, ProductsMakito productData) {
        // Obtener las categorías asociadas al producto
        List<Categories> categoriesList = categoriesRepository.findByRef(productData.getRef());

        // Limpiar las categorías existentes del producto (no necesaria si sobrescribes todas las categorías)
        // product.getCategories().clear();

        if (!categoriesList.isEmpty()) {
            // Crear un nuevo conjunto de categorías para el producto
            Set<Categories> newCategories = new HashSet<>();

            // Iterar sobre las categorías asociadas al producto
            for (Categories category : categoriesList) {
                // Asociar el producto con la categoría
                Set<Products> productsSet = category.getProducts();
                if (productsSet == null) {
                    productsSet = new HashSet<>();
                    category.setProducts(productsSet);
                }
                productsSet.add(product);
                // Añadir la categoría al nuevo conjunto de categorías del producto
                newCategories.add(category);
                logger.info("Producto asociado a la categoría: " + category.getCategory());
            }

            // Asignar el nuevo conjunto de categorías al producto
            product.setCategories(newCategories);

            // Guardar los cambios en la base de datos (asumiendo que estás usando JPA)
            productsRepository.save(product);

            logger.info("Categorías agregadas al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron categorías para el producto: " + product.getName());
        }
    }
    private void addVariantsToProduct(Products product, ProductsMakito productData) {
        // Obtener las variantes asociadas al producto según alguna referencia (ejemplo: productData.getRef())
        List<Variants> variantsList = variantsRepository.findByRef(productData.getRef());

        // Limpiar las variantes existentes del producto (no necesaria si sobrescribes todas las variantes)
        // product.getVariants().clear();

        if (!variantsList.isEmpty()) {
            // Crear un nuevo conjunto de variantes para el producto
            Set<Variants> newVariants = new HashSet<>();

            // Iterar sobre las variantes asociadas al producto
            for (Variants variant : variantsList) {
                // Asociar el producto con la variante
                variant.setProduct(product);
                // Añadir la variante al nuevo conjunto de variantes del producto
                newVariants.add(variant);
                logger.info("Producto asociado a la variante: " + variant.getUnique_ref());
            }

            // Asignar el nuevo conjunto de variantes al producto
            product.setVariants(newVariants);

            // Guardar los cambios en la base de datos (asumiendo que estás usando JPA)
            productsRepository.save(product);

            logger.info("Variantes agregadas al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron variantes para el producto: " + product.getName());
        }
    }

    private void addMarkingsToProduct(Products product, ProductsMakito productData) {
        // Obtener los marcajes asociados al producto según alguna referencia (ejemplo: productData.getRef())
        List<Markings> markingsList = markingsRepository.findByRef(productData.getRef());

        if (!markingsList.isEmpty()) {
            // Crear un nuevo conjunto de marcajes para el producto
            Set<Markings> newMarkings = new HashSet<>();

            // Iterar sobre los marcajes asociados al producto
            for (Markings marking : markingsList) {
                // Asociar el producto con el marcaje
                marking.setProduct(product);
                // Añadir el marcaje al nuevo conjunto de marcajes del producto
                newMarkings.add(marking);
                logger.info("Producto asociado al marcaje: " + marking.getRef());
            }

            // Asignar el nuevo conjunto de marcajes al producto
            product.setMarkings(newMarkings);

            // Guardar los cambios en la base de datos (asumiendo que estás usando JPA)
            productsRepository.save(product);

            logger.info("Marcajes agregados al producto: " + product.getName());
        } else {
            logger.warn("No se encontraron marcajes para el producto: " + product.getName());
        }
    }

    public List<Products> searchProducts(String searchTerm) {
        // Convertir el término de búsqueda a minúsculas y remover tildes
        searchTerm = normalizeString(searchTerm.toLowerCase());

        List<Products> allProducts = productsRepository.findAll();
        List<Products> matchingProducts = new ArrayList<>();
        boolean isExactMatch = false;

        logger.info("Starting product search for searchTerm: " + searchTerm);

        for (Products product : allProducts) {
            StringBuilder productDescriptionBuilder = new StringBuilder();
            Set<String> uniqueTypes = new HashSet<>();

            // Concatenar los tipos de descripción al inicio del nombre del producto
            Set<Descriptions> descriptions = product.getDescriptions();
            for (Descriptions description : descriptions) {
                String type = description.getType();
                if (type != null) {
                    uniqueTypes.add(type);
                }
            }
            for (String type : uniqueTypes) {
                productDescriptionBuilder.append(type).append(" ");
            }
            productDescriptionBuilder.append(product.getName()).append(" "); // Agregar el nombre del producto
            productDescriptionBuilder.append(product.getRef()).append(" "); // Agregar el número de referencia

            String productDescription = normalizeString(productDescriptionBuilder.toString().toLowerCase().trim());

            logger.info("Checking product: " + productDescription);

            if (productDescription.equals(searchTerm)) {
                matchingProducts.clear();
                matchingProducts.add(product);
                isExactMatch = true;
                logger.info("Exact match found for: " + product.getName());
                break;
            } else if (productDescription.contains(searchTerm)) {
                matchingProducts.add(product);
                logger.info("Partial match found for: " + product.getName());
            }
        }

        if (isExactMatch) {
            logger.info("Exact match found, returning the product.");
            return matchingProducts;
        } else {
            if (matchingProducts.isEmpty()) {
                logger.info("No matches found.");
                return null;
            } else {
                logger.info("Returning multiple matching products.");
                return matchingProducts;
            }
        }
    }

    private String normalizeString(String str) {
        // Remover tildes
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }


    public List<Products> searchProductsByType(String searchTerm) {
        List<Products> allProducts = productsRepository.findAll();
        List<Products> matchingProducts = new ArrayList<>();

        // Normalizar el término de búsqueda para eliminar tildes y convertir a minúsculas
        String normalizedSearchTerm = normalizeString(searchTerm.toLowerCase());

        logger.info("Starting product search for searchTerm: " + searchTerm);

        for (Products product : allProducts) {
            Set<Descriptions> descriptions = product.getDescriptions();
            for (Descriptions description : descriptions) {
                String type = description.getType();
                if (type != null && normalizeString(type.toLowerCase()).contains(normalizedSearchTerm)) {
                    matchingProducts.add(product);
                    logger.info("Product matches found for: " + product.getName());
                    break; // Si encontramos una coincidencia, no es necesario continuar con las descripciones
                }
            }
        }

        logger.info("Matches found for searchTerm: " + searchTerm + ". Total products found: " + matchingProducts.size());
        return matchingProducts;
    }

//    private String normalizeString(String input) {
//        logger.info("Normalizing string: " + input);
//        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
//        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        String result = pattern.matcher(normalized).replaceAll("");
//        logger.info("Normalized result: " + result);
//        return result;
//    }


    public List<Products> filtrarProductosPorCategoriasColoresYTipos(List<String> categorias, List<String> colores, List<String> tipos) {
        // Implementa la lógica para filtrar los productos en base a las opciones seleccionadas
        return productsRepository.findByCategoriasAndColoresAndTipos(categorias, colores, tipos);
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
//                                existingProduct.setMeasures(productData.getMeasures());
                                existingProduct.setColors(productData.getColorname());
                            } else {
                                // Si el producto no existe, crear uno nuevo
                                Products newProduct = new Products();
                                newProduct.setName(productData.getItemname());
                                newProduct.setRef(productData.getItemcode());
                                newProduct.setWeight(productData.getWeight());
//                                newProduct.setMeasures(productData.getMeasures());
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
    //METODOS PARA SABER CUANTOS PRODUCTOS HAY EN MAKITO Y ROLY, ANTES DE GUARDARLOS
    public boolean extProductsCount(String apiToken) {
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
                int totalProducts = productsMakitos.size();
                logger.info("Número total de productos de Makito: " + totalProducts);
            }

            return true;
        } catch (RestClientException ex) {
            logger.error("Error al llamar a la API: " + ex.getMessage());
            return false;
        }
    }

    public boolean extProductsCountRoly(String apiToken) {
        logger.info("ESTAS EN EL PRODUCTS SERVICE");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Items> response = restTemplate.exchange(
                    API_URL_ROLY, HttpMethod.GET, requestEntity, Items.class);

            Items items = response.getBody();

            if (items != null && items.getItem() != null) {
                List<ProductsRoly> productsRoly = items.getItem();
                int totalProducts = productsRoly.size();
                logger.info("Número total de productos de Roly: " + totalProducts);
            }

            return true;
        } catch (RestClientException ex) {
            logger.error("Error al llamar a la API: " + ex.getMessage());
            return false;
        }
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

    public Optional<Products> getProductById(Long productId) {

        return productsRepository.findByProductId(productId);
    }

    public Products getProductByRef(String ref) {

        return productsRepository.findByRef(ref);
    }

    public List<Products> getProductsByPage(int page, int size) {
        logger.info("Obteniendo productos de la página {} con tamaño {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Products> pagedResult = productsRepository.findAll(pageable);

        if (pagedResult.hasContent()) {
            logger.info("Se encontraron {} productos en la página {}", pagedResult.getNumberOfElements(), page);
            return pagedResult.getContent();
        } else {
            logger.info("No se encontraron productos en la página {}", page);
            return new ArrayList<Products>();
        }
    }

    public List<Products> filtrarProductosPorCategoriasYUnidades(List<String> categorias, float unidadesMin, float unidadesMax) {
        return productsRepository.findByCategoriasAndUnidades(categorias, unidadesMin, unidadesMax);
    }
}
