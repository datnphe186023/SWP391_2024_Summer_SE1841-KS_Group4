package models.personnel;

import models.day.Day;

public class PersonnelAttendance {
    private String id;
    private Day day;
    private Personnel personnel;
    private String status;
    private String note;

    public PersonnelAttendance(String id, Day day, Personnel personnel, String status, String note) {
        this.id = id;
        this.day = day;
        this.personnel = personnel;
        this.status = status;
        this.note = note;
    }

    public PersonnelAttendance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
