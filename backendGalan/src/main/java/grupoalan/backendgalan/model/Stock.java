package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String matnr;
    @Column(name = "unique_ref")
    private String uniqueRef;
    @Column
    private String ref;
    @Column(name = "stock_availability")
    private String stockAvailability;
    @Column
    private float stock;
    @Column
    private String date;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "variant_id") // La columna que hace referencia a la clave primaria de Variants
    private Variants variants;

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getUniqueRef() {
        return uniqueRef;
    }

    public void setUniqueRef(String uniqueRef) {
        this.uniqueRef = uniqueRef;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStockAvailability() {
        return stockAvailability;
    }

    public void setStockAvailability(String stockAvailability) {
        this.stockAvailability = stockAvailability;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Variants getVariants() {
        return variants;
    }

    public void setVariants(Variants variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", matnr='" + matnr + '\'' +
                ", uniqueRef='" + uniqueRef + '\'' +
                ", ref='" + ref + '\'' +
                ", stockAvailability='" + stockAvailability + '\'' +
                ", stock=" + stock +
                ", date='" + date + '\'' +
                '}';
    }
}
