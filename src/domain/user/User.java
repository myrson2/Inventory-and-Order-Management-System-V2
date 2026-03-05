package domain.user;

public abstract class User {

    private String id;
    private String name;
    private String email;
    private String password;

    // Constructor using "this" directly
    public User(String id, String name, String email, String password) {
        login(email, password, name);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Methods
    public boolean login(String email, String password, String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        return true;
    }

    public abstract void performRoleAction();
}