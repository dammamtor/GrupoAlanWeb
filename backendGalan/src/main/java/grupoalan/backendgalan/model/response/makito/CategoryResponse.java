package grupoalan.backendgalan.model.response.makito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse {
    private String ref;
    private int lang;
    private String category;

    public CategoryResponse() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "ref='" + ref + '\'' +
                ", lang=" + lang +
                ", category='" + category + '\'' +
                '}';
    }
}
