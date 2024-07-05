package models.evaluation;

import models.day.Day;
import models.day.DayDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.week.IWeekDAO;
import models.week.WeekDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationDAO extends DBContext implements IEvaluationDAO {

    private Evaluation createEvaluation(ResultSet resultSet) throws SQLException {
        PupilDAO pupilDAO = new PupilDAO();
        DayDAO dayDAO = new DayDAO();
        Evaluation evaluation = new Evaluation();
        evaluation.setId(resultSet.getString("id"));
        Pupil pupil = pupilDAO.getPupilsById(resultSet.getString("pupil_id"));
        evaluation.setPupil(pupil);
        Day day = dayDAO.getDayByID(resultSet.getString("date_id"));
        evaluation.setDate(day);
        evaluation.setEvaluation(resultSet.getString("evaluation"));
        evaluation.setNotes(resultSet.getString("notes"));
        return evaluation;
    }

    public Evaluation getEvaluationByPupilIdAndDay(String pupilId, String dateId) {
        String sql = "select * from Evaluations where pupil_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pupilId);
            preparedStatement.setString(2, dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createEvaluation(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean createEvaluation(Evaluation evaluation) {
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        String sql = "insert into Evaluations values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (getLatest() == null) {
                preparedStatement.setString(1, "EVA000001");
            } else {
                preparedStatement.setString(1, generateId(evaluationDAO.getLatest().getId()));
            }
            preparedStatement.setString(2, evaluation.getPupil().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            preparedStatement.setString(4, evaluation.getEvaluation());
            preparedStatement.setString(5, evaluation.getNotes());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateEvaluationByPupilAndDay(Evaluation evaluation) {
        String sql = "update Evaluations set evaluation = ? where  pupil_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evaluation.getEvaluation());
            preparedStatement.setString(2, evaluation.getPupil().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateNoteByPupilAndDay(Evaluation evaluation) {
        String sql = "update Evaluations set notes = ? where  pupil_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evaluation.getNotes());
            preparedStatement.setString(2, evaluation.getPupil().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean checkEvaluationExist(String pupilId, String dateId) {
        String sql = "select * from Evaluations where pupil_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pupilId);
            preparedStatement.setString(2, dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int getNumberOfStatus(String evaluation, String pupilId, String weekId) {
        String sql = "select pupil_id, count(evaluation)  from Evaluations e join Days d on e.date_id = d.id\n"
                + "join Weeks w on d.week_id = w.id\n"
                + "where evaluation= ? and pupil_id = ? and week_id= ? \n"
                + "group by pupil_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evaluation);
            preparedStatement.setString(2, pupilId);
            preparedStatement.setString(3, weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Evaluation> getEvaluationByWeek(String weekId) {
        List<Evaluation> list = new ArrayList<>();
        String sql = "select e.id, e.pupil_id,e.date_id,e.evaluation,e.notes  from Evaluations e join Days d on e.date_id = d.id\n"
                + "join Weeks w on d.week_id = w.id\n"
                + "where week_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(createEvaluation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
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
        return "EVA" + result;
    }

    private Evaluation getLatest() {
        String sql = "SELECT TOP 1 * FROM Evaluations ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createEvaluation(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HealthCheckUp> getHealthCheckUpById(String pupil_id) {
        IPupilDAO pupilDAO = new PupilDAO();
        List<HealthCheckUp> list = new ArrayList<>();
        String sql = "select * from HealthCheckUps where pupil_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HealthCheckUp healthCheckUp = new HealthCheckUp();
                healthCheckUp.setId(resultSet.getString("id"));
                healthCheckUp.setPupil(pupilDAO.getPupilsById(pupil_id));
                healthCheckUp.setCheckUpDate(resultSet.getDate("check_up_date"));
                healthCheckUp.setHeight(resultSet.getFloat("height"));
                healthCheckUp.setWeight(resultSet.getFloat("weight"));
                healthCheckUp.setAverageDevelopmentStage(resultSet.getString("average_development_stage"));
                healthCheckUp.setBloodPressure(resultSet.getString("blood_pressure"));
                healthCheckUp.setTeeth(resultSet.getString("teeth"));
                healthCheckUp.setEyes(resultSet.getString("eyes"));
                healthCheckUp.setNotes(resultSet.getString("notes"));
                list.add(healthCheckUp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HealthCheckUp> getHealthCheckUpByIdandSchoolYearId(String pupil_id, String schoolyear_id) {
        IPupilDAO pupilDAO = new PupilDAO();
        List<HealthCheckUp> list = new ArrayList<>();
        String sql = "select hcu.* from HealthCheckUps hcu join dbo.Pupils P on hcu.pupil_id = P.id\n"
                + "join classDetails cd on P.id = cd.pupil_id\n"
                + "                                 JOIN dbo.Class C on C.id = cd.class_id\n"
                + "                                 join dbo.SchoolYears SY on C.school_year_id = SY.id\n"
                + "where hcu.pupil_id = ? and sy.id = ? \n"
                + "and hcu.check_up_date between sy.start_date and sy.end_date";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            statement.setString(2, schoolyear_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HealthCheckUp healthCheckUp = new HealthCheckUp();
                healthCheckUp.setId(resultSet.getString("id"));
                healthCheckUp.setPupil(pupilDAO.getPupilsById(pupil_id));
                healthCheckUp.setCheckUpDate(resultSet.getDate("check_up_date"));
                healthCheckUp.setHeight(resultSet.getFloat("height"));
                healthCheckUp.setWeight(resultSet.getFloat("weight"));
                healthCheckUp.setAverageDevelopmentStage(resultSet.getString("average_development_stage"));
                healthCheckUp.setBloodPressure(resultSet.getString("blood_pressure"));
                healthCheckUp.setTeeth(resultSet.getString("teeth"));
                healthCheckUp.setEyes(resultSet.getString("eyes"));
                healthCheckUp.setNotes(resultSet.getString("notes"));
                list.add(healthCheckUp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public HealthCheckUp getHealthCheckUpByIdandDate(String pupil_id, Date date) {
        PupilDAO pupilDAO = new PupilDAO();
        HealthCheckUp healthCheckUp = new HealthCheckUp();

        String sql = "select * from HealthCheckUps where pupil_id = ? and check_up_date = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                healthCheckUp.setId(resultSet.getString("id"));
                healthCheckUp.setPupil(pupilDAO.getPupilsById(pupil_id));
                healthCheckUp.setCheckUpDate(resultSet.getDate("check_up_date"));
                healthCheckUp.setHeight(resultSet.getFloat("height"));
                healthCheckUp.setWeight(resultSet.getFloat("weight"));
                healthCheckUp.setAverageDevelopmentStage(resultSet.getString("average_development_stage"));
                healthCheckUp.setBloodPressure(resultSet.getString("blood_pressure"));
                healthCheckUp.setTeeth(resultSet.getString("teeth"));
                healthCheckUp.setEyes(resultSet.getString("eyes"));
                healthCheckUp.setNotes(resultSet.getString("notes"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return healthCheckUp;
    }

    public List<String> EvaluationReportYearly(String pupil_id) {
        List<String> reportdata = new ArrayList<>();

        String sql = "WITH GoodDays AS (\n" +
                "    SELECT\n" +
                "    SY.id,\n" +
                "    COUNT(E.evaluation) AS good_day\n" +
                "    FROM\n" +
                "    Evaluations E\n" +
                "    JOIN dbo.Days D ON D.id = E.date_id\n" +
                "    JOIN dbo.Weeks W ON D.week_id = W.id\n" +
                "    JOIN dbo.SchoolYears SY ON W.school_year_id = SY.id\n" +
                "    WHERE\n" +
                "    E.pupil_id = ? \n" +
                "    AND E.evaluation = 'Ngoan'\n" +
                "    GROUP BY\n" +
                "    SY.id\n" +
                "    ),\n" +
                "    TotalDays AS (\n" +
                "    SELECT\n" +
                "    SY.id,\n" +
                "    COUNT(E.evaluation) AS day\n" +
                "    FROM\n" +
                "    Evaluations E\n" +
                "    JOIN dbo.Days D ON D.id = E.date_id\n" +
                "    JOIN dbo.Weeks W ON D.week_id = W.id\n" +
                "    JOIN dbo.SchoolYears SY ON W.school_year_id = SY.id\n" +
                "    WHERE\n" +
                "    E.pupil_id = ? \n" +
                "    GROUP BY\n" +
                "    SY.id\n" +
                "    )\n" +
                "SELECT\n" +
                "    T1.id,\n" +
                "    T1.good_day,\n" +
                "    T2.day\n" +
                "FROM\n" +
                "    GoodDays T1\n" +
                "        JOIN TotalDays T2 ON T1.id = T2.id;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            statement.setString(2, pupil_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                String data = resultSet.getString("id")+"-"+resultSet.getInt("good_day") +"-"+resultSet.getInt("day");

                reportdata.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportdata;
    }

    public int countEvaluationOfWeek(String week_id,String pupil_id){
        int result = 0;
        String sql = "select count(e.evaluation)as good_day from Evaluations e join Days d on e.date_id = d.id\n" +
                "join Weeks on d.week_id = Weeks.id where Weeks.id =? and e.pupil_id=? and e.evaluation='Ngoan'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, week_id);
            statement.setString(2, pupil_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("good_day");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String PupilReportYearly(String pupil_id,String school_year_id) {
        IWeekDAO weekDAO = new WeekDAO();
        String data ="";
        String sql = "With T as(\n" +
                "    select  school_year_id,week_id,count(evaluation) as good_day from Evaluations\n" +
                "                                               join dbo.Days D on D.id = Evaluations.date_id\n" +
                "                                               join dbo.Weeks W on W.id = D.week_id\n" +
                "    where school_year_id=? and pupil_id=? and Evaluations.evaluation = 'Ngoan'\n" +
                "    group by school_year_id,week_id\n" +
                "),\n" +
                "    N as (\n" +
                "        select school_year_id,count(id) as week from Weeks\n" +
                "        group by school_year_id\n" +
                "    )\n" +
                "select T.school_year_id,N.week,count(T.good_day)as good_ticket from T join N on t.school_year_id = N.school_year_id\n" +
                "                                      where good_day>=3\n" +
                "group by T.school_year_id,N.week";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, school_year_id);
            statement.setString(2, pupil_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                 data = resultSet.getString("school_year_id")+"-"+resultSet.getInt("good_ticket")+"-"+resultSet.getInt("week");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    public List<Evaluation> getEvaluationByWeekandPupilId(String weekId,String pupil_id){
        List<Evaluation> list = new ArrayList<>();
        String sql="select e.id, e.pupil_id,e.date_id,e.evaluation,e.notes  from Evaluations e join Days d on e.date_id = d.id\n" +
                "join Weeks w on d.week_id = w.id\n" +
                "where d.week_id= ? and e.pupil_id = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,weekId);
            preparedStatement.setString(2,pupil_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(createEvaluation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int AccomplishmentAchieveStudents(String schoolyear_id) {
        int number = 0;
        String sql = "WITH GoodDays AS (\n" +
                "    SELECT\n" +
                "    SY.id,e.pupil_id,\n" +
                "    COUNT(E.evaluation) AS good_day\n" +
                "    FROM\n" +
                "    Evaluations E\n" +
                "    JOIN dbo.Days D ON D.id = E.date_id\n" +
                "    JOIN dbo.Weeks W ON D.week_id = W.id\n" +
                "    JOIN dbo.SchoolYears SY ON W.school_year_id = SY.id\n" +
                "    WHERE\n" +
                "    SY.id = ? and\n" +
                "     E.evaluation = 'Ngoan'\n" +
                "    GROUP BY\n" +
                "    SY.id,e.pupil_id\n" +
                "    ),\n" +
                "    TotalDays AS (\n" +
                "    SELECT\n" +
                "    SY.id,e.pupil_id,\n" +
                "    COUNT(E.evaluation) AS day\n" +
                "    FROM\n" +
                "    Evaluations E\n" +
                "    JOIN dbo.Days D ON D.id = E.date_id\n" +
                "    JOIN dbo.Weeks W ON D.week_id = W.id\n" +
                "    JOIN dbo.SchoolYears SY ON W.school_year_id = SY.id\n" +
                "where\n" +
                "    SY.id = ?\n" +
                "    GROUP BY\n" +
                "    SY.id,e.pupil_id\n" +
                "    )\n" +
                "SELECT\n" +
                "    T1.id,\n" +
                "    T1.good_day,\n" +
                "    T1.pupil_id,\n" +
                "    T2.day\n" +
                "FROM\n" +
                "    GoodDays T1\n" +
                "        JOIN TotalDays T2 ON T1.pupil_id = T2.pupil_id\n" +
                "where T1.id =? and good_day >= day/2 ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolyear_id);
            preparedStatement.setString(2, schoolyear_id);
            preparedStatement.setString(3, schoolyear_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number += 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return number;
    }
    @Override
    public int getEvaluationByPupilIdandStatusGood(String pupilid) {
        String sql = "SELECT COUNT(evaluation) AS TotalEvaluations\n"
                + "FROM [BoNo_Kindergarten].[dbo].[Evaluations] where pupil_id=? and evaluation = 'ngoan'";
        int evaluation;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pupilid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    evaluation = rs.getInt(1);
                    return evaluation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getEvaluationByPupilIdandAllStatus(String pupilid) {
        String sql = "SELECT COUNT(evaluation) AS TotalEvaluations\n"
                + "FROM [BoNo_Kindergarten].[dbo].[Evaluations] where pupil_id=?";
        int evaluation;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pupilid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    evaluation = rs.getInt(1);
                    return evaluation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

   public List<String> NumberOfGoodEvaluationsPerYear(String pupil_id) {
        List<String> list = new ArrayList<>();
        String sql = "With T as(\n" +
                "    select  school_year_id,week_id,count(evaluation) as good_day from Evaluations\n" +
                "                                               join dbo.Days D on D.id = Evaluations.date_id\n" +
                "                                               join dbo.Weeks W on W.id = D.week_id\n" +
                "    where pupil_id=? and Evaluations.evaluation = 'Ngoan'\n" +
                "    group by school_year_id,week_id\n" +
                "),\n" +
                "    N as (\n" +
                "        select school_year_id,count(id) as week from Weeks\n" +
                "        group by school_year_id\n" +
                "    )\n" +
                "select T.school_year_id,N.week,count(T.good_day)as good_ticket from T join N on t.school_year_id = N.school_year_id\n" +
                "                                      where good_day>=3\n" +
                "group by T.school_year_id,N.week";
       try (PreparedStatement ps = connection.prepareStatement(sql)) {
           ps.setString(1, pupil_id);
           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                    String data = rs.getString("school_year_id")+"-"+rs.getString("good_ticket")+"-"+rs.getString("week");
                    list.add(data);
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return list;
    }

}
