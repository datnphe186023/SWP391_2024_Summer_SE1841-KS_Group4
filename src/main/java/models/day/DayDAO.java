package models.day;

import models.timeslot.Timeslot;
import models.week.Week;
import models.week.WeekDAO;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayDAO extends DBContext implements IDayDAO{

    private Day createDay(ResultSet resultSet) throws SQLException {
        Day day = new Day();
        day.setId(resultSet.getString("id"));
        WeekDAO weekDAO = new WeekDAO();
        day.setWeek(weekDAO.getWeek(resultSet.getString("week_id")));
        day.setDate(resultSet.getDate("date"));
        return day;
    }

    @Override
    public Day getDayByDate(String date){
        String sql="select * from days where date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return createDay(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Day> getDaysWithTimetable(String weekId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT DISTINCT d.*\n" +
                "FROM Days d\n" +
                "         JOIN Timetables t ON d.id = t.date_id\n" +
                "WHERE d.week_id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                days.add(createDay(resultSet));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return days;
    }

    private Day getLatest() {
        String sql = "SELECT TOP 1 * FROM Days ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Day day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
                return day;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "D" + result;
    }

    @Override
    public void generateDays(List<Week> weeks) {
        try {
            StringBuilder sql = new StringBuilder("insert into Days values ");
            String newDayId = "";
            if (getLatest()!=null) {
                newDayId = generateId(Objects.requireNonNull(getLatest()).getId());
            }else {
                newDayId = "W000001";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Week week : weeks) {
                LocalDate currentDate = Helper.convertDateToLocalDate(week.getStartDate());
                LocalDate endDate = Helper.convertDateToLocalDate(week.getEndDate());
                while (!currentDate.isAfter(endDate)) {
                    sql.append("('").append(newDayId).append("','").append(week.getId()).append("','")
                            .append(dateFormat.format(Helper.convertLocalDateToDate(currentDate))).append("'),");
                    newDayId = generateId(newDayId);
                    currentDate = currentDate.plusDays(1);
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public List<Day> getDayByWeek(String weekId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT id, week_id, date FROM Days WHERE week_id = ? AND DATEPART(WEEKDAY, date) BETWEEN 2 AND 6";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, weekId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Day day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
                days.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    @Override
    public Day getDayByID(String dateId) {
        String sql = "SELECT * FROM Days WHERE id = ?";
        Day day = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dateId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public List<Day> getFullDayOfWeek(String weekId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT id, week_id, date FROM Days WHERE week_id = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, weekId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Day day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
                days.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

}
