package application.user;

import java.util.ArrayList;
import java.util.List;
import domain.user.Admin;
import domain.user.User;

public class UserService {
    
    // Simulates our database of registered users
    private List<User> users = new ArrayList<>();
    
    // Tracks the currently logged-in user session
    private User currentUser;
    public User login(String email, String password) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }

        // Search for a matching user in our "database"
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                this.currentUser = u; // Set active session
                System.out.println("Login successful! Welcome back, " + u.getName());
                return u;
            }
        }
        
        System.out.println("Login failed: Incorrect email or password.");
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

    public boolean registerUser(User newUser) {
         if (newUser.getEmail() == null || !newUser.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (newUser.getPassword() == null || newUser.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (emailExists(newUser.getEmail())) {
            System.out.println("Registration failed: A user with this email already exists.");
            return false;
        }
        
        users.add(newUser);
        System.out.println("Registration successful for: " + newUser.getEmail());
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