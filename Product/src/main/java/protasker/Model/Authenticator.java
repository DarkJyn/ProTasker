package protasker.Model;

public class Authenticator {

    public static final String FILE_PATH = "D:/Dean'sCode/PROPTIT/OOP-Java/ProTasker/Product/src/main/java/protasker/Model/userdata.json";

    public static User checkLogin(String username, String password) {
        DataStore dataStore = FileContact.loadDataStore();
        for (User user : dataStore.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isUsernameTaken(String username) {
        DataStore dataStore = FileContact.loadDataStore();
        for (User user : dataStore.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
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
        
        DataStore dataStore = FileContact.loadDataStore();
        User newUser = new User(username, password);
        dataStore.getUsers().add(newUser);
        FileContact.saveDataStore(dataStore);
        
        return "Successfully registered!";
    }
}
