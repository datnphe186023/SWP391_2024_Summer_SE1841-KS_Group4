package models.classes;

import java.util.List;

public interface IClassDAO {
    List<Class> getBySchoolYear(String schoolYearId);
    Class getClassById(String id);
    Class getTeacherClassByYear(String year, String teacherId);
    String createNewClass(Class c);
    List<Class> getByStatus(String status, String schoolYearId);
    String reviewClass(String newStatus, String id);
    List<Class> getClassByGradeId(String gradeId);
    boolean moveOutClassForPupil(String oldClassId, String newClassId, String pupilId);
    boolean moveOutClassForTeacher(String teacherId, String classId);
}
