package models.subject;

import java.util.List;

public interface ISubjectDAO {
    boolean checkSubjectExist(String name, String gradeId);
    boolean updateStatusById(String id, String status);
    String createSubject(Subject subject);
    List<Subject> getAll();
    List<Subject> getSubjectsByStatus(String status);
    List<Subject> getSubjectsByGradeId(String gradeId);
    Subject getLastest();
    Subject getSubjectBySubjectId(String subjectId);
    String editSubject(Subject subject);
}
