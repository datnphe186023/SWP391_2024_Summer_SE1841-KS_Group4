package models.week;

import models.schoolYear.SchoolYear;

import java.util.List;

public interface IWeekDAO {
    public void generateWeeks(SchoolYear schoolYear);
    public Week getWeek(String id);
    public List<Week> getWeeksFromNow();
}
