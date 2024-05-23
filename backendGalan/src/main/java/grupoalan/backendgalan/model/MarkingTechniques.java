package grupoalan.backendgalan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "markingtechniques")
public class MarkingTechniques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technique_id;
    @Column
    private String ref;
    @Column
    private String technique_ref;
    @Column
    private String name;
    @Column
    private int col_inc;
    @Column
    private String notice_txt;
    @Column
    private int doublepass;
    @Column
    private int layer;
    @Column(name = "`option`")
    private int option;
    @Column
    private int mixture;
    @Column(name = "`system`")
    private String system;

    //RELACIONES
    @OneToMany(mappedBy = "markingTechnique", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Markings> marking;



    public MarkingTechniques() {
    }

    public Long getTechnique_id() {
        return technique_id;
    }

    public void setTechnique_id(Long technique_id) {
        this.technique_id = technique_id;
    }

    public String getTechnique_ref() {
        return technique_ref;
    }

    public void setTechnique_ref(String technique_ref) {
        this.technique_ref = technique_ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCol_inc() {
        return col_inc;
    }

    public void setCol_inc(int col_inc) {
        this.col_inc = col_inc;
    }

    public String getNotice_txt() {
        return notice_txt;
    }

    public void setNotice_txt(String notice_txt) {
        this.notice_txt = notice_txt;
    }

    public int getDoublepass() {
        return doublepass;
    }

    public void setDoublepass(int doublepass) {
        this.doublepass = doublepass;
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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Set<Markings> getMarking() {
        return marking;
    }

    public void setMarking(Set<Markings> marking) {
        this.marking = marking;
    }

    @Override
    public String toString() {
        return "MarkingTechniques{" +
                "technique_id=" + technique_id +
                ", ref='" + ref + '\'' +
                ", technique_ref='" + technique_ref + '\'' +
                ", name='" + name + '\'' +
                ", col_inc=" + col_inc +
                ", notice_txt='" + notice_txt + '\'' +
                ", doublepass=" + doublepass +
                ", layer=" + layer +
                ", option=" + option +
                ", mixture=" + mixture +
                ", system='" + system + '\'' +
                '}';
    }
}
