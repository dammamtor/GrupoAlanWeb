package grupoalan.backendgalan.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "markingtechniques")
public class MarkingTechniques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int technique_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    //RELACIONES
    @OneToMany(mappedBy = "markingTechnique", cascade = CascadeType.ALL)
    private Set<Products> products;

    public MarkingTechniques() {
    }

    public int getTechnique_id() {
        return technique_id;
    }

    public void setTechnique_id(int technique_id) {
        this.technique_id = technique_id;
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
