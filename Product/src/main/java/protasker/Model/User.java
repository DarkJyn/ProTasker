package protasker.Model;

public class User {
    private String userId;
    private String username;
    private String password;
    private String userAvatarPath = null; // Sẽ dùng default avatar nếu null
    
    public User(String username, String password) {
        this.userId = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        // Không set avatar path, để null để dùng default
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo toUserInfo(){
        return new UserInfo(username, userAvatarPath);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
    }
    public String getUserAvatarPath() {
        return userAvatarPath;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
}
