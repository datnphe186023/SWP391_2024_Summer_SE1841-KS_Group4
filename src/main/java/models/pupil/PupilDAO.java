package models.pupil;

import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PupilDAO extends DBContext {

    PersonnelDAO personnelDAO = new PersonnelDAO();

    private Pupil createPupil(ResultSet resultSet) throws SQLException {
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

    public boolean createPupil(Pupil pupil) {
        PupilDAO pupilDAO = new PupilDAO();
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
            preparedStatement.setString(1, generateId(pupilDAO.getLatest().getId()));
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
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<Pupil> getAllPupils() {
        String sql = "select * from Pupils";
        List<Pupil> listPupil = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
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
                pupil = createPupil(resultSet);
                listPupils.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupils;
    }

    public List<Pupil> searchPupilInClass(String search, String classId) {
        String sql = "select * from Pupils p join classDetails c on p.id = c.pupil_id where \n"
                + " (last_name+' '+ first_name like N'%" + search + "%' or pupil_id like '%" + search + "%' )and class_id= '" + classId + "'";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
                listPupils.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupils;
    }

    public List<Pupil> searchPupilByStatus(String search, String status) {
        String sql = "select * from Pupils where (last_name+' '+ first_name like N'%" + search + "%' or id like '%" + search + "%' ) and status = N'" + status + "'";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
                listPupils.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupils;
    }
    public Pupil searchPupilById(String id){
        String sql = "Select * from Pupils where id = ? and status = N'đang theo học' and user_id is null";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(rs);
                return pupil;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Pupil getPupilsById(String id) {
        String sql = "select * from Pupils where id='" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
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
                pupil = createPupil(resultSet);
                listPupil.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

    public List<Pupil> getListPupilOfTeacherBySchoolYear(String schoolYearId, String teacherId) {
        String sql = "SELECT *\n"
                + "FROM  Class INNER JOIN\n"
                + "                  classDetails ON Class.id = classDetails.class_id INNER JOIN\n"
                + "                  Pupils ON classDetails.pupil_id = Pupils.id INNER JOIN\n"
                + "                  SchoolYears ON Class.school_year_id = SchoolYears.id\n"
                + "\t\t\t\t  where teacher_id = ? and school_year_id= ?";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString("pupil_id"));
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

    public List<Pupil> getPupilByClassAndSchoolYear(String classId, String schoolYearId) {
        String sql = "SELECT *\n"
                + "FROM     Class INNER JOIN\n"
                + "                  classDetails ON Class.id = classDetails.class_id INNER JOIN\n"
                + "                  Pupils ON classDetails.pupil_id = Pupils.id INNER JOIN\n"
                + "                  SchoolYears ON Class.school_year_id = SchoolYears.id\n"
                + "\t\t\t\t  where class_id= ? and school_year_id= ?";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            preparedStatement.setString(2, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(resultSet.getString("pupil_id"));
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

    public List<Pupil> getPupilByStatus(String status) {
        String sql = " Select * from Pupils where [status] = N'" + status + "'";
        List<Pupil> listPupils = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
                listPupils.add(pupil);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupils;
    }

    public Pupil getPupilByUserId(String userId) {
        String sql = "SELECT * FROM Pupils WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pupil pupil = new Pupil();
                pupil = createPupil(resultSet);
                return pupil;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Pupil getLatest() {
        String sql = "select TOP 1 * from Pupils order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createPupil(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePupil(String lastName, String firstName, Date birthday, String motherName,
            String motherPhoneNumber, String fatherName, String fatherPhoneNumber,
            String address, String parentSpecialNote) {
        String sql = "update dbo.[Pupils] set last_name=?, first_name=?, birthday=?, mother_name=?, mother_phone_number=?, father_name=?, father_phone_number=?, address=?, parent_special_note=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.setDate(3, new java.sql.Date(birthday.getTime()));
            ps.setString(4, motherName);
            ps.setString(5, motherPhoneNumber);
            ps.setString(6, fatherName);
            ps.setString(7, fatherPhoneNumber);
            ps.setString(8, address);
            ps.setString(9, parentSpecialNote);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "HS" + result;
    }

    public boolean updateParent(Pupil pupil) {
        String sql = "UPDATE [dbo].[Pupils]\n"
                + "   SET \n"
                + "      [mother_name] = ?\n"
                + "      ,[mother_phone_number] = ?\n"
                + "      \n"
                + "      ,[father_name] = ?\n"
                + "      ,[father_phone_number] = ?\n"
                + "      ,[email] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[parent_special_note] = ?\n"
                + "      \n"
                + "      \n"
                + " WHERE [user_id] = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pupil.getMotherName());
            stmt.setString(2, pupil.getMotherPhoneNumber());
            stmt.setString(3, pupil.getFatherName());
            stmt.setString(4, pupil.getFatherPhoneNumber());
            stmt.setString(5, pupil.getEmail());
            stmt.setString(6, pupil.getAddress());
            stmt.setString(7,pupil.getParentSpecialNote());
            stmt.setString(8, pupil.getUserId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pupil> getPupilsWithoutClass(String gradeId, String date) {
        int ageOfPupil = 0;
        List<Pupil> listPupil = new ArrayList<>();
        String sql = "SELECT *\n"
                + "   FROM  Pupils left  JOIN\n"
                + "  classDetails ON Pupils.id = classDetails.pupil_id  left  JOIN\n"
                + "  Class ON Class.id = classDetails.class_id\n"
                + "  where  DATEDIFF(YEAR,birthday,?) = ?  and class_id is null and Pupils.status= N'đang theo học'";
        if (gradeId.equals("G000001")) {
            ageOfPupil = 3;
        } else if (gradeId.equals("G000002")) {
            ageOfPupil = 4;
        } else if (gradeId.equals("G000003")) {
            ageOfPupil = 5;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, ageOfPupil);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPupil.add(createPupil(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

    public void updatePupil(Pupil pupil) {
        String sql = "update dbo.[Pupils] set mother_name=?, mother_phone_number=?, father_name=?, father_phone_number=?, address=?, parent_special_note=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pupil.getMotherName());
            ps.setString(2, pupil.getMotherPhoneNumber());
            ps.setString(3, pupil.getFatherName());
            ps.setString(4, pupil.getFatherPhoneNumber());
            ps.setString(5, pupil.getAddress());
            ps.setString(6, pupil.getParentSpecialNote());
            ps.setString(7, pupil.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Pupil> getPupilNonUserId() {
        List<Pupil> list = new ArrayList<>();
        String sql = "SELECT * FROM Pupils WHERE user_id IS NULL AND status = N'đang theo học'";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PupilDAO dao = new PupilDAO();               
                list.add(dao.createPupil(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Pupil> searchPupilWithoutClassByGrade(String gradeId, String date, String search){
        int ageOfPupil = 0;
        List<Pupil> listPupil = new ArrayList<>();
        String sql="SELECT *\n" +
                "                FROM  Pupils left  JOIN\n" +
                "               classDetails ON Pupils.id = classDetails.pupil_id  left  JOIN\n" +
                "                Class ON Class.id = classDetails.class_id\n" +
                "              where  (DATEDIFF(YEAR,birthday,?) = ? and class_id is null and Pupils.status= N'đang theo học') and (last_name+' '+ first_name like N'%"+search+"%' or Pupils.id like '%"+search+"%')";
        if (gradeId.equals("G000001")) {
            ageOfPupil = 3;
        } else if (gradeId.equals("G000002")) {
            ageOfPupil = 4;
        } else if (gradeId.equals("G000003")) {
            ageOfPupil = 5;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, ageOfPupil);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPupil.add(createPupil(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

}
