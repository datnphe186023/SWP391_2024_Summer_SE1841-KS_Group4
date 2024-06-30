package models.day;

import models.week.Week;

import java.util.List;

public interface IDayDAO {
    void generateDays(Week week);
    List<Day> getDayByWeek(String weekId);
    List<Day> getFullDayOfWeek(String weekId);
    Day getDayByID(String dateId);
    Day getDayByDate(String date);
}
