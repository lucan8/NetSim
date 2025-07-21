import app_init.AppInitializer;
public class Main{
    public static void main(String[] args) {
        try{
            AppInitializer.init();
        } catch (Exception e){
            System.err.println("[ERROR] Initializing the database connection: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Welcome to the application!");
        System.out.println("Please log in or register to continue.");
        // Authenticate the user before showing the main menu
        while (AppInitializer.getAuthService().isAuthenticated == false)
            AppInitializer.getAuthService().menu();
        
        System.out.println("Main menu");
        // Show the main menu until the user exits
        while(true)
            AppInitializer.getAppService().menu();
    }
}