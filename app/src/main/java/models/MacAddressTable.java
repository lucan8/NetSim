package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.MacAddressTableData;

public class MacAddressTable extends Model<MacAddressTableData>{
    public MacAddressTable(){
        super("MacAddressTable");
    }

    @Override
    protected HashMap<String, Object> getColumnsForInsert(MacAddressTableData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("sw_interface_id", data.getSwInterfaceId()),
            entry("mac_addr", data.getMacAddress())
        ));
    }

    @Override
    protected MacAddressTableData mapRowToEntity(ResultSet res) throws SQLException{
        return new MacAddressTableData(
            res.getInt("id"),
            res.getInt("sw_interface_id"),
            res.getString("mac_addr")
        );
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
            CREATE TABLE IF NOT EXISTS %s(
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                sw_interface_id INTEGER NOT NULL,
                mac_addr VARCHAR(20) NOT NULL,

                FOREIGN KEY(sw_interface_id) REFERENCES equipmentInterface(id),
                FOREIGN KEY(mac_addr) REFERENCES equipmentInterface(mac_addr)
            )
        """, this.table_name);

        System.out.println(query);
        try(Statement stmt = DBConn.instance().createStatement()){
            stmt.executeUpdate(query);
            return true;
        }
    }

    public ArrayList<MacAddressTableData> selectByEquipment(Integer equipment_id) throws SQLException{
        ArrayList<MacAddressTableData> conv_res = new ArrayList<>();
        String query = """
                        SELECT m.id AS id, sw_interface_id, m.mac_addr AS mac_addr FROM macAddressTable m
                        JOIN equipmentInterface ei ON ei.id = m.sw_interface_id
                        WHERE ei.equipment_id = ?
                       """;

        System.out.println(query);

        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            stmt.setObject(1, equipment_id);

            ResultSet res = stmt.executeQuery();

            while (res.next())
                conv_res.add(mapRowToEntity(res));
            
            return conv_res;
        }
    }

    
}