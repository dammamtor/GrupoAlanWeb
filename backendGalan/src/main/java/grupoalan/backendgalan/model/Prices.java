package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "prices")
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long price_id;
    @Column
    private String ref;
    @Column
    private double section1;
    @Column
    private double price1;
    @Column
    private double section2;
    @Column
    private double price2;
    @Column
    private double section3;
    @Column
    private double price3;
    @Column
    private double section4;
    @Column
    private double price4;
    @Column
    private double section5;
    @Column
    private double price5;
    @Column
    private double section6;
    @Column
    private double price6;

    //RELACIONES
    @OneToOne(mappedBy = "prices")
    @JsonIgnore
    private Products products;

    public Prices() {
    }

    public Long getPrice_id() {
        return price_id;
    }

    public void setPrice_id(Long price_id) {
        this.price_id = price_id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public double getSection1() {
        return section1;
    }

    public void setSection1(double section1) {
        this.section1 = section1;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getSection2() {
        return section2;
    }

    public void setSection2(double section2) {
        this.section2 = section2;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getSection3() {
        return section3;
    }

    public void setSection3(double section3) {
        this.section3 = section3;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public double getSection4() {
        return section4;
    }

    public void setSection4(double section4) {
        this.section4 = section4;
    }

    public double getPrice4() {
        return price4;
    }

    public void setPrice4(double price4) {
        this.price4 = price4;
    }

    public double getSection5() {
        return section5;
    }

    public void setSection5(double section5) {
        this.section5 = section5;
    }

    public double getPrice5() {
        return price5;
    }

    public void setPrice5(double price5) {
        this.price5 = price5;
    }

    public double getSection6() {
        return section6;
    }

    public void setSection6(double section6) {
        this.section6 = section6;
    }

    public double getPrice6() {
        return price6;
    }

    public void setPrice6(double price6) {
        this.price6 = price6;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "price_id=" + price_id +
                ", ref='" + ref + '\'' +
                ", section1=" + section1 +
                ", price1=" + price1 +
                ", section2=" + section2 +
                ", price2=" + price2 +
                ", section3=" + section3 +
                ", price3=" + price3 +
                ", section4=" + section4 +
                ", price4=" + price4 +
                ", section5=" + section5 +
                ", price5=" + price5 +
                ", section6=" + section6 +
                ", price6=" + price6 +
                '}';
    }
}
