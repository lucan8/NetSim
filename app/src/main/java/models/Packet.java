package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
public class Packet extends Model{
    // The connection the packet passed through
    private final Integer connection_id;
    
    private final String src_ip_addr;
    private final String dest_ip_addr;

    private final String src_mac_addr;
    private final String dest_mac_addr;

    // Keeping the data as a string for now
    private final String data;

    public Packet() {
        super("Packet", null);
        this.connection_id = null;
        this.src_ip_addr = null;
        this.dest_ip_addr = null;
        this.src_mac_addr = null;
        this.dest_mac_addr = null;
        this.data = null;
    }

    public Packet(Integer id, Integer connection_id, String src_ip_addr, String dest_ip_addr,
                       String src_mac_addr, String dest_mac_addr, String data){
        super("Packet", id);
        this.connection_id = connection_id;
        this.src_ip_addr = src_ip_addr;
        this.dest_ip_addr = dest_ip_addr;
        this.src_mac_addr = src_mac_addr;
        this.dest_mac_addr = dest_mac_addr;
        this.data = data;
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

            FOREIGN KEY(connection_id) REFERENCES Connection(id)
        );
        """ ,this.table_name);
        
        System.out.println(query);
        try(PreparedStatement stmt = db_conn.DBConn.Instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }

    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
                entry("connection_id", this.connection_id),
                entry("src_ip_addr", this.src_ip_addr),
                entry("dest_ip_addr", this.dest_ip_addr),
                entry("src_mac_addr", this.src_mac_addr),
                entry("dest_mac_addr", this.dest_mac_addr),
                entry("data", this.data)
        )));
    }

    @Override
    public void print(){
        return;
    }    
    
    public String getSrcIpAddr(){
        return src_ip_addr;
    }

    public String getDestIpAddr(){
        return dest_ip_addr;
    }

    public String getSrcMacAddr(){
        return src_mac_addr;
    }

    public String getDestMacAddr(){
        return dest_mac_addr;
    }

    public String getData(){
        return data;
    }

    public Integer getConnectionId(){
        return connection_id;
    }
}