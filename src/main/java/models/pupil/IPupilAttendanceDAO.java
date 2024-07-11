package models.pupil;

import java.util.List;

public interface IPupilAttendanceDAO {
    String addAttendance(PupilAttendance pupilAttendance);
    PupilAttendance getAttendanceByPupilAndDay(String pupilId, String dayId);
    boolean checkAttendanceByDay(List<Pupil> listPupil,String dayId);
}
