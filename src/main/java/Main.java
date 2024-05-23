import jakarta.servlet.http.HttpSession;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.user.UserDAO;

public class Main {
    public static void main(String[] args) {
        PersonnelDAO dao = new PersonnelDAO();
        String userId = "U000024";
        Personnel personnel = dao.getPersonnelByUserId(userId);

        if (personnel != null) {
            System.out.println("Personnel Information:");
            System.out.println("ID: " + personnel.getId());
            System.out.println("First Name: " + personnel.getFirstName());
            System.out.println("Last Name: " + personnel.getLastName());
            System.out.println("Gender: " + (personnel.getGender() ? "Male" : "Female"));
            System.out.println("Birthday: " + personnel.getBirthday());
            System.out.println("Email: " + personnel.getEmail());
            System.out.println("Address: " + personnel.getAddress());
            System.out.println("Phone Number: " + personnel.getPhoneNumber());
            System.out.println("Role ID: " + personnel.getRoleId());
            System.out.println("Status: " + personnel.getStatus());
            System.out.println("Avatar: " + personnel.getAvatar());
            System.out.println("User ID: " + personnel.getUserId());
        } else {
            System.out.println("Personnel not found.");
        }
    }
}
