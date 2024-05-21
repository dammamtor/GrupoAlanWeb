package grupoalan.backendgalan.model.response;

import grupoalan.backendgalan.model.Products;

import java.util.List;

public class ProductosFiltradosResponse {
    private List<Products> productos;
    private int cantidad;

    public ProductosFiltradosResponse(List<Products> productos, int cantidad) {
        this.productos = productos;
        this.cantidad = cantidad;
    }

    public List<Products> getProductos() {
        return productos;
    }

    public void setProductos(List<Products> productos) {
        this.productos = productos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
