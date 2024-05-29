package models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public User getUserById(String id) {
        String sql = "select * from [User] where id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setRoleId(rs.getInt(5));
                user.setIsDisabled(rs.getByte(6));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        String sql = "Update dbo.[User] set email=? , role_id=?, isDisabled=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getRoleId());
            ps.setByte(3, user.getIsDisabled());
            ps.setString(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updatePassword(String key, String newPassword) {
        try (Connection con = connection) {
            String sql = "UPDATE [dbo].[User] SET [password] = ? WHERE [email] = ? OR user_name = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, newPassword);
                pst.setString(2, key);
                pst.setString(3, key); // Thiết lập tham số cho user_name
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

    public void createNewUser(String user_name, String email, int role_id, byte isDisable) {
        String sql = "insert into [User] values(?,?,?,?,?,?) ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String userId = generateId(getLatest().getId());
            statement.setString(1, userId);
            statement.setString(2, user_name);
            statement.setString(3, generatePassword());
            statement.setString(4, email);
            statement.setInt(5, role_id);
            statement.setByte(6, isDisable);
            statement.executeUpdate();
            updatePersonnelUserId(user_name, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generatePassword() {
        return "1";
    }

    private User getLatest() {
        String sql = "select TOP 1 * from [User] order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
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
        return "U" + result;
    }

    private void updatePersonnelUserId(String personnelId, String userId) {
        String sql = "update [Personnels] set user_id = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, personnelId);
            statement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
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
        String sql = "SELECT * FROM dbo.[User] WHERE user_name = ? OR email = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
