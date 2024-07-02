package models.evaluation;

import models.pupil.Pupil;

import java.util.Date;

public class HealthCheckUp {
    private String id;
    private Pupil pupil;
    private Date checkUpDate;
    private float height;
    private float weight;
    private String averageDevelopmentStage;
    private String bloodPressure;
    private String teeth;
    private String eyes;
    private String earNoseThroat;
    private String notes;

    public HealthCheckUp(String id, Pupil pupil, Date checkUpDate, float height, float weight,
                         String averageDevelopmentStage, String bloodPressure, String teeth, String eyes,String earNoseThroat, String notes) {
        this.id = id;
        this.pupil = pupil;
        this.checkUpDate = checkUpDate;
        this.height = height;
        this.weight = weight;
        this.averageDevelopmentStage = averageDevelopmentStage;
        this.bloodPressure = bloodPressure;
        this.teeth = teeth;
        this.eyes = eyes;
        this.earNoseThroat = earNoseThroat;
        this.notes = notes;
    }

    public String getEarNoseThroat() {
        return earNoseThroat;
    }

    public void setEarNoseThroat(String earNoseThroat) {
        this.earNoseThroat = earNoseThroat;
    }

    public HealthCheckUp() {
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

    public Date getCheckUpDate() {
        return checkUpDate;
    }

    public void setCheckUpDate(Date checkUpDate) {
        this.checkUpDate = checkUpDate;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getAverageDevelopmentStage() {
        return averageDevelopmentStage;
    }

    public void setAverageDevelopmentStage(String averageDevelopmentStage) {
        this.averageDevelopmentStage = averageDevelopmentStage;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getTeeth() {
        return teeth;
    }

    public void setTeeth(String teeth) {
        this.teeth = teeth;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
