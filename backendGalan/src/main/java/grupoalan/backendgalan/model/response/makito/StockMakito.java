package grupoalan.backendgalan.model.response.makito;

public class StockMakito {
    private String matnr;
    private String unique_ref;
    private String ref;
    private String stock_availability;
    private float stock;
    private String date;

    public StockMakito() {
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getUnique_ref() {
        return unique_ref;
    }

    public void setUnique_ref(String unique_ref) {
        this.unique_ref = unique_ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStock_availability() {
        return stock_availability;
    }

    public void setStock_availability(String stock_availability) {
        this.stock_availability = stock_availability;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StockMakito{" +
                "matnr='" + matnr + '\'' +
                ", unique_ref='" + unique_ref + '\'' +
                ", ref='" + ref + '\'' +
                ", stock_availability='" + stock_availability + '\'' +
                ", stock=" + stock +
                ", date='" + date + '\'' +
                '}';
    }
}
