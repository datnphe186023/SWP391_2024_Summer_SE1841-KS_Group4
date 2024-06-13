package models.grade;

import java.util.List;

public interface IGradeDAO {
    List<Grade> getAll();
    Grade getGrade(String gradeId);
}
