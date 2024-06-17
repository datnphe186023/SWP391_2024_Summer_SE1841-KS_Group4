package models.week;

import java.util.Date;
import models.schoolYear.SchoolYear;

import java.util.List;

public interface IWeekDAO {
    public void generateWeeks(SchoolYear schoolYear);
    public Week getWeek(String id);
    public List<Week> getWeeksFromNow();
    String getYearByWeek(String id);
    public List<Week> getWeeks(String schoolYearId );
}
