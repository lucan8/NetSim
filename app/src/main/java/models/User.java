package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.UserData;
public class User extends Model<UserData>{
    public User(){
        super("User");
    }

    @Override
    protected HashMap<String, Object> getColumnsForInsert(UserData data) throws SQLException {
        return new HashMap<>(Map.ofEntries(
            entry("username", data.getUsername()),
            entry("password", data.getPassword()),
            entry("email", data.getEmail())
        ));
    }

    @Override
    public boolean create() throws SQLException {
        String query = String.format(
            """
            CREATE TABLE IF NOT EXISTS %s(
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL
            );
            """, this.table_name);

        System.out.println(query);
        try (var stmt = DBConn.instance().createStatement()) {
            stmt.executeUpdate(query);
            return true;
        }
    }
    @Override
    protected UserData mapRowToEntity(ResultSet res) throws SQLException {
        return new UserData(
            res.getInt("id"),
            res.getString("username"),
            res.getString("password"),
            res.getString("email")
        );
    }

    public UserData selectByEmail(String email) throws SQLException {
        ArrayList<UserData> users = super.select(null, 
            new HashMap<>(Map.ofEntries(
                entry("email", email)
            )));
            
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}