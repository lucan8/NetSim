package models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.CompanyData;

public class Company extends Model<CompanyData> {
    public Company() {
        super("Company");
    }

    @Override
    protected HashMap<String, Object> getColumnsForInsert(CompanyData data) throws SQLException{
        return new HashMap<>(Map.ofEntries(
            entry("name", data.getName())
        ));
    }

    @Override
    protected CompanyData mapRowToEntity(ResultSet res) throws SQLException{
        return new CompanyData(
            res.getInt("id"),
            res.getString("name")
        );
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
        try(Statement stmt = DBConn.instance().createStatement()){
            stmt.executeUpdate(query);
            return true;
        }
    }
}