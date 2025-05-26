package services;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Scanner;

import app_init.AppInitializer;
import models_data.CompanyData;
import models_data.ConnectionData;
import models_data.EquipmentData;
import models_data.EquipmentInterfaceData;
import models_data.PacketData;

public class Service{
    private static Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Runnable> menu_choices = Map.ofEntries(
            entry(1, Service::addCompany),
            entry(2, Service::addEquipment),
            entry(3, Service::addEquipmentInterface),
            entry(4, Service::addConnection),
            entry(5, Service::addPacket),
            entry(6, Service::listEquipmentInterfaces),
            // entry(7, Service::listNetwork),
            entry(8, Service::listPacketsDataForEquipment),
            // entry(9, Service::listConnections),
            // entry(10, Service::listEquipments),
            entry(11, () -> {
                System.out.println("Exiting...");
                System.exit(0);
            })
        );

    public static void addCompany(){
        System.out.println("Enter company name: ");
        String company_name = scanner.nextLine();

        CompanyData company = new CompanyData(0, company_name);

        // Insert the equipment into the database
        boolean res;
        try{
            res = AppInitializer.getCompanyModel().insert(company);
        } catch (SQLException e) {
            System.out.println("Error adding company: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding company: Failed to insert into database.");
            return;
        }

        System.out.println("Company added successfully.");
    }

    public static void addEquipment(){
        System.out.println("Enter equipment name: ");
        String equipment_name = scanner.nextLine();

        // Retrieve list of all possible equipment types and join them into a string
        List<EquipmentData.EquipmentType> equip_types_list = List.of(EquipmentData.EquipmentType.values());
        List<String> equip_types_list_s = equip_types_list.stream()
                                                .map(EquipmentData.EquipmentType::toString)
                                                .collect(java.util.stream.Collectors.toList());
        // Map each enum to its string representation and join them with commas
        String equipment_types = equip_types_list_s.stream()
                                    .reduce((a, b) -> a + ", " + b)
                                    .orElse(""); 

        // Prompt the user for an equipment type until a valid one is given                    
        String equipment_type;
        boolean is_valid_eq;
        do{
            System.out.println("Enter equipment type " + equipment_types + ": ");
            equipment_type = scanner.nextLine().toUpperCase();

            is_valid_eq = equip_types_list_s.contains(equipment_type);
            if (!is_valid_eq)
                System.out.println("Invalid equipment type");
        } while (!is_valid_eq);

        System.out.println("Enter equipment max interface count: ");
        int max_interface_count = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Company ID: ");
        int company_id = scanner.nextInt();
        scanner.nextLine(); 

        // Insert the equipment into the database
        EquipmentData equipment = new EquipmentData(0, equipment_name, max_interface_count, company_id, EquipmentData.EquipmentType.fromString(equipment_type));
        boolean res;
        try{
            res = AppInitializer.getEquipmentModel().insert(equipment);
        } catch (SQLException e) {
            System.out.println("Error adding equipment: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding equipment: Failed to insert into database.");
            return;
        }

        System.out.println("Equipment added successfully.");
    }

    public static void addEquipmentInterface(){
        System.out.println("Enter ip: ");
        String ip = scanner.nextLine();

        System.out.println("Enter mac address: ");
        String mac_address = scanner.nextLine();

        System.out.println("Enter mask: ");
        Integer mask = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter equipment ID: ");
        Integer equipment_id = scanner.nextInt();
        scanner.nextLine();

        // Insert interface inside database
        EquipmentInterfaceData equipment_interface = new EquipmentInterfaceData(0, ip, mac_address, mask, equipment_id);
        boolean res;
        try {
            res = AppInitializer.getEquipmentInterfaceModel().insert(equipment_interface);
        } catch (SQLException e) {
            System.out.println("Error adding equipment interface: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding equipment interface: Failed to insert into database");
            return;
        }

        System.out.println("Equipment interface added successfully");
    }

