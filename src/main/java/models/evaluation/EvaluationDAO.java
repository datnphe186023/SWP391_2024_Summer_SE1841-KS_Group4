package models.evaluation;

import models.day.Day;
import models.day.DayDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.week.Week;
import models.week.WeekDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
        String sql="select pupil_id, count(evaluation)  from Evaluations \n" +
                "where evaluation=? and pupil_id = ? and date_id in (";
        DayDAO dayDAO = new DayDAO();
        List<Day> listDayInWeek = dayDAO.getDayByWeek(weekId);
        for (int i=0;i<listDayInWeek.size();i++){
            if(i== listDayInWeek.size()-1){
                sql+="'"+listDayInWeek.get(i).getId()+"'"+") ";
            }else{
                sql+= "'"+listDayInWeek.get(i).getId()+"'"+",";
            }
        }
        sql+=" group by pupil_id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,evaluation);
            preparedStatement.setString(2,pupilId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
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
}
