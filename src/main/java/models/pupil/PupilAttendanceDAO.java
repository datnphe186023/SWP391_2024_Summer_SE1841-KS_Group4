package models.pupil;

import models.day.DayDAO;
import models.day.IDayDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PupilAttendanceDAO extends DBContext implements IPupilAttendanceDAO {
    private PupilAttendance createPupilAttendance(ResultSet resultSet) throws SQLException {
        PupilAttendance pupilAttendance = new PupilAttendance();
        pupilAttendance.setId(resultSet.getString("id"));
        IPupilDAO pupilDAO = new PupilDAO();
        pupilAttendance.setPupil(pupilDAO.getPupilsById(resultSet.getString("pupil_id")));
        IDayDAO dayDAO = new DayDAO();
        pupilAttendance.setDay(dayDAO.getDayByID(resultSet.getString("day_id")));
        pupilAttendance.setStatus(resultSet.getString("status"));
        pupilAttendance.setNote(resultSet.getString("note"));
        return pupilAttendance;
    }

    @Override
    public String addAttendance(PupilAttendance pupilAttendance) {
        String res = "";
        if (getAttendanceByPupilAndDay(pupilAttendance.getPupil().getId(), pupilAttendance.getDay().getId()) == null){
            res = insertAttendance(pupilAttendance);
        } else {
            res = updateAttendance(pupilAttendance);
        }
        return res;
    }

    private String insertAttendance(PupilAttendance pupilAttendance){
        String sql = "insert into PupilsAttendance values (?,?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            String newId = "";
            if (getLatest()==null){
                newId = "PUA000001";
            } else {
                newId = generateId(getLatest().getId());
            }
            statement.setString(1, newId);
            statement.setString(2, pupilAttendance.getDay().getId());
            statement.setString(3, pupilAttendance.getPupil().getId());
            statement.setString(4, pupilAttendance.getStatus());
            statement.setString(5, pupilAttendance.getNote());
            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    private String updateAttendance(PupilAttendance pupilAttendance){
        String sql = "update PupilsAttendance set status = ?, note = ? where pupil_id = ? and day_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupilAttendance.getStatus());
            statement.setString(2, pupilAttendance.getNote());
            statement.setString(3, pupilAttendance.getPupil().getId());
            statement.setString(4, pupilAttendance.getDay().getId());
            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    @Override
    public PupilAttendance getAttendanceByPupilAndDay(String pupilId, String dayId) {
        String sql = "select * from PupilsAttendance where pupil_id = ? and day_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupilId);
            statement.setString(2, dayId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return createPupilAttendance(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkAttendanceByDay(List<Pupil> listPupil, String dayId) {
        StringBuilder sql= new StringBuilder("select id from PupilsAttendance where day_id= ? ");
       if(!listPupil.isEmpty()){
           sql.append(" and pupil_id in (");
       }
        for(int i=0;i<listPupil.size();i++){
            if(i== listPupil.size()-1){
                sql.append("'").append(listPupil.get(i).getId()).append("')");
            }else{
                sql.append("'").append(listPupil.get(i).getId()).append("',");
            }
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1,dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int rows=0;
            while(resultSet.next()){
                rows++;
            }
            if(rows== listPupil.size()){
                return true;
            }else if(rows==0){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<PupilAttendance> getAttendanceOfClassByWeek(String classId, String weekId) {
        List<PupilAttendance> listPupilAttendance = new ArrayList<>();
        String sql = "select * from PupilsAttendance";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);

        }catch (Exception e){
            e.printStackTrace();
        }
        return listPupilAttendance;
    }


    private PupilAttendance getLatest() {
        String sql = "SELECT TOP 1 * FROM PupilsAttendance ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createPupilAttendance(rs);
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
        return "PUA" + result;
    }
}
