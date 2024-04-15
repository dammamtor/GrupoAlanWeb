package grupoalan.backendgalan.model.response.makito;

public class MarkingTechniquesTypesMakito {
    private String techniqueRef;
    private int lang;
    private String name;

    public MarkingTechniquesTypesMakito() {
    }

    public String getTechniqueRef() {
        return techniqueRef;
    }

    public void setTechniqueRef(String techniqueRef) {
        this.techniqueRef = techniqueRef;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MarkingTechniquesTypes{" +
                "techniqueRef='" + techniqueRef + '\'' +
                ", lang=" + lang +
                ", name='" + name + '\'' +
                '}';
    }
}
