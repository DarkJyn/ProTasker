package protasker.Model;
import java.util.ArrayList;
import java.util.List;


public class Authenticator {

    public static final String FILE_PATH = "Product/src/main/java/protasker/Model/userdata.json";

    public static User checkLogin(String username, String password) {
        List<User> userList = FileContact.loadUserFromJson();
        if (userList != null) {
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    public static boolean isUsernameTaken(String username) {
        List<User> userList = FileContact.loadUserFromJson();
        if (userList != null) {
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String registerUser(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return "Please fill in the information";
        }
        if (!password.equals(confirmPassword)) {
            return "Wrong confirmation password";
        }
        if (isUsernameTaken(username)) {
            return "Existing Username!";
        }
        List<User> userList = FileContact.loadUserFromJson();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(new User(username, password));
        FileContact.writeUsersToJson(userList);
        return "Successfully registered!";
    }
}
