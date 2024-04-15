package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.*;
import grupoalan.backendgalan.model.response.makito.ProductsMakito;
import grupoalan.backendgalan.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    private VariantsService variantsService;

    //ESTE CONTROLLER ESTA EXCLUSIVAMENTE DEDICADO A LA OBTENCION DE BBDD DE LAS APIS EXTERNAS

    //CATEGORIAS
    @GetMapping("/makito/categories")
    public ResponseEntity<List<Categories>> makitoCategories() {
        logger.info("OBTENCION CATEGORIAS MAKITO A BBDD EMPRESA");
        List<Categories> categories = categoriesService.makitoCategoriesFromApi();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/roly/categories")
    public ResponseEntity<List<Categories>> rolyCategories() {
        logger.info("OBTENCION CATEGORIAS ROLY A BBDD EMPRESA");
        List<Categories> categories = categoriesService.rolyCategoriesFromApi();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //PRODUCTS
    @GetMapping("/makito/products")
    public ResponseEntity<List<Products>> makitoProducts() {
        logger.info("OBTENCION PRODUCTOS MAKITO A BBDD EMPRESA");
        List<Products> products = productsService.makitoProductsFromApi();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/roly/products")
    public ResponseEntity<List<Products>> rolyProducts() {
        logger.info("OBTENCION PRODUCTOS ROLY A BBDD EMPRESA");
        List<Products> products = productsService.rolyProductsFromApi();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //COLORS
    @GetMapping("/makito/colors")
    public ResponseEntity<List<Colors>> makitoColors(){
        logger.info("OBTENCION COLORES MAKITO A BBDD EMPRESA");
        List<Colors> colors = colorService.makitoColorsFromApi();
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    //DESCRIPTIONS
    @GetMapping("makito/descriptions")
    public ResponseEntity<List<Descriptions>> makitoDescriptions(){
        logger.info("OBTENCION DESCRIPCIONES MAKITO A BBDD EMPRESA");
        List<Descriptions> descriptions = descriptionService.makitoDescriptionsFromApi();
        return new ResponseEntity<>(descriptions, HttpStatus.OK);
    }

    //MARKINGS
    @GetMapping("makito/markings")
    public ResponseEntity<List<Markings>> makitoMarkings(){
        logger.info("OBTENCION MARKINGS MAKITO A BBDD EMPRESA");
        List<Markings> markings = markingService.makitoMarkingsFromApi();
        return new ResponseEntity<>(markings, HttpStatus.OK);
    }

    //MARKING TECHNIQUES
    @GetMapping("makito/techniques")
    public ResponseEntity<List<MarkingTechniques>> makitoTechniques(){
        logger.info("OBTENCION MARKING TECHNIQUES A BBDD EMPRESA");
        List<MarkingTechniques> markingTechniques = markingTechniquesService.makitoMarkingTechniquesFromApi();
        return new ResponseEntity<>(markingTechniques, HttpStatus.OK);

    }

    //VARIANTS
    @GetMapping("makito/variants")
    public ResponseEntity<List<Variants>> makitoVariants(){
        logger.info("OBTENCION VARIANTS DE MAKITO A BBDD EMPRESA");
        List<Variants> variants = variantsService.makitoVariantsTechniquesFromApi();
        return new ResponseEntity<>(variants, HttpStatus.OK);
    }
}
