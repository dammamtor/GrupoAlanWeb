package grupoalan.backendgalan.model.response.makito;

public class DescriptionsMakito {
    private String ref;
    private String lang;
    private String desc;
    private String commercial_desc;

    public DescriptionsMakito() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCommercial_desc() {
        return commercial_desc;
    }

    public void setCommercial_desc(String commercial_desc) {
        this.commercial_desc = commercial_desc;
    }

    @Override
    public String toString() {
        return "DescriptionsMakito{" +
                "ref='" + ref + '\'' +
                ", lang='" + lang + '\'' +
                ", desc='" + desc + '\'' +
                ", commercial_desc='" + commercial_desc + '\'' +
                '}';
    }
}
