package models.week;

import java.util.Date;
import models.schoolYear.SchoolYear;

import java.util.List;

public interface IWeekDAO {
    public void generateWeeks(SchoolYear schoolYear);
    public Week getWeek(String id);
    String getYearByWeek(String id);
    public List<Week> getWeeks(String schoolYearId );
    List<Week> getWeeksFromNowUntilEndOfSchoolYear(String schoolYearId);
    String getCurrentWeek(Date date);
}
