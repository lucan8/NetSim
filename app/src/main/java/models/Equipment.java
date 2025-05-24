package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db_conn.DBConn;
import models_data.EquipmentData;

public class Equipment extends Model<EquipmentData> {
    public Equipment(){
        super("Equipment");
    }

    @Override
    protected EquipmentData mapRowToEntity(ResultSet res) throws SQLException{
        return new EquipmentData(
            res.getInt("id"),
            res.getString("name"),
            res.getInt("max_interface_count"),
            res.getInt("company_id"),
            EquipmentData.EquipmentType.fromString(res.getString("type"))
        );
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format("""
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(100) UNIQUE NOT NULL,
            max_interface_count INTEGER NOT NULL,
            company_id INTEGER NOT NULL,
            type VARCHAR(50) NOT NULL,

            FOREIGN KEY(company_id) REFERENCES company(id)
        );
        """, this.table_name);

        System.out.println(query);
        try(PreparedStatement stmt = DBConn.Instance().prepareStatement(query)){
            stmt.executeUpdate(query);
            return true;
        }
    }
     
    // abstract public void forwardPacket(PacketModel packet, EquipmentInterfaceModel equipmentInterface);


}