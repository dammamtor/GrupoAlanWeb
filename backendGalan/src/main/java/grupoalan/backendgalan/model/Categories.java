package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    @Column
    private String ref;
    @Column
    private String category;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "ref")
    private List<Categories> subcategories;

    //RELACIONES
    @ManyToMany(mappedBy = "categories")
    private Set<Products> products = new HashSet<>();

    public Categories() {
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public List<Categories> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Categories> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "category_id=" + category_id +
                ", ref='" + ref + '\'' +
                ", category='" + category + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
