package models.classes;

public class ClassDetails {
    private String id;
    private String pupilId;
    private String classId;
    private String teacherId;

    public ClassDetails(String id, String pupilId, String classId, String teacherId) {
        this.id = id;
        this.pupilId = pupilId;
        this.classId = classId;
        this.teacherId = teacherId;
    }

    public ClassDetails() {
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
