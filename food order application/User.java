public class User {
    private String userId;
    private String userType;

    public User(String userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public String getUserId() { return userId; }
    public String getUserType() { return userType; }
}

