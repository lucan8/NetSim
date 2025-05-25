package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.EquipmentInterfaceData;

public class EquipmentInterface extends Model<EquipmentInterfaceData> {
    public EquipmentInterface(){
        super("EquipmentInterface");
    }
    
    @Override
    protected HashMap<String, Object> getColumnsForInsert(EquipmentInterfaceData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("ip", data.getIp()),
            entry("mac_addr", data.getMacAddr()),
            entry("mask", data.getMask()),
            entry("equipment_id", data.getEquipmentId())
        ));
    }

    @Override
    protected EquipmentInterfaceData mapRowToEntity(ResultSet res) throws SQLException{
        return new EquipmentInterfaceData(
            res.getInt("id"),
            res.getString("ip"),
            res.getString("mac_address"),
            res.getInt("mask"),
            res.getInt("equipment_id")
        );
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            ip VARCHAR(20) NOT NULL UNIQUE,
            mac_addr VARCHAR(20) NOT NULL UNIQUE,
            mask INTEGER NOT NULL,
            equipment_id INTEGER NOT NULL,

            FOREIGN KEY(equipment_id) REFERENCES equipment(id) 
        );
        """, this.table_name);
        
        System.out.println(query);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
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

    
}