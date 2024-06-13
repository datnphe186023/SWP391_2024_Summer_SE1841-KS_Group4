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

public class PupilDAO extends DBContext implements IPupilDAO{

    private Pupil createPupil(ResultSet resultSet) throws SQLException {
        try{
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
            pupil.setfirstGuardianName(resultSet.getString("first_guardian_name"));
            pupil.setfirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
            pupil.setAvatar(resultSet.getString("avatar"));
            pupil.setsecondGuardianName(resultSet.getString("second_guardian_name"));
            pupil.setsecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
            Personnel personnel = personnelDAO.getPersonnel(resultSet.getString("created_by"));
            pupil.setCreatedBy(personnel);
            pupil.setParentSpecialNote(resultSet.getString("parent_special_note"));
            return pupil;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createPupil(Pupil pupil) {
        IPupilDAO pupilDAO = new PupilDAO();
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
                + "           ,[first_guardian_name]\n"
                + "           ,[first_guardian_phone_number]\n"
                + "           ,[avatar]\n"
                + "           ,[second_guardian_name]\n"
                + "           ,[second_guardian_phone_number]\n"
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
            preparedStatement.setString(10, pupil.getfirstGuardianName());
            preparedStatement.setString(11, pupil.getfirstGuardianPhoneNumber());
            preparedStatement.setString(12, pupil.getAvatar());
            preparedStatement.setString(13, pupil.getsecondGuardianName());
            preparedStatement.setString(14, pupil.getsecondGuardianPhoneNumber());
            preparedStatement.setString(15, pupil.getCreatedBy().getId());
            preparedStatement.setString(16, pupil.getParentSpecialNote());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
                pupil.setfirstGuardianName(resultSet.getString("first_guardian_name"));
                pupil.setfirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
                pupil.setAvatar(resultSet.getString("avatar"));
                pupil.setsecondGuardianName(resultSet.getString("second_guardian_name"));
                pupil.setsecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
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

    @Override
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
                pupil.setfirstGuardianName(resultSet.getString("first_guardian_name"));
                pupil.setfirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
                pupil.setAvatar(resultSet.getString("avatar"));
                pupil.setsecondGuardianName(resultSet.getString("second_guardian_name"));
                pupil.setsecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public boolean updateParent(Pupil pupil) {
        String sql = "UPDATE [dbo].[Pupils]\n"
                + "   SET \n"
                + "      [first_guardian_name] = ?\n"
                + "      ,[first_guardian_phone_number] = ?\n"
                + "      \n"
                + "      ,[second_guardian_name] = ?\n"
                + "      ,[second_guardian_phone_number] = ?\n"
                + "      ,[email] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[parent_special_note] = ?\n"
                + "      \n"
                + "      \n"
                + " WHERE [user_id] = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pupil.getfirstGuardianName());
            stmt.setString(2, pupil.getfirstGuardianPhoneNumber());
            stmt.setString(3, pupil.getsecondGuardianName());
            stmt.setString(4, pupil.getsecondGuardianPhoneNumber());
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

    @Override
    public List<Pupil> getPupilsWithoutClass(String schoolYearId) {
        List<Pupil> listPupil = new ArrayList<>();
        String sql="Select  Pupils.id    FROM  Pupils left  JOIN\n" +
                "                 classDetails ON Pupils.id = classDetails.pupil_id  left  JOIN\n" +
                "                Class ON Class.id = classDetails.class_id\n" +
                "               where  Pupils.status= N'đang theo học' and class_id is null \n" +
                "\t\t\t  union  \n" +
                "\t\t\t  Select  distinct pupil_id  from \n" +
                "\t\t\t  classDetails join Class on classDetails.class_id = Class.id\n" +
                "               where   pupil_id not in (Select pupil_id\n" +
                "\t\t\t   from classDetails  join Class on classDetails.class_id = Class.id\n" +
                "\t\t\t   where school_year_id = ? )";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPupil.add(getPupilsById(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPupil;
    }

    @Override
    public void updatePupil(Pupil pupil) {
        String sql = "update dbo.[Pupils] set first_guardian_name=?, first_guardian_phone_number=?, second_guardian_name=?, second_guardian_phone_number=?, address=?, parent_special_note=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pupil.getfirstGuardianName());
            ps.setString(2, pupil.getfirstGuardianPhoneNumber());
            ps.setString(3, pupil.getsecondGuardianName());
            ps.setString(4, pupil.getsecondGuardianPhoneNumber());
            ps.setString(5, pupil.getAddress());
            ps.setString(6, pupil.getParentSpecialNote());
            ps.setString(7, pupil.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Pupil> getPupilNonUserId() {
        List<Pupil> list = new ArrayList<>();
        String sql = "SELECT * FROM Pupils WHERE user_id IS NULL AND status = N'đang theo học'";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pupil pupil = createPupil(rs);
                list.add(pupil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean checkFirstGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Pupils] WHERE first_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkSecondGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Pupils] WHERE second_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}