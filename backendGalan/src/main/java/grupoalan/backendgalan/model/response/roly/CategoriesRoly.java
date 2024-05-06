package grupoalan.backendgalan.model.response.roly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CategoriesRoly {

    private String category;
    private String id;
    private String models;
    private String parentCategory;
    private String parentId;
    private String brand;
    private String branchorder;
    private List<CategoriesRoly> subcategories;

    public CategoriesRoly() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBranchorder() {
        return branchorder;
    }

    public void setBranchorder(String branchorder) {
        this.branchorder = branchorder;
    }

    public List<CategoriesRoly> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoriesRoly> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public String toString() {
        return "CategoriesRoly{" +
                "category='" + category + '\'' +
                ", id='" + id + '\'' +
                ", models='" + models + '\'' +
                ", parentCategory='" + parentCategory + '\'' +
                ", parentId='" + parentId + '\'' +
                ", brand='" + brand + '\'' +
                ", branchorder='" + branchorder + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
