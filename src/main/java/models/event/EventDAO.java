package models.event;

import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.role.IRoleDAO;
import models.role.RoleDAO;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDAO extends DBContext implements IEventDAO {
    private Event createEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getString("id"));
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        event.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        event.setHeading(resultSet.getString("heading"));
        event.setDetails(resultSet.getString("details"));
        return event;
    }

    @Override
    public Event getLastest(){
        String sql="select TOP 1 * from Events order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return createEvent(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public String generateId(String latestId){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "E" + result;
    }

    @Override
    public String createEvent(Event event) {
        String sql = "Insert into [Events] values (?,?,?,?)";
        if(checkExistEvent(event.getHeading())) {
            return "Tạo thất bại! Sự kiện đã được tạo !";
        }
        try {
            PreparedStatement preparedStatement =connection.prepareStatement(sql);
            if(getLastest()==null){
                preparedStatement.setString(1, "E000001");
            } else {
                preparedStatement.setString(1,event.getId());
            }
            preparedStatement.setString(2,event.getCreatedBy().getId());
            preparedStatement.setString(3,event.getHeading());
            preparedStatement.setString(4,event.getDetails());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Tạo thất bại!";
        }
        return "success";
    }

    @Override
    public boolean sendEvent(String eventId, int roleId) {
        String sql="insert into eventDetails values (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,eventId);
            preparedStatement.setInt(2,roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Event> getAllEvent() {
        List<Event> list = new ArrayList<>();
        String sql="Select * from Events order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(createEvent(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Event> getEventByRole(int roleId) {
        List<Event> list = new ArrayList<>();
        String sql="select * from Events e join eventDetails ed on e.id = ed.event_id \n" +
                     "where participant = ? order by e.id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,roleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(createEvent(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Event getEventById(String id) {
        String sql = "select * from Events where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return createEvent(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<String> getReceiver(String eventId){
        IRoleDAO roleDAO = new RoleDAO();
        List<String> list = new ArrayList<>();
        String sql="select * from Events e join eventDetails ed on e.id = ed.event_id \n" +
                "where e.id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(roleDAO.getRoleName(resultSet.getInt("participant")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean checkExistEvent(String heading){
        String sql="Select * from Events where heading =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,heading);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return  true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  false;
    }
}
