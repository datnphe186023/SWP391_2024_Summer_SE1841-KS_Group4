package models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 * UserDAO class provides CRUD operations for User entities.
 */
public class UserDAO extends DBContext {

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects.
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[User]";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // Iterate over the result set and populate the list with User objects
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole_id(rs.getInt("role_id"));
                user.setIsDisabled(rs.getInt("isDisabled"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the User object to be inserted.
     * @return the number of rows affected.
     */
    public int insert(User user) {
        String sql = "INSERT INTO [dbo].[User] (id, user_name, password, email, role_id, isDisabled) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setString(1, user.getId());
            ps.setString(2, user.getUser_name());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getRole_id());
            ps.setInt(6, user.getIsDisabled());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user.
     * @return the User object if found, otherwise null.
     */
    public User findById(String id) {
        String sql = "SELECT * FROM [dbo].[User] WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter for the prepared statement
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setIsDisabled(rs.getInt("isDisabled"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a user's information in the database.
     *
     * @param user the User object with updated information.
     * @return true if the update was successful, otherwise false.
     */
    public boolean update(User user) {
        String sql = "UPDATE [dbo].[User] SET user_name = ?, password = ?, email = ?, role_id = ?, isDisabled = ? WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getRole_id());
            ps.setInt(5, user.getIsDisabled());
            ps.setString(6, user.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    public boolean deleteById(String id) {
        String sql = "DELETE FROM [dbo].[User] WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter for the prepared statement
            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                    user.setUser_name(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setIsDisabled(rs.getInt("isDisabled"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no record is found or an exception occurs
    }

}
