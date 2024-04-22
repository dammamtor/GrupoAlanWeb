package grupoalan.backendgalan.model;

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

    //RELACIONES
    @ManyToOne
    @JoinColumn(name = "product_id")
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
}
