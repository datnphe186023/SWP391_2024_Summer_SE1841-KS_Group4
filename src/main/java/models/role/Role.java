package models.role;

public class Role {
    private String id;
    private String description;

    public Role(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
