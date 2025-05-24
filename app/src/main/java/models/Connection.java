package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db_conn.DBConn;
import models_data.ConnectionData;

public class Connection extends Model<ConnectionData> {
    public Connection(){
        super("Connection");
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
    protected ConnectionData mapRowToEntity(ResultSet res) throws SQLException{
        return new ConnectionData(
            res.getInt("id"),
            res.getInt("interface_id1"),
            res.getInt("interface_id2")
        );
    }
}