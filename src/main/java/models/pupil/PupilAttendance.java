package models.pupil;

import models.day.Day;

public class PupilAttendance {
    private String id;
    private Day day;
    private Pupil pupil;
    private String status;
    private String note;

    public PupilAttendance() {
    }

    public PupilAttendance(String id, Day day, Pupil pupil, String status, String note) {
        this.id = id;
        this.day = day;
        this.pupil = pupil;
        this.status = status;
        this.note = note;
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

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
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
