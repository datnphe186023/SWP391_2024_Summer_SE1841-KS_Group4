package models.user;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.*;

/**
 * UserDAO class provides CRUD operations for User entities.
 */
public class UserDAO extends DBContext {

    private User createUser(ResultSet rs) throws SQLException {
        User newUser = new User();
        newUser.setId(rs.getString(1));
        newUser.setUsername(rs.getString(2));
        newUser.setEmail(rs.getString(5));
        newUser.setRoleId(rs.getInt(6));
        newUser.setIsDisabled(rs.getByte(7));
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

    private byte[] getUserSalt(String username){
        byte[] salt = null;
        String sql = "select salt from [User] where user_name = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                salt = rs.getBytes("salt");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return salt;
    }

    public boolean checkPassword(String password, String username) throws Exception {
        byte[] salt = getUserSalt(username);
        byte[] expectedHashPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
        String sql = "select [hashed_password] from [User] where user_name = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                byte[] hashPassword = rs.getBytes("hashed_password");
                String expectedHex = bytesToHex(expectedHashPassword);
                String actualHex = bytesToHex(hashPassword);
                // Compare the hexadecimal representations
                return expectedHex.equals(actualHex);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public User getUserByUsernamePassword(String userName, String password) {
        String sql = "SELECT * FROM [dbo].[User] WHERE user_name = ? AND hashed_password = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
            ps.setString(1, userName);
            byte[] salt = Objects.requireNonNull(getUserSalt(userName));
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            ps.setBytes(2, hashedPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create and return a User object if a record is found
                    User user = createUser(rs);
                    return user;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                user.setEmail(rs.getString(5));
                user.setRoleId(rs.getInt(6));
                user.setIsDisabled(rs.getByte(7));
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

    public boolean resetPassword(String key) {
        try{
            String newPassword = generatePassword();
            Email.sendEmail(Objects.requireNonNull(getUserByUsernameOrEmail(key)).getEmail(),"Yêu cầu quên mật khẩu", "Mật khẩu mới: " + newPassword);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedNewPassword = PasswordUtil.hashPassword(newPassword.toCharArray(), salt);
            String sql = "UPDATE [dbo].[User] SET [salt] =?, [hashed_password] = ? WHERE [email] = ? OR user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBytes(1, salt);
            statement.setBytes(2, hashedNewPassword);
            statement.setString(3, key);
            statement.setString(4, key);
            int rowCount = statement.executeUpdate();
            return rowCount > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private User getUserByUsernameOrEmail(String key) {
        String sql = "select * from [User] where user_name = ? or email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, key);
            statement.setString(2, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = createUser(rs);
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateNewPassword(String newPassword, String userId) {
        String sql = "UPDATE [User]\n" +
                "SET [salt] = ? \n" +
                "  ,[hashed_password] = ? \n" +
                "WHERE [id] = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedNewPassword = PasswordUtil.hashPassword(newPassword.toCharArray(), salt);
            statement.setBytes(1, salt);
            statement.setBytes(2, hashedNewPassword);
            statement.setString(3, userId);
            int rowCount = statement.executeUpdate();
            return rowCount > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void createNewUser(String user_name, String email, int role_id, byte isDisable) {
        String sql = "insert into [User] values(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String userId;
            if (getLatest()!=null){
                userId = generateId(getLatest().getId());
            } else {
                userId = "U000001";
            }
            statement.setString(1, userId);
            statement.setString(2, user_name.toLowerCase());
            String password = generatePassword();
            byte[] salt = PasswordUtil.generateSalt();
            statement.setBytes(3, salt);
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            statement.setBytes(4, hashedPassword);
            statement.setString(5, email);
            statement.setInt(6, role_id);
            statement.setByte(7, isDisable);
            statement.executeUpdate();
            Email.sendEmail(email, "Tạo tài khoản Mầm Non Bono thành công", "Tên tài khoản: " + user_name.toLowerCase() + "||Mật khẩu: " + password);
            updatePersonnelUserId(user_name, userId);
            updatePupilsUserId(user_name, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePassword() {
        SecureRandom random = new SecureRandom(); // Sử dụng SecureRandom để tạo số ngẫu nhiên
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            password.append(digit); // Thêm số ngẫu nhiên vào mật khẩu
        }

        return password.toString(); // Trả về mật khẩu dưới dạng chuỗi
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
    private void updatePupilsUserId(String PupilsId, String userId) {
        String sql = "update Pupils set user_id = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, PupilsId);
            statement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
  
    public boolean updateUserById(User user) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [email] = ?\n"
                + " WHERE [id] = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
