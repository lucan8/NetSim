package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

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
            entry("interface_id", data.getInterfaceId())
        ));
    }

    @Override
    public RoutingTableData mapRowToEntity(ResultSet res) throws SQLException{
        return new RoutingTableData(
            res.getInt("id"),
            res.getString("ip"),
            res.getInt("mask"),
            res.getInt("interface_id")
        );
    }

    @Override
    public boolean create() throws SQLException{return true;}
}