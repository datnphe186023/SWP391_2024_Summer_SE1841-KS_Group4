package models.classes;

public class Class {
    private String id;
    private String name;
    private String gradeId;
    private String schoolYearId;
    private String createdBy;

    public Class(String id, String name, String gradeId, String schoolYearId,
                 String createdBy) {
        this.id = id;
        this.name = name;
        this.gradeId = gradeId;
        this.schoolYearId = schoolYearId;
        this.createdBy = createdBy;
    }

    public Class() {
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

    public String getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(String schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
