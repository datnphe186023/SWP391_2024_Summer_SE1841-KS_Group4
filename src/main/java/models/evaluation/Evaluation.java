package models.evaluation;

import models.day.Day;
import models.pupil.Pupil;

public class Evaluation {
    private String id;
    private Pupil pupil;
    private Day date;
    private String evaluation;
    private String notes;

    public Evaluation(String id, Pupil pupil, Day date, String evaluation, String notes) {
        this.id = id;
        this.pupil = pupil;
        this.date = date;
        this.evaluation = evaluation;
        this.notes = notes;
    }

    public Evaluation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public Day getDate() {
        return date;
    }

    public void setDate(Day date) {
        this.date = date;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
