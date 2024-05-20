package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;
    @Column
    private String ref; //esto es para MAKITO
    @Column(name = "img_min") // Cambiamos el nombre de la columna para que coincida con el nombre de la propiedad
    private String imgMin;
    @Column(name = "img_max")
    private String imgMax;
    @Column
    private Integer main;

    //ROLY
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String productImage;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String modelImage;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String childImage;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detailsImages;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String viewsImages;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String otherImages;

    //RELACIONES
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product;

    public Images() {
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getImgMin() {
        return imgMin;
    }

    public void setImgMin(String imgMin) {
        this.imgMin = imgMin;
    }

    public String getImgMax() {
        return imgMax;
    }

    public void setImgMax(String imgMax) {
        this.imgMax = imgMax;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public String getChildImage() {
        return childImage;
    }

    public void setChildImage(String childImage) {
        this.childImage = childImage;
    }

    public String getDetailsImages() {
        return detailsImages;
    }

    public void setDetailsImages(String detailsImages) {
        this.detailsImages = detailsImages;
    }

    public String getViewsImages() {
        return viewsImages;
    }

    public void setViewsImages(String viewsImages) {
        this.viewsImages = viewsImages;
    }

    public String getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(String otherImages) {
        this.otherImages = otherImages;
    }

    public Integer getMain() {
        return main;
    }

    public void setMain(Integer main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Images{" +
                "imageId=" + imageId +
                ", ref='" + ref + '\'' +
                ", img_min='" + imgMin + '\'' +
                ", img_max='" + imgMax + '\'' +
                ", main=" + main +
                ", productImage='" + productImage + '\'' +
                ", modelImage='" + modelImage + '\'' +
                ", childImage='" + childImage + '\'' +
                ", detailsImages='" + detailsImages + '\'' +
                ", viewsImages='" + viewsImages + '\'' +
                ", otherImages='" + otherImages + '\'' +
                '}';
    }
}
