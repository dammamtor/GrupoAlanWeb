package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.Products;
import grupoalan.backendgalan.model.response.ProductosFiltradosResponse;
import grupoalan.backendgalan.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde localhost:4200
@RequestMapping("/grupo-alan/productos")
public class ProductsController {
    static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductsService productsService;

    @GetMapping("/obtener-productos")
    public ResponseEntity<List<Products>> obtenerProductosBD() {
        logger.info("HORA DE OBTENER LOS PRODUCTOS DE NUESTRA BD");
        List<Products> products = productsService.getAllProducts();
        if (products == null) {
            logger.error("No se pudieron obtener los productos de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("LISTA DEVUELTA: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //OBTENER PRODUCTOS PAGINADOS DESDE EL BACK
    @GetMapping("/obtener-productos-paginados")
    public ResponseEntity<List<Products>> obtenerProductosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        logger.info("HORA DE OBTENER LOS PRODUCTOS PAGINADOS DE NUESTRA BD");
        List<Products> products = productsService.getProductsByPage(page, size);
        if (products == null || products.isEmpty()) {
            logger.error("No se pudieron obtener los productos paginados de la BD de Grupo Alan");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("LISTA PAGINADA DEVUELTA: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/obtener-producto/{ref}")
    public ResponseEntity<Products> obtenerProductoPorRef(
            @PathVariable("ref") String ref
    ) {
        logger.info("HORA DE OBTENER UN PRODUCTO DE NUESTRA BD POR SU REFERENCIA");
        Products product = productsService.getProductByRef(ref);
        if (product == null) {
            logger.error("No se pudo encontrar el producto con la referencia: " + ref);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PRODUCTO DEVUELTO: " + product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/buscar-productos/{searchTerm}")
    public ResponseEntity<List<Products>> buscarProductosPorTermino(
            @PathVariable("searchTerm") String searchTerm
    ) {
        logger.info("BUSCANDO PRODUCTOS POR TÉRMINO: " + searchTerm);
        List<Products> matchingProducts = productsService.searchProducts(searchTerm);
        if (matchingProducts.isEmpty()) {
            logger.info("No se encontraron productos para el término de búsqueda: " + searchTerm);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PRODUCTOS ENCONTRADOS: " + matchingProducts);
        return new ResponseEntity<>(matchingProducts, HttpStatus.OK);
    }

    @GetMapping("/buscar-productos-por-tipo/{tipo}")
    public ResponseEntity<List<Products>> buscarProductosPorTipo(
            @PathVariable("tipo") String tipo
    ) {
        logger.info("BUSCANDO PRODUCTOS POR TIPO: " + tipo);
        List<Products> matchingProducts = productsService.searchProductsByType(tipo);
        if (matchingProducts.isEmpty()) {
            logger.info("No se encontraron productos para el tipo de búsqueda: " + tipo);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PRODUCTOS ENCONTRADOS: " + matchingProducts);
        return new ResponseEntity<>(matchingProducts, HttpStatus.OK);
    }

    //FILTRADO
    @GetMapping("/productos-filtrados")
    public ResponseEntity<ProductosFiltradosResponse> obtenerProductosFiltrados(
            @RequestParam(value = "categorias", required = false) List<String> categorias,
            @RequestParam(value = "colores", required = false) List<String> colores,
            @RequestParam(value = "tipos", required = false) List<String> tipos) {

        logger.info("Filtrando productos por categorías, colores y tipos");

        // Obtener productos filtrados
        List<Products> productosFiltrados = productsService.filtrarProductosPorCategoriasColoresYTipos(categorias, colores, tipos);

        // Obtener la cantidad total de productos asociados
        int cantidadProductosAsociados = productosFiltrados.size();

        // Registrar el número de productos devueltos
        logger.info("Se encontraron {} productos después de aplicar los filtros", cantidadProductosAsociados);

        // Crear la respuesta con la lista de productos y la cantidad total de productos asociados
        ProductosFiltradosResponse respuesta = new ProductosFiltradosResponse(productosFiltrados, cantidadProductosAsociados);

        // Retornar la respuesta
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/productos-filtrados-en-tres-pasos")
    public ResponseEntity<ProductosFiltradosResponse> obtenerProductosFiltrados(
            @RequestParam(value = "categorias", required = false) List<String> categorias,
            @RequestParam(value = "unidadesMin", required = false) float unidadesMin,
            @RequestParam(value = "unidadesMax", required = false) float unidadesMax) {

        logger.info("Filtrando productos por categorías y unidades");

        // Obtener productos filtrados
        List<Products> productosFiltrados = productsService.filtrarProductosPorCategoriasYUnidades(categorias, unidadesMin, unidadesMax);

        // Obtener la cantidad total de productos asociados
        int cantidadProductosAsociados = productosFiltrados.size();

        // Registrar el número de productos devueltos
        logger.info("Se encontraron {} productos después de aplicar los filtros", cantidadProductosAsociados);

        // Crear la respuesta con la lista de productos y la cantidad total de productos asociados
        ProductosFiltradosResponse respuesta = new ProductosFiltradosResponse(productosFiltrados, cantidadProductosAsociados);

        // Retornar la respuesta
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


}
