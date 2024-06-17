package models.timeslot;

import java.util.List;

public interface ITimeslotDAO {
    List<Timeslot> getAllTimeslots();
    Timeslot getTimeslotById(String timeslotId);
    List<Timeslot> getFoodTimeslots();
}
