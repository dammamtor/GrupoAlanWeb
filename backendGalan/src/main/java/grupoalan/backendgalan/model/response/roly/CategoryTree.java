package grupoalan.backendgalan.model.response.roly;

import java.util.List;

public class CategoryTree {
    private String id;
    private String category;
    private List<CategoryTree> subcategory;

    public CategoryTree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CategoryTree> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<CategoryTree> subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public String toString() {
        return "CategoryTree{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", subcategory=" + subcategory +
                '}';
    }
}
