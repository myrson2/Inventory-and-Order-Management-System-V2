package application.user;

import java.util.ArrayList;
import java.util.List;

import domain.user.Admin;
import domain.user.User;

public class UserService {
    private User user;
    private List<User> users = new ArrayList<>();

    public boolean login(String email, String password){

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        return true;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void displayAdmin(){
        for (User user : users) {
            if(user instanceof Admin){
                System.out.println(user.getName());
            }
        }
    }

    public User getUser() {
        return user;
    }
}
