package models.classes;

import models.grade.Grade;
import models.personnel.Personnel;
import models.schoolYear.SchoolYear;

public class Class {
    private String id;
    private String name;
    private Grade gradeId;
    private SchoolYear schoolYearId;
    private String status;
    private Personnel createdBy;

    public Class() {
    }

    public Class(String id, String name, Grade gradeId, SchoolYear schoolYearId, String status, Personnel createdBy) {
        this.id = id;
        this.name = name;
        this.gradeId = gradeId;
        this.schoolYearId = schoolYearId;
        this.status = status;
        this.createdBy = createdBy;
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

    public Grade getGradeId() {
        return gradeId;
    }

    public void setGradeId(Grade gradeId) {
        this.gradeId = gradeId;
    }

    public SchoolYear getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(SchoolYear schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }
}
