package grupoalan.backendgalan.model.response.makito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ColorsMakito {
    private String color_code;
    private int lang;
    private String name;
    private int code;
    private String url;

    public ColorsMakito() {
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ColorsMakito{" +
                "color_code='" + color_code + '\'' +
                ", lang=" + lang +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", url='" + url + '\'' +
                '}';
    }
}
