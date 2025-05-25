package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import models_data.MacAddressTableData;

public class MacAddressTable extends Model<MacAddressTableData>{
    public MacAddressTable(){
        super("MacAddressTable");
    }

    @Override
    protected HashMap<String, Object> getColumnsForInsert(MacAddressTableData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("sw_interface_id", data.getSwInterfaceId()),
            entry("mac_address", data.getMacAddress())
        ));
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