package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import models_data.MacAddressTableData;

public class MacAddressTable extends Model<MacAddressTableData>{
    public MacAddressTable(){
        super("MacAddressTable");
    }

    @Override
    public boolean create() throws SQLException{return true;}

    @Override
    protected MacAddressTableData mapRowToEntity(ResultSet res) throws SQLException{
        return new MacAddressTableData(
            res.getInt("id"),
            res.getInt("sw_interface_id"),
            res.getString("mac_address")
        );
    }
}