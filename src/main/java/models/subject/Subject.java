package models.subject;

import models.grade.Grade;

public class Subject {
    private String id;
    private String name;
    private Grade grade;
    private String description;
    private String status;
    public Subject(String id, String name, Grade grade, String description,String status) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.description = description;
        this.status = status;
    }

    public Subject() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
