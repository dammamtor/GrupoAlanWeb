package grupoalan.backendgalan.model.response.roly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {
    private List<ProductsRoly> item;

    public Items() {
    }

    public List<ProductsRoly> getItem() {
        return item;
    }

    public void setItem(List<ProductsRoly> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Items{" +
                "item=" + item +
                '}';
    }
}
