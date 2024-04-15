package grupoalan.backendgalan.model.response.makito;

public class MarkingTechniquesTypesMakito {
    private String technique_ref;
    private int lang;
    private String name;

    public MarkingTechniquesTypesMakito() {
    }

    public String getTechnique_ref() {
        return technique_ref;
    }

    public void setTechnique_ref(String technique_ref) {
        this.technique_ref = technique_ref;
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
        return "MarkingTechniquesTypesMakito{" +
                "technique_ref='" + technique_ref + '\'' +
                ", lang=" + lang +
                ", name='" + name + '\'' +
                '}';
    }
}
