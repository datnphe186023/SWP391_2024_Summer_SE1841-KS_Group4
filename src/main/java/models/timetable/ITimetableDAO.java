package models.timetable;

import java.util.List;

public interface ITimetableDAO {

    List<Timetable> getAllTimetable();

    void createTimetable(Timetable timetable);
    boolean existsTimetableForClassInCurrentWeek(String classId, String dayId);
    List<TimetableDTO> getUniqueClassTimetablesWithWeeks();
    List<TimetableDTO> getListTimetableByStatus(String status);
}
