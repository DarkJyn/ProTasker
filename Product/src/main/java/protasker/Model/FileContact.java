package protasker.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static protasker.Model.Authenticator.FILE_PATH;

public class FileContact {
    
    public static void saveDataStore(DataStore dataStore) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            java.io.File file = new java.io.File(FILE_PATH);
            file.getParentFile().mkdirs();
            System.out.println("Saving to: " + file.getAbsolutePath());
            
            FileWriter writer = new FileWriter(file);
            gson.toJson(dataStore, writer);
            writer.close();
            System.out.println("Saved data successfully!");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DataStore loadDataStore() {
        Gson gson = new Gson();
        java.io.File file = new java.io.File(FILE_PATH);
        System.out.println("Loading from: " + file.getAbsolutePath());
        
        if (!file.exists()) {
            System.out.println("File không tồn tại, tạo DataStore mới");
            return new DataStore();
        }
        
        try (FileReader reader = new FileReader(file)) {
            DataStore dataStore = gson.fromJson(reader, DataStore.class);
            if (dataStore == null) {
                dataStore = new DataStore();
            }
            return dataStore;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file JSON: " + e.getMessage());
            e.printStackTrace();
            return new DataStore();
        }
    }

    public static void updateUser(DataStore dataStore, User updatedUser) {
        for (int i = 0; i < dataStore.getUsers().size(); i++) {
            if (dataStore.getUsers().get(i).getUserId().equals(updatedUser.getUserId())) {
                dataStore.getUsers().set(i, updatedUser);
                break;
            }
        }
        saveDataStore(dataStore);
    }

    public static void updateProject(DataStore dataStore, Project updatedProject) {
        for (int i = 0; i < dataStore.getProjects().size(); i++) {
            if (dataStore.getProjects().get(i).getProjectId().equals(updatedProject.getProjectId())) {
                dataStore.getProjects().set(i, updatedProject);
                break;
            }
        }
        saveDataStore(dataStore);
    }

    public static void updateTask(DataStore dataStore, Task updatedTask) {
        for (int i = 0; i < dataStore.getTasks().size(); i++) {
            if (dataStore.getTasks().get(i).getTaskId().equals(updatedTask.getTaskId())) {
                dataStore.getTasks().set(i, updatedTask);
                break;
            }
        }
        saveDataStore(dataStore);
    }
}
