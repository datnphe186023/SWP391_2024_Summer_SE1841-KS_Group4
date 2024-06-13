/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.personnel.Personnel;
import utils.DBContext;

/**
 *
 * @author TuyenCute
 */
public class NotificationDAO extends DBContext implements INotificationDAO {

    @Override
    public Notification getLatest() {
        String sql = "select TOP 1 * from [Notifications] order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createNoti(resultSet);
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
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        String result = decimalFormat.format(number);
        return result;
    }

    @Override
    public Notification createNoti(ResultSet resultSet) {
        Notification notifi = new Notification();
        notifi.setId(resultSet.getString(1));
        notifi.setHeading(resultSet.getString(2));
        notifi.setDetails(resultSet.getString(3));
        notifi.setCreatedBy(resultSet.getObject();
        notifi.setCreatedAt();
        return notifi;
    }

}
