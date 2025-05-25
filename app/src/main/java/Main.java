import services.Service;
import db_conn.DBConn;
import app_init.AppInitializer;
import java.sql.SQLException;
public class Main{
    public static void main(String[] args) {
        try{
            DBConn.TestConn();
            AppInitializer.init();
        } catch (SQLException e){
            System.err.println("[ERROR] Testing the database connection: " + e.getMessage());
            System.exit(1);
        }
        while(true)
            Service.menu();
    }
}