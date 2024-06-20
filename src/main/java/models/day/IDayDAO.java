package models.day;

import models.week.Week;

import java.util.List;

public interface IDayDAO {
    void generateDays(Week week);
    List<Day> getDayByWeek(String weekId);
    public List<Day> getFullDayOfWeek(String weekId);
    Day getDayByID(String dateId);
}
