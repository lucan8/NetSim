package db_conn;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConn{
    private static Connection instance = null;
    private DBConn(){}

    public static void main(String[] args) {
        try{
            if (TestConn())
                System.out.println("[INFO] Connected to database succesfully");
            else
                System.out.println("[ERROR] Connected to database unsuccesfully");
        } catch(Exception e){
            System.err.println("[ERROR] Testing the database connection: " + e.getMessage());
        }
    }

    // Initializes database connection
    public static void init() throws SQLException, FileNotFoundException, IOException{
        if (instance == null){
            BufferedReader conf_file = new BufferedReader(new FileReader("app/src/main/java/db_conn/config.txt"));
            String url = conf_file.readLine().split(": ")[1];
            String user = conf_file.readLine().split(": ")[1];
            String password = conf_file.readLine().split(": ")[1];
            instance = DriverManager.getConnection(url, user, password);
        }
    }

    // Returns the database connection object
    public static Connection instance(){
        return instance;
    }

    public static boolean TestConn() throws SQLException, IOException{
        Connection inst = instance();

        return inst.isValid(2);
    }
}