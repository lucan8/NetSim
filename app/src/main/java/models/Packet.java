package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
import models_data.PacketData;
public class Packet extends Model<PacketData>{
    public Packet(){
        super("Packet");
    }

    @Override
    protected PacketData mapRowToEntity(ResultSet res) throws SQLException{
        return new PacketData(
            res.getInt("id"),
            res.getInt("connection_id"),
            res.getString("src_ip_addr"),
            res.getString("dest_ip_addr"),
            res.getString("src_mac_addr"),
            res.getString("dest_mac_addr"),
            res.getString("data")
        );
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            connection_id INTEGER NOT NULL,
            src_ip_addr VARCHAR(20) NOT NULL,
            dest_ip_addr VARCHAR(20) NOT NULL,
            src_mac_addr VARCHAR(20) NOT NULL,
            dest_mac_addr VARCHAR(20) NOT NULL,
            data TEXT NOT NULL,

            FOREIGN KEY(connection_id) REFERENCES Connection(id),
            FOREIGN KEY(src_ip_addr) REFERENCES EquipmentInterface(ip),
            FOREIGN KEY(dest_ip_addr) REFERENCES EquipmentInterface(ip),
            FOREIGN KEY(src_mac_addr) REFERENCES EquipmentInterface(mac_addr),
            FOREIGN KEY(dest_mac_addr) REFERENCES EquipmentInterface(mac_addr),
        );
        """ ,this.table_name);
        
        System.out.println(query);
        try(PreparedStatement stmt = db_conn.DBConn.Instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }

}