package grupoalan.backendgalan.model.response.makito;

import java.util.Arrays;

public class MarkingTechniquesPricesMakito {
    private String techniqueRef;
    private String code;
    private double[] sections;
    private double[] prices;
    private double[] priceCols;
    private double[] priceCms;
    private int cliche;
    private int clicheRepetition;
    private int min;

    public MarkingTechniquesPricesMakito() {
    }

    public String getTechniqueRef() {
        return techniqueRef;
    }

    public void setTechniqueRef(String techniqueRef) {
        this.techniqueRef = techniqueRef;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double[] getSections() {
        return sections;
    }

    public void setSections(double[] sections) {
        this.sections = sections;
    }

    public double[] getPrices() {
        return prices;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    public double[] getPriceCols() {
        return priceCols;
    }

    public void setPriceCols(double[] priceCols) {
        this.priceCols = priceCols;
    }

    public double[] getPriceCms() {
        return priceCms;
    }

    public void setPriceCms(double[] priceCms) {
        this.priceCms = priceCms;
    }

    public int getCliche() {
        return cliche;
    }

    public void setCliche(int cliche) {
        this.cliche = cliche;
    }

    public int getClicheRepetition() {
        return clicheRepetition;
    }

    public void setClicheRepetition(int clicheRepetition) {
        this.clicheRepetition = clicheRepetition;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "MarkingTechniquesPrices{" +
                "techniqueRef='" + techniqueRef + '\'' +
                ", code='" + code + '\'' +
                ", sections=" + Arrays.toString(sections) +
                ", prices=" + Arrays.toString(prices) +
                ", priceCols=" + Arrays.toString(priceCols) +
                ", priceCms=" + Arrays.toString(priceCms) +
                ", cliche=" + cliche +
                ", clicheRepetition=" + clicheRepetition +
                ", min=" + min +
                '}';
    }
}
