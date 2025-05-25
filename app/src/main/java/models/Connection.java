package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.ConnectionData;

public class Connection extends Model<ConnectionData> {
    public Connection(){
        super("Connection");
    }
    
    @Override
    protected HashMap<String, Object> getColumnsForInsert(ConnectionData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("interface_id1", data.getInterfaceId1()),
            entry("interface_id2", data.getInterfaceId2())
        ));
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
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }

    @Override
    protected ConnectionData mapRowToEntity(ResultSet res) throws SQLException{
        return new ConnectionData(
            res.getInt("id"),
            res.getInt("interface_id1"),
            res.getInt("interface_id2")
        );
    }
}