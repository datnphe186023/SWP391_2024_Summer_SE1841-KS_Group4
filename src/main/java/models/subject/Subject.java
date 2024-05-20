package models.subject;

public class Subject {
    private String id;
    private String name;
    private String gradeId;
    private String description;

    public Subject(String name, String id, String gradeId, String description) {
        this.name = name;
        this.id = id;
        this.gradeId = gradeId;
        this.description = description;
    }

    public Subject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
