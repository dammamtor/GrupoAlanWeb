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
    @Column
    private String img_min;
    @Column
    private String img_max;
    @Column
    private Integer  main;

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

    public String getImg_min() {
        return img_min;
    }

    public void setImg_min(String img_min) {
        this.img_min = img_min;
    }

    public String getImg_max() {
        return img_max;
    }

    public void setImg_max(String img_max) {
        this.img_max = img_max;
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
                ", img_min='" + img_min + '\'' +
                ", img_max='" + img_max + '\'' +
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
