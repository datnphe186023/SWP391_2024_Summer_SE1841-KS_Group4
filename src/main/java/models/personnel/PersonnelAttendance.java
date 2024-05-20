package models.personnel;

public class PersonnelAttendance {
    private String id;
    private String dayId;
    private String personnelId;
    private String status;
    private String note;

    public PersonnelAttendance(String id, String dayId, String personnelId,
                               String status, String note) {
        this.id = id;
        this.dayId = dayId;
        this.personnelId = personnelId;
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

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
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
