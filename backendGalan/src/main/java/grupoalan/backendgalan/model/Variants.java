package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "variants")
public class Variants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variant_id;
    @Column
    private String matnr;
    @Column(name = "unique_ref")
    private String uniqueRef;
    @Column
    private String ref;
    @Column
    private String color;
    @Column
    private String size;
    @Column
    private String img100;

    //RELACIONES
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Colors colorSet;

    @OneToOne(mappedBy = "variants", cascade = CascadeType.ALL)
    private Stock stock;

    public Variants() {
    }

    public Long getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(Long variant_id) {
        this.variant_id = variant_id;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getUnique_ref() {
        return uniqueRef;
    }

    public void setUnique_ref(String unique_ref) {
        this.uniqueRef = unique_ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImg100() {
        return img100;
    }

    public void setImg100(String img100) {
        this.img100 = img100;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Colors getColorSet() {
        return colorSet;
    }

    public void setColorSet(Colors colorSet) {
        this.colorSet = colorSet;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Variants{" +
                "variant_id=" + variant_id +
                ", matnr='" + matnr + '\'' +
                ", unique_ref='" + uniqueRef + '\'' +
                ", ref='" + ref + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", img100='" + img100 + '\'' +
                '}';
    }
}
