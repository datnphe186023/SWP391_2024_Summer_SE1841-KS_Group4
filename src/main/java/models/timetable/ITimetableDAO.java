package models.timetable;

import java.util.List;

public interface ITimetableDAO {

    List<Timetable> getAllTimetable();

    void createTimetable(Timetable timetable);
    
}
