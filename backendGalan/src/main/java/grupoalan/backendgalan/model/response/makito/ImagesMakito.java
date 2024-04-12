package grupoalan.backendgalan.model.response.makito;

public class ImagesMakito {
    private String ref;
    private String imgMin;
    private String imgMax;
    private int main;

    public ImagesMakito() {
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

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "ImagesMakito{" +
                "ref='" + ref + '\'' +
                ", imgMin='" + imgMin + '\'' +
                ", imgMax='" + imgMax + '\'' +
                ", main=" + main +
                '}';
    }
}
