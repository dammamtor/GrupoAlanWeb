package grupoalan.backendgalan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "variants")
public class Variants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variant_id;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private int stock_quantity;

    //RELACIONES
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    public Variants() {
    }

    public Long getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(Long variant_id) {
        this.variant_id = variant_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
