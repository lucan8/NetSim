package models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
public class MacAddressTable extends Model{
    private final Integer switch_id;
    private final Integer interface_id;
    private final String mac_address;

    
    // New default constructor
    public MacAddressTable() {
        super("MacAddressTable", null);
        this.switch_id = null;
        this.interface_id = null;
        this.mac_address = null;
    }

    public MacAddressTable(Integer id, Integer switch_id, Integer interface_id, String mac_address){
        super("MacAddressTable", id);
        this.switch_id = switch_id;
        this.interface_id = interface_id;
        this.mac_address = mac_address;
    }

    @Override
    public boolean create() throws SQLException{return true;}
    
    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
                entry("switch_id", this.switch_id),
                entry("interface_id", this.interface_id),
                entry("mac_address", this.mac_address)
        )));
    }
    public Integer getSwitchId(){
        return switch_id;
    }

    public Integer getInterfaceId(){
        return interface_id;
    }

    public String getMacAddress(){
        return mac_address;
    }
}