package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.*;
import grupoalan.backendgalan.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grupo-alan/api-externa")
public class APIextController {
    static final Logger logger = LoggerFactory.getLogger(APIextController.class);

    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private DescriptionService descriptionService;
    @Autowired
    private MarkingService markingService;
    @Autowired
    private MarkingTechniquesService markingTechniquesService;
    @Autowired
    private MarkingTranslationsService markingTranslationsService;
    @Autowired
    private VariantsService variantsService;
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private StockService stockService;
    @Autowired
    private PricesService pricesService;
    @Autowired
    private APITokenService apiTokenService;

    //ESTE CONTROLLER ESTA EXCLUSIVAMENTE DEDICADO A LA OBTENCION DE BBDD DE LAS APIS EXTERNAS

    //OBTENCION TOKEN
    // Método privado para obtener el token de la API externa Makito
    private String getApiToken() {
        String apiToken = apiTokenService.getApiToken();
        if (apiToken == null) {
            logger.error("No se pudo obtener el token de la API externa");
        }
        return apiToken;
    }

    //Método privado para obtener el token de la API externa Roly
    private String getApiRolyToken(){
        String apiToken = apiTokenService.getApiRolyToken();
        if (apiToken == null) {
            logger.error("No se pudo obtener el token de la API externa");
        }
        return apiToken;
    }

