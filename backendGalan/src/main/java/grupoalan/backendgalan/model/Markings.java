package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "markings")
public class Markings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ref; //ACA ENTRARIA LOS CAMPOS EN COMUN CON MAKITO
    @Column(name = "technique_ref")
    private String techniqueRef;
    @Column(name = "print_area_id")
    private int printAreaId;
    @Column(nullable = false)
    private String max_colors;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private int width;
    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private String area_img;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product;

    @ManyToOne
    @JoinColumn(name = "technique_id")
    @JsonIgnore
    private MarkingTechniques markingTechnique;

    @ManyToOne
    @JoinColumn(name = "marking_translation_id") // Columna de unión en la tabla Markings
    private MarkingsTranslations markingsTranslations;

    public Markings() {
    }

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

    public int getPrintAreaId() {
        return printAreaId;
    }

    public void setPrintAreaId(int printAreaId) {
        this.printAreaId = printAreaId;
    }

    public String getMax_colors() {
        return max_colors;
    }

    public void setMax_colors(String max_colors) {
        this.max_colors = max_colors;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getArea_img() {
        return area_img;
    }

    public void setArea_img(String area_img) {
        this.area_img = area_img;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public MarkingTechniques getMarkingTechniques() {
        return markingTechnique;
    }

    public void setMarkingTechniques(MarkingTechniques markingTechniques) {
        this.markingTechnique = markingTechniques;
    }

    public String getTechniqueRef() {
        return techniqueRef;
    }

    public void setTechniqueRef(String techniqueRef) {
        this.techniqueRef = techniqueRef;
    }

    public MarkingsTranslations getMarkingsTranslations() {
        return markingsTranslations;
    }

    public void setMarkingsTranslations(MarkingsTranslations markingsTranslations) {
        this.markingsTranslations = markingsTranslations;
    }

    @Override
    public String toString() {
        return "Markings{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", technique_ref='" + techniqueRef + '\'' +
                ", print_area_id=" + printAreaId +
                ", max_colors='" + max_colors + '\'' +
                ", position='" + position + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", area_img='" + area_img + '\'' +
                '}';
    }
}
