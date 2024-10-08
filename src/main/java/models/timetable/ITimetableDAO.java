package models.timetable;

import java.util.List;

public interface ITimetableDAO {

    List<Timetable> getAllTimetable();

    String createTimetable(String sql);
    boolean existsTimetableForClassInCurrentWeek(String classId, String dayId);
    List<TimetableDTO> getUniqueClassTimetablesWithWeeks();
    List<TimetableDTO> getListTimetableByStatusAndCreatedBy(String status, String createdBy);
    List<TimetableDTO> getListTimetableByStatus(String status);
    String generateTimetableId(String latestId);
    List<Timetable> getTimetableByClassAndWeek(String classId, String weekId,String status);
    boolean updateTimetableStatus(String classId, String weekId, String status, String note,String oldStatus);
    List<TimetableDTO> getTimetableByClassAndWeekAndCreateBy(String create_by);
    List<TimetableDTO> getTimetableByClassAndWeekAndTeacher(String teacher);
    void updateTeacherOfTimetable(String classId, String teacherId);
    String getTeacherByDayId(String dayId);
    String getLatestTimetableId();
    String updateTimetableOfClass(String teacherId, String classId, String dayId);
    List<Timetable> getTeacherTimetable(String teacherId, String weekId);
}
