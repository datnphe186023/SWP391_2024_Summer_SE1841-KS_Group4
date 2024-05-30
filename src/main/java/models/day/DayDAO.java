package models.day;

import models.schoolYear.SchoolYear;
import models.week.Week;
import models.week.WeekDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayDAO extends DBContext {
    private Day getLatest() {
        String sql = "SELECT TOP 1 * FROM Days ORDER BY ID DESC";
        try{
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId){
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

    public void generateDays(Week week){
        try{
            LocalDate currentDate = convertToLocalDate(week.getStartDate());
            LocalDate endDate = convertToLocalDate(week.getEndDate());
            while(!currentDate.isAfter(endDate)) {
                Day day = new Day();
                day.setId(generateId(Objects.requireNonNull(getLatest()).getId()));
                day.setWeek(week);
                day.setDate(convertLocalDateToDate(currentDate));
                addDayToDatabase(day);
                currentDate = currentDate.plusDays(1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDayToDatabase(Day day) {
        String sql = "INSERT INTO Days VALUES (?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, day.getId());
            statement.setString(2, day.getWeek().getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sqlDate = dateFormat.format(day.getDate());
            statement.setString(3, sqlDate);
            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate convertToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, month, dayOfMonth);
    }
}
