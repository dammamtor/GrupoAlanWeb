package grupoalan.backendgalan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "descriptions")
public class Descriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ref; //ACA ENTRARIA LOS CAMPOS REF DE ROLY Y MAKITO

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    // Constructor vacío (obligatorio para JPA)
    public Descriptions() {
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
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

    @Override
    public String toString() {
        return "Descriptions{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
