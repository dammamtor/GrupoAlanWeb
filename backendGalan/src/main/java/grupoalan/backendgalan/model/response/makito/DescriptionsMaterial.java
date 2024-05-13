package grupoalan.backendgalan.model.response.makito;

public class DescriptionsMaterial {
    private String ref;
    private Integer lang;
    private String type;
    private String comp;
    private String info;

    public DescriptionsMaterial() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "DescriptionsMaterial{" +
                "ref='" + ref + '\'' +
                ", lang=" + lang +
                ", type='" + type + '\'' +
                ", comp='" + comp + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
