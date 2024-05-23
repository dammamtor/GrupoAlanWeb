package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column
    private String ref;
    @Column
    private String name;
    @Column
    private String printcode;
    @Column
    private String length;
    @Column
    private String height;
    @Column
    private String width;
    @Column
    private String diameter;
    @Column
    private String weight;
    @Column
    private String intrastat;
    @Column
    private int pf_type;
    @Column
    private int pf_units;
    @Column
    private String pf_description;
    @Column
    private String pf_length;
    @Column
    private String pf_height;
    @Column
    private String pf_width;
    @Column
    private String pf_weight;
    @Column
    private int pi2_type;
    @Column
    private int pi2_units;
    @Column
    private String pi2_description;
    @Column
    private String pi2_length;
    @Column
    private String pi2_height;
    @Column
    private String pi2_width;
    @Column
    private String pi2_weight;
    @Column
    private int pi1_type;
    @Column
    private int pi1_units;
    @Column
    private String pi1_description;
    @Column
    private String pi1_length;
    @Column
    private String pi1_height;
    @Column
    private String pi1_width;
    @Column
    private String pi1_weight;
    @Column
    private int ptc_type;
    @Column
    private int ptc_units;
    @Column
    private String ptc_description;
    @Column
    private String ptc_length;
    @Column
    private String ptc_height;
    @Column
    private String ptc_width;
    @Column
    private String ptc_wight;
    @Column
    private String ptc_net_weight;
    @Column
    private int pallet_units;
    @Column
    private int bundle_pallets;
    @Column
    private int pallet_weight;
    @Column
    private String sizes;
    @Column(nullable = true)
    private String colors;

    //RELACIONES

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = true)
    private ProductTypes productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Markings> markings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Variants> variants;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @OrderBy("id ASC") // Orden ascendente por id de descripción, puedes cambiar ASC a DESC si deseas orden descendente
    private Set<Descriptions> descriptions;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @OrderBy("imageId ASC") // Orden ascendente por imageId, puedes cambiar ASC a DESC si deseas orden descendente
    private Set<Images> images;


    @ManyToMany
    @JoinTable(
            name = "categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Categories> categories = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "price_id", referencedColumnName = "price_id")
    private Prices prices;

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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintcode() {
        return printcode;
    }

    public void setPrintcode(String printcode) {
        this.printcode = printcode;
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

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getIntrastat() {
        return intrastat;
    }

    public void setIntrastat(String intrastat) {
        this.intrastat = intrastat;
    }

    public int getPf_type() {
        return pf_type;
    }

    public void setPf_type(int pf_type) {
        this.pf_type = pf_type;
    }

    public int getPf_units() {
        return pf_units;
    }

    public void setPf_units(int pf_units) {
        this.pf_units = pf_units;
    }

    public String getPf_description() {
        return pf_description;
    }

    public void setPf_description(String pf_description) {
        this.pf_description = pf_description;
    }

    public String getPf_length() {
        return pf_length;
    }

    public void setPf_length(String pf_length) {
        this.pf_length = pf_length;
    }

    public String getPf_height() {
        return pf_height;
    }

    public void setPf_height(String pf_height) {
        this.pf_height = pf_height;
    }

    public String getPf_width() {
        return pf_width;
    }

    public void setPf_width(String pf_width) {
        this.pf_width = pf_width;
    }

    public String getPf_weight() {
        return pf_weight;
    }

    public void setPf_weight(String pf_weight) {
        this.pf_weight = pf_weight;
    }

    public int getPi2_type() {
        return pi2_type;
    }

    public void setPi2_type(int pi2_type) {
        this.pi2_type = pi2_type;
    }

    public int getPi2_units() {
        return pi2_units;
    }

    public void setPi2_units(int pi2_units) {
        this.pi2_units = pi2_units;
    }

    public String getPi2_description() {
        return pi2_description;
    }

    public void setPi2_description(String pi2_description) {
        this.pi2_description = pi2_description;
    }

    public String getPi2_length() {
        return pi2_length;
    }

    public void setPi2_length(String pi2_length) {
        this.pi2_length = pi2_length;
    }

    public String getPi2_height() {
        return pi2_height;
    }

    public void setPi2_height(String pi2_height) {
        this.pi2_height = pi2_height;
    }

    public String getPi2_width() {
        return pi2_width;
    }

    public void setPi2_width(String pi2_width) {
        this.pi2_width = pi2_width;
    }

    public String getPi2_weight() {
        return pi2_weight;
    }

    public void setPi2_weight(String pi2_weight) {
        this.pi2_weight = pi2_weight;
    }

    public int getPi1_type() {
        return pi1_type;
    }

    public void setPi1_type(int pi1_type) {
        this.pi1_type = pi1_type;
    }

    public int getPi1_units() {
        return pi1_units;
    }

    public void setPi1_units(int pi1_units) {
        this.pi1_units = pi1_units;
    }

    public String getPi1_description() {
        return pi1_description;
    }

    public void setPi1_description(String pi1_description) {
        this.pi1_description = pi1_description;
    }

    public String getPi1_length() {
        return pi1_length;
    }

    public void setPi1_length(String pi1_length) {
        this.pi1_length = pi1_length;
    }

    public String getPi1_height() {
        return pi1_height;
    }

    public void setPi1_height(String pi1_height) {
        this.pi1_height = pi1_height;
    }

    public String getPi1_width() {
        return pi1_width;
    }

    public void setPi1_width(String pi1_width) {
        this.pi1_width = pi1_width;
    }

    public String getPi1_weight() {
        return pi1_weight;
    }

    public void setPi1_weight(String pi1_weight) {
        this.pi1_weight = pi1_weight;
    }

    public int getPtc_type() {
        return ptc_type;
    }

    public void setPtc_type(int ptc_type) {
        this.ptc_type = ptc_type;
    }

    public int getPtc_units() {
        return ptc_units;
    }

    public void setPtc_units(int ptc_units) {
        this.ptc_units = ptc_units;
    }

    public String getPtc_description() {
        return ptc_description;
    }

    public void setPtc_description(String ptc_description) {
        this.ptc_description = ptc_description;
    }

    public String getPtc_length() {
        return ptc_length;
    }

    public void setPtc_length(String ptc_length) {
        this.ptc_length = ptc_length;
    }

    public String getPtc_height() {
        return ptc_height;
    }

    public void setPtc_height(String ptc_height) {
        this.ptc_height = ptc_height;
    }

    public String getPtc_width() {
        return ptc_width;
    }

    public void setPtc_width(String ptc_width) {
        this.ptc_width = ptc_width;
    }

    public String getPtc_wight() {
        return ptc_wight;
    }

    public void setPtc_wight(String ptc_wight) {
        this.ptc_wight = ptc_wight;
    }

    public String getPtc_net_weight() {
        return ptc_net_weight;
    }

    public void setPtc_net_weight(String ptc_net_weight) {
        this.ptc_net_weight = ptc_net_weight;
    }

    public int getPallet_units() {
        return pallet_units;
    }

    public void setPallet_units(int pallet_units) {
        this.pallet_units = pallet_units;
    }

    public int getBundle_pallets() {
        return bundle_pallets;
    }

    public void setBundle_pallets(int bundle_pallets) {
        this.bundle_pallets = bundle_pallets;
    }

    public int getPallet_weight() {
        return pallet_weight;
    }

    public void setPallet_weight(int pallet_weight) {
        this.pallet_weight = pallet_weight;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public ProductTypes getProductType() {
        return productType;
    }

    public void setProductType(ProductTypes productType) {
        this.productType = productType;
    }

    public Set<Variants> getVariants() {
        return variants;
    }

    public void setVariants(Set<Variants> variants) {
        this.variants = variants;
    }

    public Set<Descriptions> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Descriptions> descriptions) {
        this.descriptions = descriptions;
    }

    public Set<Images> getImages() {
        return images;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Set<Markings> getMarkings() {
        return markings;
    }

    public void setMarkings(Set<Markings> markings) {
        this.markings = markings;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                ", printcode='" + printcode + '\'' +
                ", length='" + length + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", diameter='" + diameter + '\'' +
                ", weight='" + weight + '\'' +
                ", intrastat='" + intrastat + '\'' +
                ", pf_type=" + pf_type +
                ", pf_units=" + pf_units +
                ", pf_description='" + pf_description + '\'' +
                ", pf_length='" + pf_length + '\'' +
                ", pf_height='" + pf_height + '\'' +
                ", pf_width='" + pf_width + '\'' +
                ", pf_weight='" + pf_weight + '\'' +
                ", pi2_type=" + pi2_type +
                ", pi2_units=" + pi2_units +
                ", pi2_description='" + pi2_description + '\'' +
                ", pi2_length='" + pi2_length + '\'' +
                ", pi2_height='" + pi2_height + '\'' +
                ", pi2_width='" + pi2_width + '\'' +
                ", pi2_weight='" + pi2_weight + '\'' +
                ", pi1_type=" + pi1_type +
                ", pi1_units=" + pi1_units +
                ", pi1_description='" + pi1_description + '\'' +
                ", pi1_length='" + pi1_length + '\'' +
                ", pi1_height='" + pi1_height + '\'' +
                ", pi1_width='" + pi1_width + '\'' +
                ", pi1_weight='" + pi1_weight + '\'' +
                ", ptc_type=" + ptc_type +
                ", ptc_units=" + ptc_units +
                ", ptc_description='" + ptc_description + '\'' +
                ", ptc_length='" + ptc_length + '\'' +
                ", ptc_height='" + ptc_height + '\'' +
                ", ptc_width='" + ptc_width + '\'' +
                ", ptc_wight='" + ptc_wight + '\'' +
                ", ptc_net_weight='" + ptc_net_weight + '\'' +
                ", pallet_units=" + pallet_units +
                ", bundle_pallets=" + bundle_pallets +
                ", pallet_weight=" + pallet_weight +
                ", sizes='" + sizes + '\'' +
                ", colors='" + colors + '\'' +
                '}';
    }
}