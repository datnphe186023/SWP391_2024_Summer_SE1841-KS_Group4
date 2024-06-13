package models.week;

import models.day.DayDAO;
import models.day.IDayDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeekDAO extends DBContext implements IWeekDAO {

    private Week createWeek(ResultSet rs) throws SQLException {
        Week week = new Week();
        week.setId(rs.getString("id"));
        week.setStartDate(rs.getDate("start_date"));
        week.setEndDate(rs.getDate("end_date"));
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        week.setSchoolYear(schoolYearDAO.getSchoolYear(rs.getString("school_year_id")));
        return week;
    }

    @Override
    public void generateWeeks(SchoolYear schoolYear) {
        try {
            Date startDate = schoolYear.getStartDate();
            Date endDate = schoolYear.getEndDate();
            LocalDate schoolYearStartDate = Helper.convertDateToLocalDate(startDate);
            LocalDate schoolYearEndDate = Helper.convertDateToLocalDate(endDate);;
            LocalDate currentStartDate = schoolYearStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            while (!currentStartDate.isAfter(schoolYearEndDate)) {
                LocalDate currentEndDate = currentStartDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                if (currentEndDate.isAfter(schoolYearEndDate)) {
                    currentEndDate = schoolYearEndDate;
                }
                Week week = new Week();
                String newWeekId = generateId(Objects.requireNonNull(getLatest()).getId());
                week.setId(newWeekId);
                week.setStartDate(Helper.convertLocalDateToDate(currentStartDate));
                week.setEndDate(Helper.convertLocalDateToDate(currentEndDate));
                week.setSchoolYear(schoolYear);
                addWeekToDatabase(week);
                IDayDAO dayDAO = new DayDAO();
                dayDAO.generateDays(getWeek(newWeekId));
                currentStartDate = currentStartDate.plusWeeks(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWeekToDatabase(Week week) {
        String sql = "insert into Weeks values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, week.getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sqlStartDate = dateFormat.format(week.getStartDate());
            statement.setString(2, sqlStartDate);
            String sqlEndDate = dateFormat.format(week.getEndDate());
            statement.setString(3, sqlEndDate);
            statement.setString(4, week.getSchoolYear().getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Week getLatest() {
        String sql = "SELECT TOP 1 * FROM Weeks ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
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
        return "W" + result;
    }

    @Override
    public Week getWeek(String id) {
        String sql = "select * from Weeks where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Week> getWeeksFromNow() {
        List<Week> weeks = new ArrayList<>();
        String sql = "SELECT TOP 3 * FROM Weeks WHERE start_date >= ? ORDER BY start_date";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = Helper.convertLocalNowDateToDate(currentDate);
            statement.setDate(1, new java.sql.Date(currentDateSql.getTime()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Week week = createWeek(rs);
                weeks.add(week);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeks;
    }

    @Override
    public String createTimetableWeek(Week week) {
        String sql = "INSERT INTO Weeks (id, start_date, end_date, school_year_id) VALUES (?, ?, ?, ?)";
        try {
            ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            SchoolYear currentSchoolYear = schoolYearDAO.getSchoolYearByDate(week.getStartDate());
            if (currentSchoolYear == null) {
                return "Tạo mới thất bại! Năm học mới chưa được tạo.";
            }
            week.setSchoolYear(currentSchoolYear);
            if (validateWeekTimetable(week).equals("success")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, week.getId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                statement.setString(2, dateFormat.format(week.getStartDate()));
                statement.setString(3, dateFormat.format(week.getEndDate()));
                statement.setString(4, week.getSchoolYear().getId());
                statement.executeUpdate();
                IDayDAO dayDAO = new DayDAO();
                dayDAO.generateDays(week);
            } else {
                return "Tạo mới thất bại! " + validateWeekTimetable(week);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Tạo mới thất bại! " + sqlException.getMessage();
        } catch (Exception e) {
            return "Tạo mới thất bại! " + e.getMessage();
        }
        return "success";
    }

    private String validateWeekTimetable(Week week) {
        if (week.getEndDate().before(week.getStartDate())) {
            return "Ngày kết thúc không thể trước ngày bắt đầu";
        }

        Week latestWeek = getLatest();
        if (latestWeek != null) {
            Date lastEndDate = latestWeek.getEndDate();
            if (!week.getStartDate().after(lastEndDate)) {
                return "Ngày bắt đầu tuần học mới phải sau ngày kết thúc tuần học cũ";
            }
        }

        // Check if the new week falls within the current school year
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        SchoolYear currentSchoolYear = schoolYearDAO.getSchoolYearByDate(week.getStartDate());
        if (currentSchoolYear == null) {
            return "Tạo mới thất bại! Năm học bạn chọn chưa được tạo.";
        }
        week.setSchoolYear(currentSchoolYear);

        // Ensure the new week starts at least one week after the current date
        LocalDate currentDate = LocalDate.now();
        LocalDate newStartDateLocal = convertToLocalDate(week.getStartDate());
        LocalDate oneWeekAfterCurrentDate = currentDate.plus(1, ChronoUnit.WEEKS);

        if (!newStartDateLocal.isAfter(oneWeekAfterCurrentDate.minusDays(1))) {
            return "Ngày tạo thời khóa biểu phải ít nhất là tuần sau tuần học hiện tại";
        }

        return "success";
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
