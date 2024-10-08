package models.pupil;

import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;

import java.util.List;

public interface IPupilDAO {

    IPersonnelDAO personnelDAO = new PersonnelDAO();

    boolean createPupil(Pupil pupil);

    List<Pupil> getAllPupils();

    List<Pupil> getListPupilsByClass(String pupilId, String classId);

    Pupil getPupilsById(String id);

    boolean updatePupilStatus(String pupilId, String status);

    boolean addPupilToClass(String pupilId, String classId);

    List<Pupil> getListPupilOfTeacherBySchoolYear(String schoolYearId, String teacherId);

    List<Pupil> getPupilByClass(String classId);
    int getSumPupilInClass(String classId);

    List<Pupil> getPupilByStatus(String status);

    Pupil getPupilByUserId(String userId);

    Pupil getLatest();

    String generateId(String latestId);

    boolean updateParent(Pupil pupil);

    List<Pupil> getPupilsWithoutClass(String schoolYearId);

    boolean updatePupil(Pupil pupil);

    boolean updatePupilForTeacher(Pupil pupil);

    List<Pupil> getPupilNonUserId();

    boolean checkFirstGuardianPhoneNumberExists(String phoneNumber);

    boolean checkSecondGuardianPhoneNumberExists(String phoneNumber);

    List<Pupil> getPupilsByTeacherAndTimetable(String teacherId, String date);

    List<Pupil> getPupilBySchoolYear( String schoolYearId);


}
