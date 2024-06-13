/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models.notification;

import java.util.Date;
import models.personnel.Personnel;

/**
 *
 * @author TuyenCute
 */
public interface INotificationDAO {

    Notification createNoti(String id, String heading, String details, Personnel create_by, Date create_at);

    Notification getLatest();

    String generateId(String latestId);

}
