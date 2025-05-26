package services;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import app_init.AppInitializer;
import models_data.CompanyData;
import models_data.ConnectionData;
import models_data.EquipmentData;
import models_data.EquipmentInterfaceData;
import models_data.MacAddressTableData;
import models_data.PacketData;
import models_data.RoutingTableData;
public class Service{
    private static Scanner scanner = new Scanner(System.in);
    private static final String AUDIT_FILE_NAME = "audit.csv";
    private static FileWriter audit_file_writer;
    // Make this an arraylist instead of a map
    private static final Runnable[] menu_choices = new Runnable[]{
            Service::addCompany,
            Service::addEquipment,
            Service::addEquipmentInterface,
            Service::addConnection,
            Service::addPacket,
            Service::listEquipmentInterfaces,
            Service::updatePacketLayer2Header,
            Service::listPacketsDataForEquipment,
            Service::updateInterfaceIpAndMask,
            Service::removeConnection,
            Service::addMacAddressEntry,
            Service::addRoute,
            Service::listMacAdressTableForEquipment,
            Service::removeRoute,
            () -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
    };

    private static final String[] menu_choices_strings = new String[]{
            "Add company",
            "Add equipment",
            "Add equipment interface",
            "Add connection",
            "Add packet",
            "List equipment interfaces",
            "Update the layer 2 header of a packet",
            "List the data of packets for equipment",
            "Update interface ip and mask",
            "Remove a connection",
            "Add mac address entry to the mac address table",
            "Add route to the routing table",
            "List the mac address table for an equipment",
            "Remove route from the routing table",
            "Exit"
    };

    static {
        try{
            audit_file_writer = new FileWriter(AUDIT_FILE_NAME, true);
        } catch(IOException e){
            throw new RuntimeException("Error opening " + AUDIT_FILE_NAME + "for appending: " + e);
        }

    }

    protected static void audit(String action){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        try {
            audit_file_writer.append(String.format("%s, %s\n", formattedNow, action));
            audit_file_writer.flush();
        } catch (IOException e){ 
            System.out.println("Error writing to audit file: " + e.getMessage());
        }
    }

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

