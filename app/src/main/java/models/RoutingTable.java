package models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class RoutingTable extends Model{
    private final Integer router_id;
    private final String ip_address;
    private final Integer mask;
    private final Integer interface_id;

    // New default constructor
    public RoutingTable() {
        super("RoutingTable", null);
        this.router_id = null;
        this.ip_address = null;
        this.mask = null;
        this.interface_id = null;
    }
    
    public RoutingTable(String table_name, Integer id, Integer router_id, String ip_address, Integer mask,
                        Integer interface_id){
        super("RoutingTable", id);
        this.router_id = router_id;
        this.ip_address = ip_address;
        this.mask = mask;
        this.interface_id = interface_id;
    }

    @Override
    public boolean create() throws SQLException{return true;}

    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
                entry("router_id", this.router_id),
                entry("ip_address", this.ip_address),
                entry("mask", this.mask),
                entry("interface_id", this.interface_id)
        )));
    }

    @Override
    public void print(){
        return;
    }    
    
    public Integer getRouterId(){
        return router_id;
    }
    
    public String getIpAddress(){
        return ip_address;
    }

    public Integer getMask(){
        return mask;
    }

    public Integer getInterfaceId(){
        return interface_id;
    }

}