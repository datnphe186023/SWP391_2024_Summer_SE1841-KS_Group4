/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import models.personnel.Personnel;

/**
 *
 * @author TuyenCute
 */
public interface INotificationDAO {

    Notification createNotifi(ResultSet resultSet) throws SQLException;

    List<Notification> getListNotifi();

    void createNoti(Notification notification) throws SQLException;

    Notification getLatest();

    String generateId(String latestId);

}
