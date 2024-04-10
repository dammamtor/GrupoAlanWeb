package grupoalan.backendgalan.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "producttypes")
public class ProductTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_type_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    //RELACIONES
    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    private Set<Products> products;

    public ProductTypes() {
    }

    public Long getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(Long product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
}
