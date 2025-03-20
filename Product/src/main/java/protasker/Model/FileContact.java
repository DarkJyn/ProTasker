package protasker.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static protasker.Model.Authenticator.FILE_PATH;

public class FileContact {
    public static void writeUsersToJson(List<User> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file JSON: " + e.getMessage());
        }
    }
    public static void saveUsersToJson(User currentUser) {
        List<User> usersList = loadUserFromJson();
        for (User user : usersList){
            if (user.getUsername().equals(currentUser.getUsername())){
                user.setUserAvatarPath(currentUser.getUserAvatarPath());
                user.setUsername(currentUser.getUsername());
                user.setPassword(currentUser.getPassword());
                user.setProjects(currentUser.getProjects());
                user.setTasksList(currentUser.getTasksList());
                break;
            }
        }
        writeUsersToJson(usersList);
    }
    public static List<User> loadUserFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file JSON: " + e.getMessage());
            return null;
        }
    }
}
