/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.role.Role;
import utils.DBContext;

/**
 *
 * @author asus
 */
public class PersonnelDAO extends DBContext implements IPersonnelDAO {

    private Personnel createPersonnel(ResultSet resultSet) throws SQLException {
        Personnel person = new Personnel();
        person.setId(resultSet.getString("id"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setGender(resultSet.getBoolean("gender"));
        person.setBirthday(resultSet.getDate("birthday"));
        person.setEmail(resultSet.getString("email"));
        person.setAddress(resultSet.getString("address"));
        person.setPhoneNumber(resultSet.getString("phone_number"));
        person.setRoleId(resultSet.getInt("role_id"));
        person.setStatus(resultSet.getString("status"));
        person.setAvatar(resultSet.getString("avatar"));
        person.setUserId(resultSet.getString("user_id"));
        return person;
    }

    @Override
    // Get all personnel infomation
    public List<Personnel> getAllPersonnels() {
        String sql = "select * from [Personnels] order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    @Override
    public List<Personnel> getPersonnelByRole(int role) {
        String sql = "select * from [Personnels] where role_id = ? order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    @Override
    public List<Personnel> getPersonnelByNameOrId(String search) {
        String sql = "select * from [Personnels] where (last_name+' '+ first_name like N'%" + search + "%' or id like '%" + search + "%' ) ";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println("nction");
        }
        return persons;
    }

    @Override
    public Personnel getPersonnel(String id) {
        String sql = "select * from [Personnels] where id like ? ";
        Personnel person = new Personnel();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + id + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    @Override
    public int getNumberOfPersonByRole(int id) {
        String sql = "select count(id) as numberofpersonbyrole\n"
                + "from Personnels where role_id = ? ";
        int number = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt("numberofpersonbyrole");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return number;
    }

    @Override
    public void insertPersonnel(String id, String firstname, String lastname, int gender, String birthday, String address, String email, String phone, int role, String avatar) {
        String sql = "insert into Personnels values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, firstname);
            st.setString(3, lastname);
            st.setInt(4, gender);
            st.setString(5, birthday);
            st.setString(6, address);
            st.setString(7, email);
            st.setString(8, phone);
            st.setInt(9, role);
            st.setString(10, "đang chờ xử lý");
            st.setString(11, avatar);
            st.setString(12, null);

            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Personnel getPersonnelByUserId(String userId) {
        String sql = "select * from [User] u join Personnels p on u.id=p.user_id \n"
                + "where u.id = ?";
        Personnel personnel = new Personnel();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personnel.setId(resultSet.getString(8)); /// 8 is position of personnel Id on join of two table
                personnel.setFirstName(resultSet.getString("first_name"));
                personnel.setLastName(resultSet.getString("last_name"));
                personnel.setGender(resultSet.getBoolean("gender"));
                personnel.setBirthday(resultSet.getDate("birthday"));
                personnel.setEmail(resultSet.getString("email"));
                personnel.setAddress(resultSet.getString("address"));
                personnel.setPhoneNumber(resultSet.getString("phone_number"));
                personnel.setRoleId(resultSet.getInt("role_id"));
                personnel.setStatus(resultSet.getString("status"));
                personnel.setAvatar(resultSet.getString("avatar"));
                personnel.setUserId(resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personnel;
    }

    @Override
    public List<Personnel> getPersonnelNonUserId() {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM Personnels \n"
                + "WHERE user_id IS NULL \n"
                + "  AND status = N'đang làm việc';";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personnel p = new Personnel();
                p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setGender(rs.getBoolean(4));
                p.setBirthday(rs.getDate(5));
                p.setAddress(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPhoneNumber(rs.getString(8));
                p.setRoleId(rs.getInt(9));
                p.setStatus(rs.getString(10));
                p.setAvatar(rs.getString(11));
                p.setUserId(rs.getString(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Personnel> getPersonnelByRoleAndNonUserId(int id) {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * FROM Personnels where role_id=? and user_id is null and status = N'đang làm việc'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personnel p = new Personnel();
                p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setGender(rs.getBoolean(4));
                p.setBirthday(rs.getDate(5));
                p.setAddress(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPhoneNumber(rs.getString(8));
                p.setRoleId(rs.getInt(9));
                p.setStatus(rs.getString(10));
                p.setAvatar(rs.getString(11));
                p.setUserId(rs.getString(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Personnel> getPersonnelByStatus(String status) {
        String sql = " Select * from Personnels where [status] = N'" + status + "' order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    @Override
    public boolean updatePersonnelStatus(String pId, String status) {
        String sql = "UPDATE [dbo].[Personnels]\n"
                + "   SET [status] = ? \n"
                + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, pId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public List<Role> getAllPersonnelRole() {
        String sql = "select DISTINCT r.id,r.description from Roles r where id !=5";
        List<Role> roles = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getString("id"));
                role.setDescription(resultSet.getString("description"));
                roles.add(role);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return roles;
    }

    @Override
    public boolean updatePerson(Personnel person) {
        String sql = "UPDATE Personnels SET first_name = ?, last_name = ?, gender = ?, address = ?, email = ?, phone_number = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setBoolean(3, person.getGender());
            stmt.setString(4, person.getAddress());
            stmt.setString(5, person.getEmail());
            stmt.setString(6, person.getPhoneNumber());
            stmt.setString(7, person.getUserId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Personnel> getAvailableTeachers(String schoolYearId) {
        String sql = "SELECT *\n"
                + "FROM Personnels t\n"
                + "         LEFT JOIN class c ON t.id = c.teacher_id AND c.school_year_id = ? \n"
                + "WHERE c.teacher_id IS NULL and t.id like 'TEA%' and t.status like N'đang làm việc%';";
        List<Personnel> teachers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel teacher = createPersonnel(resultSet);
                teachers.add(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    @Override
    public List<String> getAllStatus() {
        String sql = "select distinct status from Personnels";
        List<String> status = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status.add(resultSet.getString("status"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    @Override
    public boolean checkPersonnelPhone(String phoneNumber) {
        String sql = "select phone_number  from  Personnels where phone_number='" + phoneNumber + "'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean checkPersonnelEmail(String email) {
        String sql = "select email from  Personnels where email='" + email + "'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public List<Personnel> getPersonnelByIdNameRoleStatus(String status, String role) {
        String sql = " Select * from Personnels where 1=1";

        if (status != null && !status.isEmpty()) {
            sql += " AND status = N'" + status + "'";
        }
        if (role != null && !role.isEmpty()) {
            int xrole = Integer.parseInt(role);
            sql += " AND role_id = " + xrole + "";
        }
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    @Override
    public boolean checkPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Personnels] WHERE phone_number = ?";
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
    public Personnel getTeacherByClassAndSchoolYear(String classId, String schoolYearId) {
        String sql = "Select teacher_id from class where id= ? and school_year_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            preparedStatement.setString(2, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonnel(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Personnel getTeacherByClass(String classId) {
        String sql = "Select teacher_id from class where id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonnel(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Personnel> getPersonnelAttendance() {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM Personnels \n"
                + "WHERE status = N'đang làm việc' \n"
                + "AND (role_id = 1 OR role_id = 2 OR role_id = 3 OR role_id = 4);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personnel p = new Personnel();
                p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setGender(rs.getBoolean(4));
                p.setBirthday(rs.getDate(5));
                p.setAddress(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPhoneNumber(rs.getString(8));
                p.setRoleId(rs.getInt(9));
                p.setStatus(rs.getString(10));
                p.setAvatar(rs.getString(11));
                p.setUserId(rs.getString(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Personnel> getFreeTeacherByDate(String dayId){
        String sql = "SELECT p.* FROM Personnels p WHERE p.id NOT IN (\n" +
                "    SELECT t.teacher_id\n" +
                "    FROM Timetables t\n" +
                "    WHERE t.date_id = ?\n" +
                ") and p.id like 'TEA%';";
        List<Personnel> teacherList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacherList.add(createPersonnel(resultSet));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return teacherList;
    }

}
