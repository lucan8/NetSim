package models;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class Connection extends Model { // Renamed from ConnectionModel
    private final Integer interface_id1;
    private final Integer interface_id2;

    // New default constructor
    public Connection() {
        super("Connection", null);
        this.interface_id1 = null;
        this.interface_id2 = null;
    }

    public Connection(Integer id, Integer interface_id1, Integer interface_id2) {
        super("Connection", id);
        this.interface_id1 = interface_id1;
        this.interface_id2 = interface_id2;
    }

    @Override
    public boolean create() throws SQLException{return true;}
    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
            entry("interface_id1", this.interface_id1),
            entry("interface_id2", this.interface_id2)
        )));
    }
    
    public Integer getInterfaceId1() {
        return interface_id1;
    }

    public Integer getInterfaceId2() {
        return interface_id2;
    }
}