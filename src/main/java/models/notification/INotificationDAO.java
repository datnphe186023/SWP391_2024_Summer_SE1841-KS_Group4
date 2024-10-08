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

    boolean createNoti(Notification notification);

    boolean createNotiDetails(NotificationDetails notificationdetails);

    Notification getLatest();

    String generateId(String latestId);

    Notification getNotificationById(String id);

    List<Notification> getListNotifiByUserId(String userId);

    List<Notification> getListSentNotifiById(String id);
    
    List<Integer> getRoleSentNotifiByIdandCreatBy(String id, String create_by);
}