    public static void updatePacketLayer2Header(){
        System.out.println("Enter packet id: ");
        Integer packet_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new source mac address: ");
        String src_mac = scanner.nextLine();

        System.out.println("Enter new destination mac address: ");
        String dest_mac = scanner.nextLine();

        // Update the packet in the database
        boolean res;
        try {
            res = AppInitializer.getPacketModel().updateLayer2Header(packet_id, src_mac, dest_mac);
        } catch (SQLException e) {
            System.out.println("Error updating packet layer 2 header: " + e.getMessage());
            return;
        }

        // Check if the update was successful
        if (!res) {
            System.out.println("Error updating packet layer 2 header: Failed to update in database");
            return;
        }

        System.out.println("Packet layer 2 header updated successfully");
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
    
    public static void updateInterfaceIpAndMask(){
        System.out.println("Enter interface id: ");

        Integer interface_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new ip: ");
        String new_ip = scanner.nextLine();

        System.out.println("Enter new mask: ");
        Integer new_mask = scanner.nextInt();
        scanner.nextLine();

        // Update the interface in the database
        boolean res;
        try {
            res = AppInitializer.getEquipmentInterfaceModel().updateIpAndMask(interface_id, new_ip, new_mask);
        } catch (SQLException e) {
            System.out.println("Error updating interface: " + e.getMessage());
            return; 
        }

        // Check if the update was successful
        if (!res) {
            System.out.println("Error updating interface: Failed to update in database");
            return;
        }

        System.out.println("Interface updated successfully");
    }

    public static void removeConnection(){
        System.out.println("Enter connection id: ");
        Integer connection_id = scanner.nextInt();
        scanner.nextLine();

        // Remove the connection from the database
        boolean res;
        try {
            AppInitializer.getPacketModel().deleteByConnection(connection_id);
            res = AppInitializer.getConnectionModel().deleteById(connection_id);
        } catch (SQLException e) {
            System.out.println("Error removing connection: " + e.getMessage());
            return;
        }
        // Check if the removal was successful
        if (!res) {
            System.out.println("Error removing connection: Failed to delete from database");
            return;
        }
        System.out.println("Connection removed successfully");
    }


    // TODO: Check if an actual switch interface was given
    public static void addMacAddressEntry(){
        System.out.println("Enter switch interface id: ");
        Integer interface_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter mac address: ");
        String mac_address = scanner.nextLine();

        // Insert the mac address entry into the database
        // Removed undefined MacAddressTableData instantiation

        MacAddressTableData data = new MacAddressTableData(0, interface_id, mac_address);
        boolean res;
        try {
            res = AppInitializer.getMacAddressTableModel().insert(data);
        } catch (SQLException e) {
            System.out.println("Error adding mac address entry: " + e.getMessage());
            return;
        }

        // Check if the insertion was successful
        if (!res) {
            System.out.println("Error adding mac address entry: Failed to insert into database");
            return;
        }

        System.out.println("Mac address entry added successfully");
    }

    public static void addRoute(){
        System.out.println("Enter destination network ip: ");
        String dest_network_ip = scanner.nextLine();

        System.out.println("Enter destination network mask: ");
        Integer dest_network_mask = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter outgoing interface id: ");
        Integer outgoing_interface_id = scanner.nextInt();
        scanner.nextLine();

        // Insert the route into the database
        RoutingTableData route = new RoutingTableData(0, dest_network_ip, dest_network_mask, outgoing_interface_id);
        boolean res;
        try {
            res = AppInitializer.getRoutingTableModel().insert(route);
        } catch (SQLException e) {
            System.out.println("Error adding route: " + e.getMessage());
            return;
        }

        if (!res) {
            System.out.println("Error adding route: Failed to insert into database");
            return;
        }

        System.out.println("Route added successfully");
    }

    public static void listMacAdressTableForEquipment(){
        System.out.println("Equipment id: ");
        Integer equipment_id = scanner.nextInt();
        scanner.nextLine();

        // Retrieve the mac address table for the given equipment id
        ArrayList<MacAddressTableData> mac_address_table;
        try {
            mac_address_table = AppInitializer.getMacAddressTableModel().selectByEquipment(equipment_id);
        } catch (SQLException e) {
            System.out.println("Error retrieving mac address table: " + e.getMessage());
            return;
        }

        if (mac_address_table.isEmpty()) {
            System.out.println("No mac address entries found for equipment with id " + equipment_id);
            return;
        }

        System.out.println("Mac address table for equipment " + equipment_id.toString() + ": ");
        for (MacAddressTableData entry : mac_address_table)
            entry.print();
    }

    public static void removeRoute(){
        System.out.println("Enter route id: ");
        Integer route_id = scanner.nextInt();
        scanner.nextLine();

        // Remove the route from the database
        boolean res;
        try {
            res = AppInitializer.getRoutingTableModel().deleteById(route_id);
        } catch (SQLException e) {
            System.out.println("Error removing route: " + e.getMessage());
            return;
        }

        // Check if the removal was successful
        if (!res) {
            System.out.println("Error removing route: Failed to delete from database");
            return;
        }

        System.out.println("Route removed successfully");
    }

    public static void menu(){
        menuPrint();
        int choice;
        do{
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > menu_choices.length)
                System.out.println("Invalid choice, try again");
            else
                break;
        } while (true);

        audit(menu_choices_strings[choice - 1]);
        menu_choices[choice - 1].run();
    }

    private static void menuPrint(){
        for (int i = 0 ; i < menu_choices_strings.length; ++i)
            System.out.println((i + 1) + ". " + menu_choices_strings[i]);
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