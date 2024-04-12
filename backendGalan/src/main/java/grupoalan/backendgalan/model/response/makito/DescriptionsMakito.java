package grupoalan.backendgalan.model.response.makito;

public class DescriptionsMakito {
    private String ref;
    private String lang;
    private String desc;
    private String commercialDesc;

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

    public String getCommercialDesc() {
        return commercialDesc;
    }

    public void setCommercialDesc(String commercialDesc) {
        this.commercialDesc = commercialDesc;
    }

    @Override
    public String toString() {
        return "Descriptions{" +
                "ref='" + ref + '\'' +
                ", lang='" + lang + '\'' +
                ", desc='" + desc + '\'' +
                ", commercialDesc='" + commercialDesc + '\'' +
                '}';
    }
}