    //CATEGORIAS
    @GetMapping("/makito/categories")
    public ResponseEntity<String > makitoCategories() {
        // Obtener el token de la API externa
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            logger.info("ACTUALIZACIÓN DE CATEGORIES MAKITO EN LA BASE DE DATOS");

            // Realizar la actualización de los productos desde la API
            boolean updatedSuccessfully = categoriesService.makitoCategoriesFromApi(apiToken);

            if (updatedSuccessfully) {
                return new ResponseEntity<>("Lista de categorias Makito actualizada correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error en la actualización de productos Makito: " + e.getMessage());
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    @GetMapping("/roly/categories")
//    public ResponseEntity<String> rolyCategories() {
//        // Obtener el token de la API externa
//        String apiToken = getApiRolyToken();
//        if (apiToken == null) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        try {
//            logger.info("ACTUALIZACIÓN DE CATEGORIES ROLY EN LA BASE DE DATOS");
//
//            // Realizar la actualización de los productos desde la API
//            boolean updatedSuccessfully = categoriesService.rolyCategoriesFromApi(apiToken);
//
//            if (updatedSuccessfully) {
//                return new ResponseEntity<>("Lista de categorias Roly actualizada correctamente.", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Error al actualizar la lista de categorias Roly", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            logger.error("Error en la actualización de lista de categorias Roly: " + e.getMessage());
//            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    //PRODUCTS
    @GetMapping("/makito/products")
    public ResponseEntity<String> updateMakitoProducts() {
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>("Error al obtener el token de la API", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            logger.info("ACTUALIZACIÓN DE PRODUCTOS MAKITO EN LA BASE DE DATOS");

            // Realizar la actualización de los productos desde la API
            boolean updatedSuccessfully = productsService.makitoProductsFromApi(apiToken);

            if (updatedSuccessfully) {
                return new ResponseEntity<>("Lista de productos Makito actualizada correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error en la actualización de productos Makito: " + e.getMessage());
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/roly/products")
    public ResponseEntity<String> rolyProducts() {
        String apiToken = getApiRolyToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = productsService.rolyProductsFromApi(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de productos Roly actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/makito/products/{id}")
    public ResponseEntity<String> getMakitoProductById(@PathVariable Long id) {
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>("Error al obtener el token de la API", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            logger.info("SELECCIONANDO UN PRODUCTO EN CONCRETO DE LA BASE DE DATOS");

            // Obtener el producto por ID
            Optional<Products> producto = productsService.getProductById(id);
            logger.info("ID DEL PRODUCTO: " + producto.toString());

            if (producto != null) {
                return new ResponseEntity<>("Producto seleccionado: " + producto.toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró un producto con el ID especificado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al seleccionar producto Makito por ID: " + e.getMessage());
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //COLORS
    @GetMapping("/makito/colors")
    public ResponseEntity<String> makitoColors(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION COLORES MAKITO A BBDD EMPRESA");
        boolean updatedSuccessfully = colorService.makitoColorsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de colores makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/roly/colors")
    public ResponseEntity<String> rolyColors(){
        String apiToken = getApiRolyToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION COLORES ROLY A BBDD EMPRESA");
        boolean updatedSuccessfully = colorService.rolyColorsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de colores roly actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DESCRIPTIONS
    @GetMapping("makito/descriptions")
    public ResponseEntity<String > makitoDescriptions(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION DESCRIPCIONES MAKITO A BBDD EMPRESA");
        boolean updatedSuccessfully = descriptionService.makitoDescriptionsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de descripciones Roly actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("roly/descriptions")
    public ResponseEntity<String> rolyDescriptions() {
        String apiToken = getApiRolyToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION DESCRIPCIONES ROLY A BBDD EMPRESA");
        boolean updatedSuccessfully = descriptionService.rolyDescriptionsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de descripciones Roly actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de descripciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //MARKINGS
    @GetMapping("makito/markings")
    public ResponseEntity<String> makitoMarkings(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION MARKINGS MAKITO A BBDD EMPRESA");
        boolean updatedSuccessfully = markingService.makitoMarkingsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de markings Makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de productos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //MARKING TECHNIQUES
    @GetMapping("makito/techniques")
    public ResponseEntity<String> makitoTechniques(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION MARKING TECHNIQUES MAKITO A BBDD EMPRESA");
        boolean updatedSuccessfully = markingTechniquesService.makitoMarkingTechniquesFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de markings de makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de descripciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //MARKING TRANSLATIONS
    @GetMapping("makito/marking-translations")
    public ResponseEntity<String> makitoTranslatiions(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        boolean updatedSuccessfully = markingTranslationsService.makitoMarkingTranslationsFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de markings translations de makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de descripciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //VARIANTS
    @GetMapping("makito/variants")
    public ResponseEntity<String> makitoVariants(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION VARIANTS DE MAKITO A BBDD EMPRESA");
        boolean updatedSuccessfully = variantsService.makitoVariantsTechniquesFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de variants de makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de descripciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //IMAGES
    @GetMapping("makito/images")
    public ResponseEntity<String> makitoImages(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION IMAGES DE MAKITO A BBDD EMPRESA");
        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = imagesService.makitoImagesFromApi(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de images Makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //STOCK
    @GetMapping("makito/stock")
    public ResponseEntity<String> makitoStock(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION DATOS DE STOCK DE MAKITO A BBDD EMPRESA");
        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = stockService.makitoStockFromApi(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de stock de Makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //PRECIOS
    @GetMapping("makito/prices")
    public ResponseEntity<String> makitoPrices(){
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION DATOS DE PRECIOS DE MAKITO A BBDD EMPRESA");
        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = pricesService.makitoPricesFromApi(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de precios de Makito actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de precios", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("roly/images")
    public ResponseEntity<String> rolyImages(){
        String apiToken = getApiRolyToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION IMAGENES ROLY A BBDD EMPRESA");
        boolean updatedSuccessfully = imagesService.rolyImagesFromApi(apiToken);
        if (updatedSuccessfully) {
            return new ResponseEntity<>("Lista de imagenes roly actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al actualizar la lista de imagenes de roly", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TOTAL PRODUCTOS
    @GetMapping("grupoalan/makito/total-productos")
    public ResponseEntity<String> totalProductos() {
        String apiToken = getApiToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION CANTIDAD TOTAL DE PRODUCTOS");
        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = productsService.extProductsCount(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Operacion exitosa.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ERROR. No se pudo ejecutar la operacion", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("grupoalan/roly/total-productos")
    public ResponseEntity<String> totalProductosRoly() {
        String apiToken = getApiRolyToken();
        if (apiToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("OBTENCION CANTIDAD TOTAL DE PRODUCTOS");
        // Realizar la actualización de los productos desde la API
        boolean updatedSuccessfully = productsService.extProductsCountRoly(apiToken);

        if (updatedSuccessfully) {
            return new ResponseEntity<>("Operacion exitosa.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ERROR. No se pudo ejecutar la operacion", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
