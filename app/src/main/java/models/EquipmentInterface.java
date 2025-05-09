package models;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;

public class EquipmentInterface extends Model {
    private String ip;
    private final String mac_addr;
    private Integer mask;
    private final Integer equipment_id;

    public EquipmentInterface() {
        super("EquipmentInterface", null);
        this.ip = null;
        this.mac_addr = null;
        this.mask = null;
        this.equipment_id = null;
    }

    public EquipmentInterface(Integer id, String ip, String mac_addr, Integer mask, Integer equipment_id) {
        super("EquipmentInterface", id);
        this.ip = ip;
        this.mac_addr = mac_addr;
        this.mask = mask;
        this.equipment_id = equipment_id;
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            ip VARCHAR(20) NOT NULL,
            mac_addr VARCHAR(20) NOT NULL UNIQUE,
            mask INTEGER NOT NULL,
            equipment_id INTEGER NOT NULL,

            FOREIGN KEY(equipment_id) REFERENCES equipment(id) 
        );
        """, this.table_name);
        
        System.out.println(query);
        try(PreparedStatement stmt = DBConn.Instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }
    
    @Override
    public boolean insert() throws SQLException {
        return super.insert(new HashMap(Map.ofEntries(
                entry("ip", this.ip),
                entry("mac_addr", this.mac_addr),
                entry("mask", this.mask),
                entry("equipment_id", this.equipment_id)
        )));
    }

    // Selects all interfaces for the given equipment id
    // public List<EquipmentInterface> selectByEquipment(Integer equipment_id) throws SQLException{
    //     ResultSet result = super.select(null, new HashMap(Map.ofEntries(
    //         entry("equipment_id", equipment_id)
    //     )));


    //     List<EquipmentInterface> interfaces = new java.util.ArrayList<>();
    //     while (result.next()){
    //         interfaces.add(new EquipmentInterface(
    //             result.getInt("id"),
    //             result.getString("ip"),
    //             result.getString("mac_addr"),
    //             result.getInt("mask"),
    //             result.getInt("equipment_id")
    //         ));
    //         System.out.println("id: " + result.getInt("id"));
    //     }
    //     return interfaces;
    // }

    @Override
    public void print(){
        System.out.println("id: " + this.id);
        System.out.println("ip: " + this.ip + "/" + this.mask);
        System.out.println("mac_addr: " + this.mac_addr);
    }    
    
    public String getIp() {
        return ip;
    }

    public String getMac_addr() {
        return mac_addr;
    }

    public Integer getMask() {
        return mask;
    }

    public Integer getEquipmentId() {
        return equipment_id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }
}