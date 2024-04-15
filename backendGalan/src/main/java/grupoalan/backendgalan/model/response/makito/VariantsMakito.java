package grupoalan.backendgalan.model.response.makito;

public class VariantsMakito {
    private String matnr;
    private String unique_ref;
    private String ref;
    private String color;
    private String size;
    private String img100;

    public VariantsMakito() {
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getUnique_ref() {
        return unique_ref;
    }

    public void setUnique_ref(String unique_ref) {
        this.unique_ref = unique_ref;
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

    @Override
    public String toString() {
        return "VariantsMakito{" +
                "matnr='" + matnr + '\'' +
                ", unique_ref='" + unique_ref + '\'' +
                ", ref='" + ref + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", img100='" + img100 + '\'' +
                '}';
    }
}
