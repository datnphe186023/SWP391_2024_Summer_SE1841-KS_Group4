/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models.personnel;

/**
 *
 * @author TuyenCute
 */
public interface IPersonnelAttendanceDAO {

    String addAttendance(PersonnelAttendance personnelAttendance);

    PersonnelAttendance getAttendanceByPersonnelAndDay(String personnelId, String dayId);
}
