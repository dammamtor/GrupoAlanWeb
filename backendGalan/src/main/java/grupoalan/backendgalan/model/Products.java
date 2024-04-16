package grupoalan.backendgalan.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name; //ACA ENTRARIA LOS CAMPOS NAME E ITEMNAME DE AMBAS APIS

    @Column(nullable = false)
    private String ref; //ACA ENTRARIA LOS CAMPOS REF E ITEMCODE DE AMBAS APIS

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private BigDecimal price;

    @Column(name = "is_available", nullable = true)
    private boolean available;

    // Campos adicionales comunes a ambas APIs
    @Column(nullable = true)
    private String  weight;

    @Column(nullable = true)
    private String length;

    @Column(nullable = true)
    private String height;

    @Column(nullable = true)
    private String width;

    @Column(nullable = true)
    private String measures; // Medidas del producto de ROLY

    //RELACIONES

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = true)
    private ProductTypes productType;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "technique_id", nullable = true)
    private MarkingTechniques markingTechnique;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Variants> variants;

    // Constructor vacío (obligatorio para JPA)
    public Products() {
    }

    // Getters y Setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ProductTypes getProductType() {
        return productType;
    }

    public void setProductType(ProductTypes productType) {
        this.productType = productType;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public MarkingTechniques getMarkingTechnique() {
        return markingTechnique;
    }

    public void setMarkingTechnique(MarkingTechniques markingTechnique) {
        this.markingTechnique = markingTechnique;
    }

    public Set<Variants> getVariants() {
        return variants;
    }

    public void setVariants(Set<Variants> variants) {
        this.variants = variants;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", weight='" + weight + '\'' +
                ", length='" + length + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", measures='" + measures + '\'' +
                ", productType=" + productType +
                ", category=" + category +
                ", markingTechnique=" + markingTechnique +
                ", variants=" + variants +
                '}';
    }
}