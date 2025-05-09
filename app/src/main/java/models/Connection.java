package models;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;

public class Connection extends Model {
    private final Integer interface_id1;
    private final Integer interface_id2;

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
    public boolean create() throws SQLException{
        String query = String.format("""
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            interface_id1 INTEGER NOT NULL UNIQUE,
            interface_id2 INTEGER NOT NULL UNIQUE,

            FOREIGN KEY(interface_id1) REFERENCES EquipmentInterface(id),
            FOREIGN KEY(interface_id2) REFERENCES EquipmentInterface(id)
        );
        """, this.table_name);

        System.out.println(query);
        try(PreparedStatement stmt = DBConn.Instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }

    @Override
    public boolean insert() throws SQLException{
        return super.insert(new HashMap(Map.ofEntries(
            entry("interface_id1", this.interface_id1),
            entry("interface_id2", this.interface_id2)
        )));
    }

    @Override
    public void print(){
        return;
    }    
    public Integer getInterfaceId1() {
        return interface_id1;
    }

    public Integer getInterfaceId2() {
        return interface_id2;
    }
}