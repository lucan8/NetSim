package models;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
public class Company extends Model { // Renamed from CompanyModel
    private String name;

    // New default constructor
    public Company() {
        super("Company", null);
        this.name = null;
    }
    
    public Company(Integer id, String name) {
        super("Company", id);
        this.name = name;
    }

    @Override
    public boolean create() throws SQLException{
        String query = String.format(
        """
        CREATE TABLE IF NOT EXISTS %s(
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(100) UNIQUE NOT NULL
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
        return super.insert(new HashMap<>(Map.ofEntries(
            entry("name", this.name)
        )));
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}