    public static void addConnection(){
        System.out.println("Enter equipment interface ID 1: ");
        Integer equipment_interface_id_1 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter equipment interface ID 2: ");
        Integer equipment_interface_id_2 = scanner.nextInt();
        scanner.nextLine();

        // Insert the connection into the database
        ConnectionData connection = new ConnectionData(0, equipment_interface_id_1, equipment_interface_id_2);
        boolean res;
        try {
            res = AppInitializer.getConnectionModel().insert(connection);
        } catch (SQLException e) {
            System.out.println("Error adding connection: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding connection: Failed to insert into database");
            return;
        }

        System.out.println("Connection added successfully");
    }
    public static void addPacket(){
        System.out.println("Enter packet connection: ");
        Integer connection_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter packet source ip: ");
        String src_ip = scanner.nextLine();
        
        System.out.println("Enter packet destination ip: ");
        String dest_ip = scanner.nextLine();
        
        System.out.println("Enter packet source mac address: ");
        String src_mac = scanner.nextLine();
        
        System.out.println("Enter packet destination mac address: ");
        String dest_mac = scanner.nextLine();

        System.out.println("Enter packet data: ");
        String data = scanner.nextLine();

        // Insert the packet into the database
        PacketData packet = new PacketData(0, connection_id, src_ip, dest_ip, src_mac, dest_mac, data);
        boolean res;
        try {
            res = AppInitializer.getPacketModel().insert(packet);
        } catch (SQLException e) {
            System.out.println("Error adding packet: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding packet: Failed to insert into database");
            return;
        }

        System.out.println("Packet added successfully");
    }

    public static void listEquipmentInterfaces(){
        System.out.println("Equipment id: ");
        Integer equipment_id = scanner.nextInt();
        scanner.nextLine();

        ArrayList<EquipmentInterfaceData> eq_interfaces;
        try {
            eq_interfaces = AppInitializer.getEquipmentInterfaceModel().selectByEquipment(equipment_id);
        } catch (SQLException e) {
            System.out.println("Error retrieving equipment interfaces: " + e.getMessage());
            return;
        }

        if (eq_interfaces.isEmpty()) {
            System.out.println("No interfaces found for equipment with id " + equipment_id);
            return;
        }

        System.out.println("Equipment " + equipment_id.toString() + " interfaces: ");
        
        for (var curr : eq_interfaces)
            curr.print();

    }

    // List all packets that went through an equipment
    public static void listPacketsDataForEquipment(){
        System.out.println("Equipment id: ");
        Integer equipment_id = scanner.nextInt();

        // Retrieve packets for the given equipment id
        ArrayList<PacketData> packets;
        try{
            packets = AppInitializer.getPacketModel().selectPacketsForEquipment(equipment_id);
        } catch(SQLException e){
            System.out.println("Error retrieving packets: " + e.getMessage());
            return;
        }

        if (packets.isEmpty()) {
            System.out.println("No packets found for equipment with id " + equipment_id);
            return;
        }

        System.out.println("Packets for equipment " + equipment_id.toString() + ": ");
        for (PacketData packet : packets)
            System.out.println("Packet " + packet.getId() + " data: " + packet.getData());
    }
    // public static void listConnections();
    // public static void listEquipments();

    private static void menuPrint(){
        System.out.println("1. Add company");
        System.out.println("2. Add equipment");
        System.out.println("3. Add equipment interface");
        System.out.println("4. Add connection");
        System.out.println("5. Add packet");
        System.out.println("6. List equipment interfaces");
        // System.out.println("7. List network");
        System.out.println("8. List the data of packets for equipment");
        // System.out.println("9. List connections");
        // System.out.println("10. List equipments");
        System.out.println("11. Exit");
        System.out.println("Your choice: ");
    }
    public static void menu(){
        menuPrint();
        Runnable chosen_func = null;
        do{
            int choice = scanner.nextInt();
            scanner.nextLine();

            chosen_func = menu_choices.get(choice);
            if (chosen_func == null)
                System.out.println("Invalid choice");
        } while (chosen_func == null);

        chosen_func.run();
    }

    protected static boolean isValidIp(String ip){
        try {
            parseIp(ip);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    protected static boolean isValidMacAddr(String mac_addr){
        List<String> split_mac_addr = Arrays.asList(mac_addr.split(":"));
        if (split_mac_addr.size() != 6)
            return false;
        
        for (String part_mac : split_mac_addr){
            try{
                int parsed_part_ip = Integer.parseInt(part_mac, 16);
                if (parsed_part_ip < 0 || parsed_part_ip > 255)
                    return false;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

    protected static int parseIp(String ip) throws RuntimeException, NumberFormatException {
        Integer result = 0;

        List<String> split_ip = Arrays.asList(ip.split("."));

        if (split_ip.size() != 4)
            throw new RuntimeException("Invalid IP address format");
        
        for (int i = 0; i < split_ip.size(); ++i){
            int parsed_part_ip = Integer.parseInt(split_ip.get(i));
            if (parsed_part_ip < 0 || parsed_part_ip > 255)
                throw new RuntimeException("Invalid IP address format");

            result += parsed_part_ip * (int)Math.pow(256, split_ip.size() - i - 1); 
        }
        return result;
    }

}