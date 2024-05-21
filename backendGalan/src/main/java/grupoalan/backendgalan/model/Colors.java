package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colors")
public class Colors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private Integer lang;
    @Column
    private String url;

    //RELACIONES
    @OneToMany(mappedBy = "colorSet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Variants> variants;

    public Colors() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public Set<Variants> getVariants() {
        return variants;
    }

    public void setVariants(Set<Variants> variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "Colors{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", lang=" + lang +
                ", url='" + url + '\'' +
                '}';
    }
}

