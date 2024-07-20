package models.personnel;

import models.role.Role;

import java.util.List;

public interface IPersonnelDAO {
    List<Personnel> getAllPersonnels();
    List<Personnel> getPersonnelByRole(int role);
    List<Personnel> getPersonnelByNameOrId(String search);
    Personnel getPersonnel(String id);
    int getNumberOfPersonByRole(int id);
    void insertPersonnel(String id, String firstname, String lastname, int gender, String birthday, String address, String email, String phone, int role, String avatar);
    Personnel getPersonnelByUserId(String userId);
    List<Personnel> getPersonnelNonUserId();
    List<Personnel> getPersonnelByRoleAndNonUserId(int id);
    List<Personnel> getPersonnelByStatus(String status);
    List<Personnel> getPersonnelAttendance();
    boolean updatePersonnelStatus(String pId, String status);
    List<Role> getAllPersonnelRole();
    boolean updatePerson(Personnel person);
    List<Personnel> getAvailableTeachers(String schoolYearId);
    List<String> getAllStatus();
    boolean checkPersonnelPhone(String phoneNumber);
    boolean checkPersonnelEmail(String email);
    List<Personnel> getPersonnelByIdNameRoleStatus(String status, String role);
    boolean checkPhoneNumberExists(String phoneNumber);
    Personnel getTeacherByClassAndSchoolYear(String classId, String schoolYearId);
    Personnel getTeacherByClass(String classId);
}
