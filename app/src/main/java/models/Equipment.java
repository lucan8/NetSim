package models;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
public class Equipment extends Model { // Renamed from EquipmentModel
    public enum EquipmentType {
        ROUTER,
        SWITCH,
        HOST
    }

    protected final String name;
    protected final Integer max_interface_count;
    protected final Integer company_id;
    protected final String type;

    // New default constructor
    public Equipment() {
        super("Equipment", null);
        this.name = null;
        this.max_interface_count = null;
        this.company_id = null;
        this.type = null;
    }

    public Equipment(Integer id, String name, Integer max_interface_count, Integer company_id,
                     EquipmentType type) {
        super("Equipment", id);
        this.name = name;
        this.max_interface_count = max_interface_count;
        this.company_id = company_id;
        this.type = type.toString();
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

    @Override
    public boolean insert() throws SQLException {
        return super.insert(new HashMap(Map.ofEntries(
                entry("name", this.name),
                entry("max_interface_count", this.max_interface_count),
                entry("company_id", this.company_id),
                entry("type", this.type)
        )));
    }   
    // abstract public void forwardPacket(PacketModel packet, EquipmentInterfaceModel equipmentInterface);

    public String getName() {
        return name;
    }

    public Integer getMaxInterfaceCount() {
        return max_interface_count;
    }

    public Integer getCompanyId() {
        return company_id;
    }

    public String getType() {
        return type;
    }
}