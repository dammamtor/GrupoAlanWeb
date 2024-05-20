package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column
    private String technique_ref;

    //RELACIONES
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product;

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

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTechnique_ref() {
        return technique_ref;
    }

    public void setTechnique_ref(String technique_ref) {
        this.technique_ref = technique_ref;
    }

    @Override
    public String toString() {
        return "MarkingTechniques{" +
                "technique_id=" + technique_id +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", technique_ref='" + technique_ref + '\'' +
                '}';
    }
}
