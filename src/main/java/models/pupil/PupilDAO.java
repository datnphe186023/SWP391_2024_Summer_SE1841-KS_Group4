package models.pupil;

import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PupilDAO extends DBContext {

    PersonnelDAO personnelDAO = new PersonnelDAO();

    public void createPupil(Pupil pupil) {
        String sql = "INSERT INTO [dbo].[Pupils]\n"
                + "           ([id]\n"
                + "           ,[user_id]\n"
                + "           ,[first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[address]\n"
                + "           ,[email]\n"
                + "           ,[status]\n"
                + "           ,[birthday]\n"
                + "           ,[gender]\n"
                + "           ,[mother_name]\n"
                + "           ,[mother_phone_number]\n"
                + "           ,[avatar]\n"
                + "           ,[father_name]\n"
                + "           ,[father_phone_number]\n"
                + "           ,[created_by]\n"
                + "           ,[parent_special_note])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pupil.getId());
            preparedStatement.setString(2, pupil.getUserId());
            preparedStatement.setString(3, pupil.getFirstName());
            preparedStatement.setString(4, pupil.getLastName());
            preparedStatement.setString(5, pupil.getAddress());
            preparedStatement.setString(6, pupil.getEmail());
            Date birhtday = pupil.getBirthday();
            preparedStatement.setString(7, pupil.getStatus());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            String formattedBirthday = dateFormat.format(birhtday);
            preparedStatement.setString(8, formattedBirthday);
            preparedStatement.setBoolean(9, pupil.getGender());
            preparedStatement.setString(10, pupil.getMotherName());
            preparedStatement.setString(11, pupil.getMotherPhoneNumber());
            preparedStatement.setString(12, pupil.getAvatar());
            preparedStatement.setString(13, pupil.getFatherName());
            preparedStatement.setString(14, pupil.getFatherPhoneNumber());
            preparedStatement.setString(15, pupil.getCreatedBy().getId());
            preparedStatement.setString(16, pupil.getParentSpecialNote());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pupil> getAllPupils() {
        String sql = "select * from Pupils";
        List<Pupil> listPupil = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString(1));
                pupil.setUserId(resultSet.getString(2));
                pupil.setFirstName(resultSet.getString(3));
                pupil.setLastName(resultSet.getString(4));
                pupil.setAddress(resultSet.getString(5));
                pupil.setEmail(resultSet.getString(6));
                pupil.setStatus(resultSet.getString(7));
                pupil.setBirthday(resultSet.getDate(8));
                pupil.setGender(resultSet.getBoolean(9));
                pupil.setMotherName(resultSet.getString(10));
                pupil.setMotherPhoneNumber(resultSet.getString(11));
                pupil.setAvatar(resultSet.getString(12));
                pupil.setFatherName(resultSet.getString(13));
                pupil.setFatherPhoneNumber(resultSet.getString(14));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString(15));
                pupil.setCreatedBy(personnel);
                pupil.setParentSpecialNote(resultSet.getString(16));
                listPupil.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

    public List<Pupil> getListPupilsByClass(String classId) {
        String sql = "select * from Pupils p join classDetails c on p.id = c.pupil_id \n"
                + "where class_id= '" + classId + "'";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString("id"));
                pupil.setUserId(resultSet.getString("user_id"));
                pupil.setFirstName(resultSet.getString("first_name"));
                pupil.setLastName(resultSet.getString("last_name"));
                pupil.setAddress(resultSet.getString("address"));
                pupil.setEmail(resultSet.getString("email"));
                pupil.setStatus(resultSet.getString("status"));
                pupil.setBirthday(resultSet.getDate("birthday"));
                pupil.setGender(resultSet.getBoolean("gender"));
                pupil.setMotherName(resultSet.getString("mother_name"));
                pupil.setMotherPhoneNumber(resultSet.getString("mother_phone_number"));
                pupil.setAvatar(resultSet.getString("avatar"));
                pupil.setFatherName(resultSet.getString("father_name"));
                pupil.setFatherPhoneNumber(resultSet.getString("father_phone_number"));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString("created_by"));
                pupil.setCreatedBy(personnel);
                pupil.setParentSpecialNote(resultSet.getString("parent_special_note"));
                listPupils.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupils;
    }

    public Pupil getPupilsById(String id) {
        String sql = "select * from Pupils where id='" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString(1));
                pupil.setUserId(resultSet.getString(2));
                pupil.setFirstName(resultSet.getString(3));
                pupil.setLastName(resultSet.getString(4));
                pupil.setAddress(resultSet.getString(5));
                pupil.setEmail(resultSet.getString(6));
                pupil.setStatus(resultSet.getString(7));
                pupil.setBirthday(resultSet.getDate(8));
                pupil.setGender(resultSet.getBoolean(9));
                pupil.setMotherName(resultSet.getString(10));
                pupil.setMotherPhoneNumber(resultSet.getString(11));
                pupil.setAvatar(resultSet.getString(12));
                pupil.setFatherName(resultSet.getString(13));
                pupil.setFatherPhoneNumber(resultSet.getString(14));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString(15));
                pupil.setCreatedBy(personnel);
                pupil.setParentSpecialNote(resultSet.getString(16));
                return pupil;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean updatePupilStatus(String pupilId, String status) {
        String sql = "UPDATE [dbo].[Pupils]\n"
                + "   SET [status] = ? \n"
                + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, pupilId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean addPupilToClass(String pupilId, String classId) {
        String sql = "INSERT INTO [dbo].[classDetails]\n"
                + "           ([pupil_id]\n"
                + "           ,[class_id])\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pupilId);
            preparedStatement.setString(2, classId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<Pupil> getListOfSearchPupilByNameOrId(String search) {
        String sql = "select * from Pupils where last_name+' '+ first_name like N'%" + search + "%' or id like '%" + search + "%'";
        List<Pupil> listPupil = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString(1));
                pupil.setUserId(resultSet.getString(2));
                pupil.setFirstName(resultSet.getString(3));
                pupil.setLastName(resultSet.getString(4));
                pupil.setAddress(resultSet.getString(5));
                pupil.setEmail(resultSet.getString(6));
                pupil.setStatus(resultSet.getString(7));
                pupil.setBirthday(resultSet.getDate(8));
                pupil.setGender(resultSet.getBoolean(9));
                pupil.setMotherName(resultSet.getString(10));
                pupil.setMotherPhoneNumber(resultSet.getString(11));
                pupil.setAvatar(resultSet.getString(12));
                pupil.setFatherName(resultSet.getString(13));
                pupil.setFatherPhoneNumber(resultSet.getString(14));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString(15));
                pupil.setCreatedBy(personnel);
                pupil.setParentSpecialNote(resultSet.getString(16));
                listPupil.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

    public Pupil getPupilByUserId(String userId) {
        String sql = "SELECT * FROM Pupils WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString("id"));
                pupil.setUserId(resultSet.getString("user_id"));
                pupil.setFirstName(resultSet.getString("first_name"));
                pupil.setLastName(resultSet.getString("last_name"));
                pupil.setAddress(resultSet.getString("address"));
                pupil.setEmail(resultSet.getString("email"));
                pupil.setStatus(resultSet.getString("status"));
                pupil.setBirthday(resultSet.getDate("birthday"));
                pupil.setGender(resultSet.getBoolean("gender"));
                pupil.setMotherName(resultSet.getString("mother_name"));
                pupil.setMotherPhoneNumber(resultSet.getString("mother_phone_number"));
                pupil.setAvatar(resultSet.getString("avatar"));
                pupil.setFatherName(resultSet.getString("father_name"));
                pupil.setFatherPhoneNumber(resultSet.getString("father_phone_number"));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString("created_by"));
                pupil.setCreatedBy(personnel);
                pupil.setParentSpecialNote(resultSet.getString("parent_special_note"));
                return pupil;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        PupilDAO p = new PupilDAO();
        PersonnelDAO per = new PersonnelDAO();
        System.out.println(per.getPersonnel("AS000010").getFirstName());
        System.out.println(p.getListPupilsByClass("C000001").get(0).getFirstName());

    }

}
