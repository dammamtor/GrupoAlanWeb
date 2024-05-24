package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "markingstranslations")
public class MarkingsTranslations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer print_area_id;
    @Column
    private Integer lang;
    @Column
    private String txt;

    //RELACIONES
    @OneToMany(mappedBy = "markingsTranslations", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Markings> markings;

    public MarkingsTranslations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrint_area_id() {
        return print_area_id;
    }

    public void setPrint_area_id(Integer print_area_id) {
        this.print_area_id = print_area_id;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Set<Markings> getMarkings() {
        return markings;
    }

    public void setMarkings(Set<Markings> markings) {
        this.markings = markings;
    }

    @Override
    public String toString() {
        return "MarkingsTranslations{" +
                "id=" + id +
                ", print_area_id=" + print_area_id +
                ", lang=" + lang +
                ", txt='" + txt + '\'' +
                '}';
    }
}
