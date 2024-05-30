package models.week;

import models.day.DayDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeekDAO extends DBContext {
    private Week createWeek(ResultSet rs) throws SQLException {
        Week week = new Week();
        week.setId(rs.getString("id"));
        week.setStartDate(rs.getDate("start_date"));
        week.setEndDate(rs.getDate("end_date"));
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        week.setSchoolYear(schoolYearDAO.getSchoolYear(rs.getString("school_year_id")));
        return week;
    }

    public void generateWeeks(SchoolYear schoolYear) {
        try{
            Date startDate = schoolYear.getStartDate();
            Date endDate = schoolYear.getEndDate();
            LocalDate schoolYearStartDate = convertToLocalDate(startDate);
            LocalDate schoolYearEndDate = convertToLocalDate(endDate);;
            LocalDate currentStartDate = schoolYearStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            while (!currentStartDate.isAfter(schoolYearEndDate)) {
                LocalDate currentEndDate = currentStartDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                if (currentEndDate.isAfter(schoolYearEndDate)) {
                    currentEndDate = schoolYearEndDate;
                }
                Week week = new Week();
                String newWeekId = generateId(Objects.requireNonNull(getLatest()).getId());
                week.setId(newWeekId);
                week.setStartDate(convertLocalDateToDate(currentStartDate));
                week.setEndDate(convertLocalDateToDate(currentEndDate));
                week.setSchoolYear(schoolYear);
                addWeekToDatabase(week);
                DayDAO dayDAO = new DayDAO();
                dayDAO.generateDays(getWeek(newWeekId));
                currentStartDate = currentStartDate.plusWeeks(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addWeekToDatabase(Week week){
        String sql = "insert into Weeks values (?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, week.getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sqlStartDate = dateFormat.format(week.getStartDate());
            statement.setString(2, sqlStartDate);
            String sqlEndDate = dateFormat.format(week.getEndDate());
            statement.setString(3, sqlEndDate);
            statement.setString(4, week.getSchoolYear().getId());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Week getLatest() {
        String sql = "SELECT TOP 1 * FROM Weeks ORDER BY ID DESC";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
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
        return "W" + result;
    }

    private LocalDate convertToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, month, dayOfMonth);
    }

    public Week getWeek(String id){
        String sql = "select * from Weeks where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
