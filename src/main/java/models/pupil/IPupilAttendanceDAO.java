package models.pupil;

public interface IPupilAttendanceDAO {
    String addAttendance(PupilAttendance pupilAttendance);
    PupilAttendance getAttendanceByPupilAndDay(String pupilId, String dayId);
}
