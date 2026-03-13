package application.user;

import java.util.ArrayList;
import java.util.List;

import domain.user.Admin;
import domain.user.User;
import infrastructure.log.LoggerService;

public class UserService {
    
    // Simulates our database of registered users
    private List<User> users = new ArrayList<>();
    
    // Tracks the currently logged-in user session
    private User currentUser;
    
    public User login(String email, String password, LoggerService loggerService) {
        // Search for a matching user in our "database"
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                this.currentUser = u; // Set active session   
                loggerService.logInfo(u.getEmail(), "[INFO]: USER SUCCESSFULLY LOGIN");
                return u;
            }
        }
        return null;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.getName() + "!");
            this.currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public boolean registerUser(User newUser, LoggerService loggerService) {
        if (emailExists(newUser.getEmail())) {
            System.out.println("Registration failed: A user with this email already exists.");
            loggerService.logWarning(newUser.getEmail(), "[WARNING]: EMAIL IS ALREADY EXIST.");
            return false;
        }
        
        users.add(newUser);
        loggerService.logInfo(newUser.getEmail(), "[INFO]: USER SUCCESSFULLY REGISTERED");
        return true;
    }

    public boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void displayAdmin() {
        System.out.println("=== List of Admins ===");
        for (User u : users) {
            if (u instanceof Admin) {
                System.out.println("- " + u.getName());
            }
        }
    }
}