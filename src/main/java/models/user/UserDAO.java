package models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.personnel.Personnel;
import utils.DBContext;

/**
 * UserDAO class provides CRUD operations for User entities.
 */
public class UserDAO extends DBContext {

    private User createUser(ResultSet rs) throws SQLException {
        User newUser = new User();
        newUser.setId(rs.getString(1));
        newUser.setUsername(rs.getString(2));
        newUser.setPassword(rs.getString(3));
        newUser.setEmail(rs.getString(4));
        newUser.setRoleId(rs.getInt(5));
        newUser.setIsDisabled(rs.getByte(6));
        return newUser;
    }

    public List<User> getListUser() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM dbo.[User]";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = createUser(rs);
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserByUsernamePassword(String userName, String password) {
        String sql = "SELECT * FROM [dbo].[User] WHERE user_name = ? AND password = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
            ps.setString(1, userName);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create and return a User object if a record is found
                    User user = createUser(rs);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no record is found or an exception occurs
    }

    public boolean updatePassword(String email, String newPassword) {
        try (Connection con = connection) {
            String sql = "UPDATE [dbo].[User] SET [password] = ? WHERE [email] = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, newPassword);
                pst.setString(2, email);
                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean updateNewPassword(User user) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET \n"
                + "      \n"
                + "      [password] = ?\n"
                + "      \n"
                + " WHERE [id] = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getId());
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createNewUser(String id, String user_name, String password, String email, int role_id, byte isDisable) {
        String sql = "insert into User values(?,?,?,?,?,?) ";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, user_name);
            pst.setString(3, password);
            pst.setString(4, email);
            pst.setInt(5, role_id);
            pst.setByte(6, isDisable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User searchById(String search) {
        String sql = "Select * from dbo.[User] where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, search);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUserByRole(int id) {
        List<User> list = new ArrayList<>();
        String sql = "Select * from dbo.[User] where role_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = createUser(rs);
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getByUsernameOrEmail(String key) {
        String sql = "Select * from dbo.[User] where user_name = ? or email = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = createUser(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public User getUserById(String id) {
        String sql = "select * from [User] where id=?";
        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setRoleId(rs.getInt(5));
                user.setIsDisabled(rs.getByte(6));
                return user;
            }
        }catch(SQLException e) {
            e.printStackTrace();
    }
        return null;
}
    public void updateUser(User user) {
        String sql = "Update dbo.[User] set email=? , role_id=?, isDisabled=? where id=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getRoleId());
            ps.setByte(3, user.getIsDisabled());
            ps.setString(4, user.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
