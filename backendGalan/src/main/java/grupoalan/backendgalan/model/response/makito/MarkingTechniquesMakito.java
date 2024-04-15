package grupoalan.backendgalan.model.response.makito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkingTechniquesMakito {
    private String ref;
    private String technique_ref;
    private String name;
    private int col_inc;
    private String notice_txt;
    private int doublepass;
    private int layer;
    private int option;
    private int mixture;
    private String system;

    public MarkingTechniquesMakito() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCol_inc() {
        return col_inc;
    }

    public void setCol_inc(int col_inc) {
        this.col_inc = col_inc;
    }

    public String getNotice_txt() {
        return notice_txt;
    }

    public void setNotice_txt(String notice_txt) {
        this.notice_txt = notice_txt;
    }

    public int getDoublepass() {
        return doublepass;
    }

    public void setDoublepass(int doublepass) {
        this.doublepass = doublepass;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getMixture() {
        return mixture;
    }

    public void setMixture(int mixture) {
        this.mixture = mixture;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "MarkingTechniquesMakito{" +
                "ref='" + ref + '\'' +
                ", technique_ref='" + technique_ref + '\'' +
                ", name='" + name + '\'' +
                ", col_inc=" + col_inc +
                ", notice_txt='" + notice_txt + '\'' +
                ", doublepass=" + doublepass +
                ", layer=" + layer +
                ", option=" + option +
                ", mixture=" + mixture +
                ", system='" + system + '\'' +
                '}';
    }
}
