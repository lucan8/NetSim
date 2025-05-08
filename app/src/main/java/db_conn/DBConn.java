package db_conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import models.Company;
import models.Equipment;
import models.EquipmentInterface;
public class DBConn{
    private static Connection instance = null;
    private DBConn(){}

    public static void main(String[] args) {
        try{
            if (TestConn())
                System.out.println("[INFO] Connected to database succesfully");
            else
                System.out.println("[ERROR] Connected to database unsuccesfully");
        } catch(SQLException e){
            System.err.println("[ERROR] Testing the database connection: " + e.getMessage());
        }
    }

    // Returns the database connection object(and sets it if needed)
    public static Connection Instance() throws SQLException{
        if (instance == null){
            String url = "jdbc:mysql://localhost:3306/packet_tracer_2";
            String user = "root";
            String password = "REDACTED";
            instance = DriverManager.getConnection(url, user, password);

            System.out.println("[INFO] Connected to database successfully");
            System.out.println("[INFO] Creating tables if they don't exist");

            initDB();
        }
        return instance;
    }

    private static void initDB() throws SQLException{
        Company company = new Company();
        company.create();

        Equipment equipment = new Equipment();
        equipment.create();
    
        EquipmentInterface equipment_interface = new EquipmentInterface();
        equipment_interface.create();
    }   

    public static boolean TestConn() throws SQLException{
        Connection inst = Instance();

        return inst.isValid(2);
    }
}