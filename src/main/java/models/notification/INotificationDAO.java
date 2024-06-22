/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author TuyenCute
 */
public interface INotificationDAO {

    Notification createNotifi(ResultSet resultSet) throws SQLException;

    List<Notification> getListNotifiByRoleId(int role_id);

    void createNoti(Notification notification, NotificationDetails notificationdetails) throws SQLException;

    Notification getLatest();

    String generateId(String latestId);

    Notification getNotificationById(String id);
}
