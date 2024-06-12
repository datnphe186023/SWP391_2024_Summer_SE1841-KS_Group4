package models.timetable;

import java.util.List;

public interface ITimetableDAO {
    void insertTimetable(String classId, String timeslotId, String dateId, String subjectId,
                         String createdBy, String status, String note, String teacherId);

    List<Timetable> getAllTimetable();
}
