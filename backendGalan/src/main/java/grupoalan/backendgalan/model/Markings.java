package grupoalan.backendgalan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "markings")
public class Markings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ref; //ACA ENTRARIA LOS CAMPOS EN COMUN CON MAKITO
    @Column(nullable = false)
    private int print_area_id;
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
    private Products product;

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

    public int getPrint_area_id() {
        return print_area_id;
    }

    public void setPrint_area_id(int print_area_id) {
        this.print_area_id = print_area_id;
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

    @Override
    public String toString() {
        return "Markings{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", print_area_id=" + print_area_id +
                ", max_colors='" + max_colors + '\'' +
                ", position='" + position + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", area_img='" + area_img + '\'' +
                '}';
    }
}
