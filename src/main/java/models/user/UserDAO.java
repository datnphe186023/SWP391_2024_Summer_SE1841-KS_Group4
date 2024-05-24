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

    public List<User> getListUser() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM dbo.[User]";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setRoleId(rs.getInt(5));
                u.setIsDisabled(rs.getByte(6));
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
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUsername(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setIsDisabled(rs.getByte("isDisabled"));
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
        User u = new User();
        String sql = "Select * from dbo.[User] where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, search);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setRoleId(rs.getInt(5));
                u.setIsDisabled(rs.getByte(6));
            } else {
                u = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public List<User> getUserByRole(int id) {
        List<User> list = new ArrayList<>();
        String sql = "Select * from dbo.[User] where role_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setRoleId(rs.getInt(5));
                u.setIsDisabled(rs.getByte(6));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
