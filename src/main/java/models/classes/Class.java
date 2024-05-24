package models.classes;

import models.grade.Grade;
import models.personnel.Personnel;
import models.schoolYear.SchoolYear;

public class Class {
    private String id;
    private String name;
    private Grade grade;
    private Personnel teacher;
    private SchoolYear schoolYear;
    private String status;
    private Personnel createdBy;

    public Class() {
    }

    public Class(String id, String name, Grade grade, Personnel teacher, SchoolYear schoolYear, String status, Personnel createdBy) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.teacher = teacher;
        this.schoolYear = schoolYear;
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
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

    public Personnel getTeacher() {
        return teacher;
    }

    public void setTeacher(Personnel teacher) {
        this.teacher = teacher;
    }
}
