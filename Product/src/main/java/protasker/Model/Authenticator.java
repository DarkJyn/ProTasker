package protasker.Model;
import java.io.*;

public class Authenticator {

    private static final String FILE_PATH = "Product/src/main/java/protasker/Model/userdata.txt";

    public static String checkLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    String savedUsername = credentials[0].trim();
                    String savedPassword = credentials[1].trim();
                    if (savedUsername.equals(username) && savedPassword.equals(password)){
                        return "Login Successful";
                    }
                }
            }
            return "Wrong username or password";
        }
        catch (IOException e) {
            return "Lỗi đọc file: " + e.getMessage();
        }
    }

    public static boolean isUsernameTaken(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].trim().equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(username + "," + password + "\n");
            bw.newLine();
            return "Successfully registered!";
        } catch (IOException e) {
            return "Lỗi ghi file: " + e.getMessage();
        }
    }
}
