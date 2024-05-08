package grupoalan.backendgalan.model.response.makito;

public class ImagesMakito {
    private String ref;
    private String img_min;
    private String img_max;
    private Integer main;

    public ImagesMakito() {
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

    public Integer getMain() {
        return main;
    }

    public void setMain(Integer main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "ImagesMakito{" +
                "ref='" + ref + '\'' +
                ", img_min='" + img_min + '\'' +
                ", img_max='" + img_max + '\'' +
                ", main=" + main +
                '}';
    }
}
