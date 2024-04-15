package grupoalan.backendgalan.model.response.makito;

public class MarkingsMakito {
    private String ref;
    private String techniqueRef;
    private int printAreaId;
    private String maxColors;
    private String position;
    private int width;
    private int height;
    private String areaImg;

    public MarkingsMakito() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTechniqueRef() {
        return techniqueRef;
    }

    public void setTechniqueRef(String techniqueRef) {
        this.techniqueRef = techniqueRef;
    }

    public int getPrintAreaId() {
        return printAreaId;
    }

    public void setPrintAreaId(int printAreaId) {
        this.printAreaId = printAreaId;
    }

    public String getMaxColors() {
        return maxColors;
    }

    public void setMaxColors(String maxColors) {
        this.maxColors = maxColors;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAreaImg() {
        return areaImg;
    }

    public void setAreaImg(String areaImg) {
        this.areaImg = areaImg;
    }

    @Override
    public String toString() {
        return "Markings{" +
                "ref='" + ref + '\'' +
                ", techniqueRef='" + techniqueRef + '\'' +
                ", printAreaId=" + printAreaId +
                ", maxColors='" + maxColors + '\'' +
                ", position='" + position + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", areaImg='" + areaImg + '\'' +
                '}';
    }
}
