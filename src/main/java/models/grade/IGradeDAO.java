package models.grade;

import java.util.List;

public interface IGradeDAO {
    List<Grade> getAll();
    Grade getGrade(String gradeId);
    public String getGradeFromPupilIdAndSchoolYearId(String schoolYear_id,String pupil_id);
    public List<Grade> getGradeByUserId(String userId);
}
