package models.schoolYear;

import java.util.List;

public interface ISchoolYearDAO {
    List<SchoolYear> getAll();
    SchoolYear getLatest();
    String createNewSchoolYear(SchoolYear schoolYear);
    SchoolYear getSchoolYear(String id);
}
