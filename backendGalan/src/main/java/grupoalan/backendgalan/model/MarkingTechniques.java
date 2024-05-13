package grupoalan.backendgalan.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "markingtechniques")
public class MarkingTechniques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technique_id;
    @Column
    private String name;
    @Column
    private String ref;

    //RELACIONES
    @OneToMany(mappedBy = "markingTechnique", cascade = CascadeType.ALL)
    private Set<Products> products;

    public MarkingTechniques() {
    }

    public Long getTechnique_id() {
        return technique_id;
    }

    public void setTechnique_id(Long technique_id) {
        this.technique_id = technique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "MarkingTechniques{" +
                "technique_id=" + technique_id +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}
