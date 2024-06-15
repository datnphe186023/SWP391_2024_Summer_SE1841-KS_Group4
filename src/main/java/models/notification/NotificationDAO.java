/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    public void createNoti(Notification notification) throws SQLException {
        String sql = "insert into [Notifications] values(?,?,?,?,?) ";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            String id;
            if (getLatest() != null) {
                id = generateId(getLatest().getId());
            } else {
                id = "N000001";
            }
            statement.setString(1, id);
            statement.setString(2, notification.getHeading());
            statement.setString(3, notification.getDetails());
            statement.setObject(4, new Personnel().getId());
            Date date = notification.getCreatedAt();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            String Create_at = dateFormat.format(date);
            statement.setString(5, Create_at);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> getListNotifi() {
        List<Notification> listNoti = new ArrayList<>();
        String sql = "select * from [Notifications]";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Notification notifi = createNotifi(rs);
                listNoti.add(notifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNoti;
    }
    public static void main(String[] args) {
        List<Notification> list = new NotificationDAO().getListNotifi();
        System.out.println(list.toString());
    }
}
