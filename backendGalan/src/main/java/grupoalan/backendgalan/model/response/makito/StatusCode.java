package grupoalan.backendgalan.model.response.makito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCode {
    private int status_code;
    private List<CategoryResponse> categories;
    private List<ProductsMakito> products;
    private List<ColorsMakito> colors;
    private List<DescriptionsMakito> descriptions;
    private List<DescriptionsMaterial> descriptionsMaterials;
    private List<ImagesMakito> images;
    private List<PricesMakito> tarifs;
    private List<VariantsMakito> variants;
    private List<MarkingTechniquesMakito> techniques;
    private List<MarkingTechniquesPricesMakito> techniquesPrices;
    private List<MarkingTechniquesTypesMakito> techniquesTypes;
    private List<MarkingsMakito> markings;
    private List<StockMakito> stock;

    public StatusCode() {
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<ProductsMakito> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsMakito> products) {
        this.products = products;
    }

    public List<ColorsMakito> getColors() {
        return colors;
    }

    public void setColors(List<ColorsMakito> colors) {
        this.colors = colors;
    }

    public List<DescriptionsMakito> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DescriptionsMakito> descriptions) {
        this.descriptions = descriptions;
    }

    public List<ImagesMakito> getImages() {
        return images;
    }

    public void setImages(List<ImagesMakito> images) {
        this.images = images;
    }

    public List<PricesMakito> getTarifs() {
        return tarifs;
    }

    public void setTarifs(List<PricesMakito> tarifs) {
        this.tarifs = tarifs;
    }

    public List<VariantsMakito> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantsMakito> variants) {
        this.variants = variants;
    }

    public List<MarkingTechniquesMakito> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<MarkingTechniquesMakito> techniques) {
        this.techniques = techniques;
    }

    public List<MarkingTechniquesPricesMakito> getTechniquesPrices() {
        return techniquesPrices;
    }

    public void setTechniquesPrices(List<MarkingTechniquesPricesMakito> techniquesPrices) {
        this.techniquesPrices = techniquesPrices;
    }

    public List<MarkingTechniquesTypesMakito> getTechniquesTypes() {
        return techniquesTypes;
    }

    public void setTechniquesTypes(List<MarkingTechniquesTypesMakito> techniquesTypes) {
        this.techniquesTypes = techniquesTypes;
    }

    public List<MarkingsMakito> getMarkings() {
        return markings;
    }

    public void setMarkings(List<MarkingsMakito> markings) {
        this.markings = markings;
    }

    public List<DescriptionsMaterial> getDescriptionsMaterials() {
        return descriptionsMaterials;
    }

    public void setDescriptionsMaterials(List<DescriptionsMaterial> descriptionsMaterials) {
        this.descriptionsMaterials = descriptionsMaterials;
    }

    public List<StockMakito> getStock() {
        return stock;
    }

    public void setStock(List<StockMakito> stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "status_code=" + status_code +
                ", categories=" + categories +
                ", products=" + products +
                ", colors=" + colors +
                ", descriptions=" + descriptions +
                ", descriptionsMaterials=" + descriptionsMaterials +
                ", images=" + images +
                ", tarifs=" + tarifs +
                ", variants=" + variants +
                ", techniques=" + techniques +
                ", techniquesPrices=" + techniquesPrices +
                ", techniquesTypes=" + techniquesTypes +
                ", markings=" + markings +
                ", stock=" + stock +
                '}';
    }
}
