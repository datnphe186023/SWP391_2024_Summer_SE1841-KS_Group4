package models.user;

import java.util.List;

public interface IUserDAO {
    List<User> getListUser();
    boolean checkPassword(String password, String username);
    User getUserByUsernamePassword(String userName, String password);
    User getUserById(String id);
    void updateUser(User user);
    boolean resetPassword(String key);
    boolean updateNewPassword(String newPassword, String userId);
    void createNewUser(String user_name, String email, int role_id, byte isDisable);
    List<User> getUserByRole(int id);
    User getByUsernameOrEmail(String key);
    boolean updateUserById(User user);
    boolean checkEmailExists(String email);
    List<User> getUserByRoleIdandTeacherId(int role, String teacherId);

}
