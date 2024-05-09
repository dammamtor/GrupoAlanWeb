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
    private Integer lang;
    @Column
    private String category;


    //RELACIONES
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
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

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "category_id=" + category_id +
                ", ref='" + ref + '\'' +
                ", lang=" + lang +
                ", category='" + category + '\'' +
                '}';
    }
}
