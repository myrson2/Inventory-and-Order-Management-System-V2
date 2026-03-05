package domain.user;

public abstract class User {

    private String id;
    private String name;
    private String email;
    private String password;

    // Constructor using "this" directly
    public User(String id, String name, String email, String password) {
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
    public abstract void performRoleAction();
}