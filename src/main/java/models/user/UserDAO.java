package models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBContext;

/**
 * UserDAO class provides CRUD operations for User entities.
 */
public class UserDAO extends DBContext {

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

}
