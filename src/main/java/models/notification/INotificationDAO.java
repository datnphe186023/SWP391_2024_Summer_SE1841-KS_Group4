/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models.notification;

import java.sql.ResultSet;

/**
 *
 * @author TuyenCute
 */
public interface INotificationDAO {

    Notification createNoti(ResultSet rs);

    Notification getLatest();

    String generateId(String latestId);

}
