/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.timeslot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class TimeslotDAO extends DBContext {


    public List<Timeslot> getAllTimeslots() {
        String sql = "SELECT * FROM Timeslots";
        List<Timeslot> listTimeslot = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Timeslot timeslot = new Timeslot();
                timeslot.setId(resultSet.getString("id"));
                timeslot.setName(resultSet.getString("name"));
                timeslot.setStartTime(resultSet.getString("start_time"));
                timeslot.setEndTime(resultSet.getString("end_time"));
                timeslot.setSlotNumber(resultSet.getString("slot_number"));
                listTimeslot.add(timeslot);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTimeslot;
    }
}
