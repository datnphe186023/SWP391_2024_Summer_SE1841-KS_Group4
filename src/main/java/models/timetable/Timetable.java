package models.timetable;

public class Timetable {
    private String id;
    private String classId;
    private String timeslotId;
    private String dateId;
    private String subjectId;
    private String createdBy;
    private String status;
    private String note;
    private String teacherId;

    public Timetable() {
    }

    public Timetable(String id, String classId, String timeslotId, String dateId,
                     String subjectId, String createdBy, String status, String note,
                     String teacherId) {
        this.id = id;
        this.classId = classId;
        this.timeslotId = timeslotId;
        this.dateId = dateId;
        this.subjectId = subjectId;
        this.createdBy = createdBy;
        this.status = status;
        this.note = note;
        this.teacherId = teacherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(String timeslotId) {
        this.timeslotId = timeslotId;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
