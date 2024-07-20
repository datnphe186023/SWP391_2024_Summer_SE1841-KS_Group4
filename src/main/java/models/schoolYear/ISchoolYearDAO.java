package models.schoolYear;

import java.util.Date;
import java.util.List;

public interface ISchoolYearDAO {
    List<SchoolYear> getAll();
    SchoolYear getLatest();
    String createNewSchoolYear(SchoolYear schoolYear);
    SchoolYear getSchoolYear(String id);
    SchoolYear getSchoolYearByDate(Date date);
    List<SchoolYear> getFutureSchoolYears();

    List<SchoolYear> getListSchoolYearsByPupilID(String id);

    SchoolYear getClosestSchoolYears() ;
    boolean checkPupilInClassOfSchoolYear(String pupil_id, String school_year_id);
    String editSchoolYear(SchoolYear schoolYear);

}
