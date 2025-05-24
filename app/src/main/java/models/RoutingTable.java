package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import models_data.RoutingTableData;
public class RoutingTable extends Model<RoutingTableData>{

    public RoutingTable(){
        super("RoutingTable");
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