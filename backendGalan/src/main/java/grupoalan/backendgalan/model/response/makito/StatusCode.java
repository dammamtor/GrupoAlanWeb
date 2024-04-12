package grupoalan.backendgalan.model.response.makito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCode {
    private int status_code;
    private List<CategoryResponse> categories;
    private List<ProductsMakito> products;
    private List<ColorsMakito> colors;

    public StatusCode() {
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<ProductsMakito> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsMakito> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "status_code=" + status_code +
                ", categories=" + categories +
                ", products=" + products +
                '}';
    }
}
