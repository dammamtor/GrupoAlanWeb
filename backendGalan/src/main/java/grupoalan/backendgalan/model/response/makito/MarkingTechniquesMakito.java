package grupoalan.backendgalan.model.response.makito;

public class MarkingTechniquesMakito {
    private String ref;
    private String techniqueRef;
    private String name;
    private int colInc;
    private String noticeTxt;
    private int doublePass;
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

    public String getTechniqueRef() {
        return techniqueRef;
    }

    public void setTechniqueRef(String techniqueRef) {
        this.techniqueRef = techniqueRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColInc() {
        return colInc;
    }

    public void setColInc(int colInc) {
        this.colInc = colInc;
    }

    public String getNoticeTxt() {
        return noticeTxt;
    }

    public void setNoticeTxt(String noticeTxt) {
        this.noticeTxt = noticeTxt;
    }

    public int getDoublePass() {
        return doublePass;
    }

    public void setDoublePass(int doublePass) {
        this.doublePass = doublePass;
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
        return "MarkingTechniques{" +
                "ref='" + ref + '\'' +
                ", techniqueRef='" + techniqueRef + '\'' +
                ", name='" + name + '\'' +
                ", colInc=" + colInc +
                ", noticeTxt='" + noticeTxt + '\'' +
                ", doublePass=" + doublePass +
                ", layer=" + layer +
                ", option=" + option +
                ", mixture=" + mixture +
                ", system='" + system + '\'' +
                '}';
    }
}
