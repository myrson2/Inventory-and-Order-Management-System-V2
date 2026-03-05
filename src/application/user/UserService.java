package application.user;

import domain.user.User;

public class UserService {
    private User user;

    public boolean login(String email, String password){

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        return true;
    }

    public User getUser() {
        return user;
    }
}
