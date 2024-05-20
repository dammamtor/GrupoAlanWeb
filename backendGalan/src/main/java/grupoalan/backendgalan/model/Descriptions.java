package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "descriptions")
public class Descriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ref; //ACA ENTRARIA LOS CAMPOS REF DE ROLY Y MAKITO
    @Column
    private String lang;
    @Column(name = "details", columnDefinition = "TEXT")
    private String details;
    @Column
    private String type;
    @Column
    private String comp;

    // Constructor vacío (obligatorio para JPA)
    public Descriptions() {
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Descriptions{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", lang='" + lang + '\'' +
                ", details='" + details + '\'' +
                ", type='" + type + '\'' +
                ", comp='" + comp + '\'' +
                '}';
    }
}
