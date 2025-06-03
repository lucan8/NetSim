package services;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import app_init.AppInitializer;
import models_data.UserData;
public class AuthService extends Service{
    public boolean isAuthenticated = false;

    public AuthService(){
        super(
            new String[]{
                "Login",
                "Register"
            }
        );
    

        setMenuChoices(
            new Runnable[]{
                this::login,
                this::register
            }
        );
    }

    protected void login(){
        // Prompt user for email and password
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        // Check if user exists and password matches
        try {
            UserData user = AppInitializer.getUserModel().selectByEmail(email);
            if (user != null && checkPassword(password, user.getPassword())) {
                System.out.println("Login successful!");
                isAuthenticated = true;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    protected void register(){
        // Prompt user for registration details
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        // Repromt for email until one that doesn't exist is entered
        String email;
        do{
            System.out.println("Enter email: ");
            email = scanner.nextLine();

            // Check if email already exists in the database, if not break the loop
            try {
                if (AppInitializer.getUserModel().selectByEmail(email) != null) {
                    System.out.println("Email already exists. Please try a different email.");
                    return;
                }
                else {
                    break; // Email is unique, exit the loop
                }
            } catch (SQLException e) {
                System.out.println("Error checking email: " + e.getMessage());
                return; 
            }
        } while (true);

        // Hash the password
        password = hashPassword(password);

        // Create a new UserData object and insert it into the database
        UserData user = new UserData(0, username, password, email);

        try {
            if (AppInitializer.getUserModel().insert(user)) {
                System.out.println("User registered successfully!");
                
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }   
    }

    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private static boolean checkPassword(String plainTextPassword, String storedHash) {
        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}