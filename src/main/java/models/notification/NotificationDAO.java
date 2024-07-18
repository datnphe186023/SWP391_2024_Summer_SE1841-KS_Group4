/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.personnel.Personnel;
import static models.pupil.IPupilDAO.personnelDAO;
import utils.DBContext;

/**
 *
 * @author TuyenCute
 */
public class NotificationDAO extends DBContext implements INotificationDAO {

    @Override
    public Notification createNotifi(ResultSet resultSet) throws SQLException {
        try {
            Notification notifi = new Notification();
            notifi.setId(resultSet.getString(1));
            notifi.setHeading(resultSet.getString(2));
            notifi.setDetails(resultSet.getString(3));
            Personnel personnel = personnelDAO.getPersonnel(resultSet.getString(4));
            notifi.setCreatedBy(personnel);
            notifi.setCreatedAt(resultSet.getDate(5));
            return notifi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification getLatest() {
        String sql = "select TOP 1 * from [Notifications] order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createNotifi(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "N" + result;
    }

    @Override
    public boolean createNoti(Notification notification) {
        String sqlNotification = "INSERT INTO Notifications (id, heading, details, created_by, created_at) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement statementNotification = connection.prepareStatement(sqlNotification);
            statementNotification.setString(1, notification.getId());
            statementNotification.setString(2, notification.getHeading());
            statementNotification.setString(3, notification.getDetails());
            statementNotification.setObject(4, notification.getCreatedBy().getId());
            Date date = notification.getCreatedAt();
            statementNotification.setDate(5, new java.sql.Date(date.getTime()));
            statementNotification.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Notification> getListNotifiByRoleId(int role_id) {
        List<Notification> listNoti = new ArrayList<>();
        String sql = "select * from [Notifications] n inner join NotificationDetails nd on n.id = nd.notification_id where receiver_id=? order by n.id desc";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, role_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notifi = createNotifi(rs);
                listNoti.add(notifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNoti;
    }

    @Override
    public Notification getNotificationById(String id) {
        String sql = "select * from [Notifications] where id = ?";
        Notification notifi = new Notification();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    notifi = createNotifi(rs);
                    return notifi;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createNotiDetails(NotificationDetails notificationdetails) {
        String sqlNotification = "INSERT INTO NotificationDetails VALUES (?, ?)";
        try (PreparedStatement statementNotification = connection.prepareStatement(sqlNotification);) {
            statementNotification.setString(1, notificationdetails.getNotificationId());
            statementNotification.setString(2, notificationdetails.getReceiver());
            statementNotification.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Notification> getListNotifiByUserId(String userId) {
        String sql = "select * from Notifications n inner join NotificationDetails nd on n.id = nd.notification_id where nd.receiver_id = ? order by nd.notification_id desc";
        List<Notification> listnoti = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listnoti.add(createNotifi(rs));
            }
            return listnoti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listnoti;

    }

    @Override
    public List<Notification> getListSentNotifiById(String id) {
        List<Notification> listnotifi = new ArrayList<>();
        String sql = "select * from Notifications where created_by = ? order by id desc";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listnotifi.add(createNotifi(rs));
            }
            return listnotifi;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
