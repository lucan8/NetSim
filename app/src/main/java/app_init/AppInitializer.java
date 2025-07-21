package app_init;
import db_conn.DBConn;
import models.Company;
import models.Connection;
import models.Equipment;
import models.EquipmentInterface;
import models.MacAddressTable;
import models.Packet;
import models.RoutingTable;
import models.User;
import services.AppService;
import services.AuthService;

public class AppInitializer{
    // Services
    private static final AppService app_service;
    private static final AuthService auth_service;

    // models
    private static final User user_model;
    private static final Company company_model;
    private static final Equipment equipment_model;
    private static final EquipmentInterface equipment_interface_model;
    private static final Connection conn_model;
    private static final Packet packet_model;
    private static final MacAddressTable mac_addr_table_model;
    private static final RoutingTable routing_table_model;

    static {
        try {
            // Initialize services
            app_service = new AppService();
            auth_service = new AuthService();

            // Initialize DAOs
            user_model = new User();
            company_model = new Company();
            equipment_model = new Equipment();
            equipment_interface_model = new EquipmentInterface();
            conn_model = new Connection();
            packet_model = new Packet();
            mac_addr_table_model = new MacAddressTable();
            routing_table_model = new RoutingTable();

            System.out.println("[INFO]: Connecting to and initializing database");
            // Initialize database conenction
            DBConn.init();
            
            // Create tables if needed
            user_model.create();
            company_model.create();
            equipment_model.create();
            equipment_interface_model.create();
            conn_model.create();
            packet_model.create();
            mac_addr_table_model.create();
            routing_table_model.create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    // Exists only to execute the static block
    public static void init(){}

    public static AppService getAppService(){
        return app_service;
    }

    public static AuthService getAuthService(){
        return auth_service;
    }
    
    public static User getUserModel() {
        return user_model;
    }
    
    public static Company getCompanyModel(){
        return company_model;
    }

    public static Connection getConnectionModel() {
        return conn_model;
    }

    public static Equipment getEquipmentModel() {
        return equipment_model;
    }

    public static EquipmentInterface getEquipmentInterfaceModel() {
        return equipment_interface_model;
    }

    public static MacAddressTable getMacAddressTableModel() {
        return mac_addr_table_model;
    }

    public static Packet getPacketModel() {
        return packet_model;
    }

    public static RoutingTable getRoutingTableModel() {
        return routing_table_model;
    }
}