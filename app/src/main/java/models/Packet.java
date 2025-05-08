package models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
public class Packet extends Model{ // Renamed from PacketModel
    private final Integer interface_id;
    
    private final String src_ip_addr;
    private final String dest_ip_addr;

    private final String src_mac_addr;
    private final String dest_mac_addr;

    private final String data;

    // New default constructor
    public Packet() {
        super("Packet", null);
        this.interface_id = null;
        this.src_ip_addr = null;
        this.dest_ip_addr = null;
        this.src_mac_addr = null;
        this.dest_mac_addr = null;
        this.data = null;
    }

    public Packet(Integer id, Integer interface_id, String src_ip_addr, String dest_ip_addr,
                       String src_mac_addr, String dest_mac_addr, String data){
        super("Packet", id);
        this.interface_id = interface_id;
        this.src_ip_addr = src_ip_addr;
        this.dest_ip_addr = dest_ip_addr;
        this.src_mac_addr = src_mac_addr;
        this.dest_mac_addr = dest_mac_addr;
        this.data = data;
    }

    @Override
    public boolean create() throws SQLException{return true;}

    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
                entry("interface_id", this.interface_id),
                entry("src_ip_addr", this.src_ip_addr),
                entry("dest_ip_addr", this.dest_ip_addr),
                entry("src_mac_addr", this.src_mac_addr),
                entry("dest_mac_addr", this.dest_mac_addr),
                entry("data", this.data)
        )));
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

    public Integer getInterfaceId(){
        return interface_id;
    }
}