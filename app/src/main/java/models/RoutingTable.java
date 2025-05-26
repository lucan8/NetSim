package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.RoutingTableData;

public class RoutingTable extends Model<RoutingTableData>{

    public RoutingTable(){
        super("RoutingTable");
    }

    @Override
    protected HashMap<String, Object> getColumnsForInsert(RoutingTableData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("ip", data.getIp()),
            entry("mask", data.getMask()),
            entry("r_interface_id", data.getRInterfaceId())
        ));
    }

    @Override
    public RoutingTableData mapRowToEntity(ResultSet res) throws SQLException{
        return new RoutingTableData(
            res.getInt("id"),
            res.getString("ip"),
            res.getInt("mask"),
            res.getInt("r_interface_id")
        );
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
            CREATE TABLE IF NOT EXISTS %s(
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                ip VARCHAR(20) NOT NULL,
                mask INTEGER NOT NULL,
                r_interface_id INTEGER NOT NULL,

                FOREIGN KEY(r_interface_id) REFERENCES equipmentInterface(id)
            )
        """, this.table_name);

        System.out.println(query);

        try(Statement stmt = DBConn.instance().createStatement()){
            stmt.executeUpdate(query);
            return true;
        }
    }
}