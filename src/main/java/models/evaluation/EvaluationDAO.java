package models.evaluation;

import models.day.Day;
import models.day.DayDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
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

public class EvaluationDAO extends DBContext implements IEvaluationDAO{
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

    public Evaluation getEvaluationByPupilIdAndDay(String pupilId,String dateId){
        String sql="select * from Evaluations where pupil_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,pupilId);
            preparedStatement.setString(2,dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return createEvaluation(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public boolean createEvaluation(Evaluation evaluation){
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        String sql = "insert into Evaluations values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(getLatest()==null){
                preparedStatement.setString(1, "EVA000001");
            } else {
                preparedStatement.setString(1, generateId(evaluationDAO.getLatest().getId()));
            }
            preparedStatement.setString(2,evaluation.getPupil().getId());
            preparedStatement.setString(3,evaluation.getDate().getId());
            preparedStatement.setString(4,evaluation.getEvaluation());
            preparedStatement.setString(5,evaluation.getNotes());
            int row = preparedStatement.executeUpdate();
            if(row==1){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateEvaluationByPupilAndDay(Evaluation evaluation){
        String sql="update Evaluations set evaluation = ? where  pupil_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,evaluation.getEvaluation());
            preparedStatement.setString(2,evaluation.getPupil().getId());
            preparedStatement.setString(3,evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if(row==1){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateNoteByPupilAndDay(Evaluation evaluation){
        String sql="update Evaluations set notes = ? where  pupil_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,evaluation.getNotes());
            preparedStatement.setString(2,evaluation.getPupil().getId());
            preparedStatement.setString(3,evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if(row==1){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean checkEvaluationExist(String pupilId, String dateId){
        String sql="select * from Evaluations where pupil_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,pupilId);
            preparedStatement.setString(2,dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int getNumberOfStatus(String evaluation, String pupilId, String weekId){
        String sql="select pupil_id, count(evaluation)  from Evaluations e join Days d on e.date_id = d.id\n" +
                "join Weeks w on d.week_id = w.id\n" +
                "where evaluation= ? and pupil_id = ? and week_id= ? \n" +
                "group by pupil_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,evaluation);
            preparedStatement.setString(2,pupilId);
            preparedStatement.setString(3,weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Evaluation> getEvaluationByWeek(String weekId){
        List<Evaluation> list = new ArrayList<>();
        String sql="select e.id, e.pupil_id,e.date_id,e.evaluation,e.notes  from Evaluations e join Days d on e.date_id = d.id\n" +
                "join Weeks w on d.week_id = w.id\n" +
                "where week_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
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

    public static void main(String[] args) {
        EvaluationDAO evaluationDAO = new EvaluationDAO();

    }

    public List<HealthCheckUp> getHealthCheckUpById(String pupil_id) {
        IPupilDAO  pupilDAO = new PupilDAO();
        List<HealthCheckUp> list = new ArrayList<>();
        String sql = "select * from HealthCheckUps where pupil_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,pupil_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<HealthCheckUp> getHealthCheckUpByIdandSchoolYearId(String pupil_id,String schoolyear_id) {
        IPupilDAO  pupilDAO = new PupilDAO();
        List<HealthCheckUp> list = new ArrayList<>();
        String sql = "select hcu.* from HealthCheckUps hcu join dbo.Pupils P on hcu.pupil_id = P.id\n" +
                "join classDetails cd on P.id = cd.pupil_id\n" +
                "                                 JOIN dbo.Class C on C.id = cd.class_id\n" +
                "                                 join dbo.SchoolYears SY on C.school_year_id = SY.id\n" +
                "where hcu.pupil_id = ? and sy.id = ? \n" +
                "and hcu.check_up_date between sy.start_date and sy.end_date";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,pupil_id);
            statement.setString(2,schoolyear_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public HealthCheckUp getHealthCheckUpByIdandDate(String pupil_id, Date date) {
        PupilDAO  pupilDAO = new PupilDAO();
        HealthCheckUp healthCheckUp = new  HealthCheckUp();

        String sql = "select * from HealthCheckUps where pupil_id = ? and check_up_date = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,pupil_id);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()){

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
        }catch (Exception e){
            e.printStackTrace();
        }
        return healthCheckUp;
    }

    public List<String> EvaluationReportYearly(String pupil_id) {
        List<String> reportdata = new ArrayList<>();
        String sql = "select sy.name,count(e.evaluation)as good_day from Evaluations e join dbo.Days D on D.id = e.date_id\n" +
                "\n" +
                "join dbo.Weeks W on D.week_id = W.id\n" +
                "join dbo.SchoolYears SY on W.school_year_id = SY.id\n" +
                "\n" +
                "where e.pupil_id = ?  and e.evaluation = 'Ngoan'\n" +
                "group by evaluation,sy.name";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String data = resultSet.getString("name")+"-"+resultSet.getInt("good_day") ;
                reportdata.add(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return reportdata;
    }

}
