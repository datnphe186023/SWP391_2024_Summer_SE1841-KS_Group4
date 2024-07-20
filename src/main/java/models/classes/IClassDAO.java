package models.classes;

import java.util.List;

public interface IClassDAO {

    List<Class> getBySchoolYear(String schoolYearId);

    Class getClassById(String id);

    Class getTeacherClassByYear(String year, String teacherId);

    String createNewClass(Class c);

    List<Class> getByStatus(String status, String schoolYearId);

    String reviewClass(String newStatus, String id);

    List<Class> getClassByGradeIdAndSchoolYearAndStatus(String gradeId, String schoolYearId, String status);

    List<Class> getClassByGradeId(String gradeId);

    boolean moveOutClassForPupil(String oldClassId, String newClassId, String pupilId);

    List<Class> getClassesByGradeAndSchoolYear(String classId, String gradeId, String schoolYearId);

    String assignTeacherToClass(String teacherId, String classId);
    
    Class getClassByPupilIdAndSchoolYear(String id, String schoolYear);

    String getClassNameByTeacherAndTimetable(String teacherId, String date);
    Class getClassNameByTeacher(String teacherId);
}
