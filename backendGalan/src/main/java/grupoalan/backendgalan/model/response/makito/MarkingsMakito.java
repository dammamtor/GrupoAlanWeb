package grupoalan.backendgalan.model.response.makito;

public class MarkingsMakito {
    private String ref;
    private String technique_ref;
    private int print_area_id;
    private String max_colors;
    private String position;
    private int width;
    private int height;
    private String area_img;
    private Integer lang;
    private String txt;

    public MarkingsMakito() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTechnique_ref() {
        return technique_ref;
    }

    public void setTechnique_ref(String technique_ref) {
        this.technique_ref = technique_ref;
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

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "MarkingsMakito{" +
                "ref='" + ref + '\'' +
                ", technique_ref='" + technique_ref + '\'' +
                ", print_area_id=" + print_area_id +
                ", max_colors='" + max_colors + '\'' +
                ", position='" + position + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", area_img='" + area_img + '\'' +
                '}';
    }
}
