package models.evaluation;

public class Evaluation {
    private String id;
    private String pupilId;
    private String dateId;
    private String evaluation;
    private String notes;

    public Evaluation(String id, String pupilId, String dateId, String evaluation,
                      String notes) {
        this.id = id;
        this.pupilId = pupilId;
        this.dateId = dateId;
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

    public String getPupilId() {
        return pupilId;
    }

    public void setPupilId(String pupilId) {
        this.pupilId = pupilId;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
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
