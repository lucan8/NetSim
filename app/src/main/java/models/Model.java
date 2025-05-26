package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import db_conn.DBConn;
import models_data.Entity;
public abstract class Model<T extends Entity>{
    protected final String table_name;

    public Model(String table_name){
        this.table_name = table_name;
    }

    public String getTableName(){
        return table_name;
    }
    
    protected abstract T mapRowToEntity(ResultSet res) throws SQLException;
    protected abstract HashMap<String, Object> getColumnsForInsert(T data) throws SQLException;

    public abstract boolean create() throws SQLException;
    
    public ArrayList<T> selectById(Integer id) throws SQLException{
        return this.select(null, 
                           new HashMap(Map.ofEntries(
                                    entry("id", id)
                                    ))
                          );
    }
    public boolean deleteById(Integer id) throws SQLException{
        return delete(new HashMap(Map.ofEntries(
            entry("id", id)
        )));
    }

    public boolean insert(T data) throws SQLException{
        HashMap<String, Object> col_val_map = getColumnsForInsert(data);

        StringBuilder columns = new StringBuilder(),
                      placeholders = new StringBuilder();
        String sep = ", ";
        ArrayList<Object> parameters = new ArrayList();

        // Set strings used in query
        for (Map.Entry<String, Object> entry : col_val_map.entrySet()){
            columns.append(entry.getKey()).append(sep);
            placeholders.append("?").append(sep);
            parameters.add(entry.getValue());
        }

        // Ignore the last ", "
        columns.setLength(columns.length() - sep.length());
        placeholders.setLength(placeholders.length() - sep.length());

        // Create and execute parameterized query
        String query = String.format("INSERT INTO %s (%s) VALUES(%s)", table_name, columns, placeholders);
        System.out.println(query);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            for (int i = 0; i < parameters.size(); ++i)
                stmt.setObject(i + 1, parameters.get(i));

            return stmt.executeUpdate() > 0;
        }
    }

    protected boolean delete(HashMap<String, Object> where_cond) throws SQLException{
        StringBuilder where_str = new StringBuilder();
        String sep = " AND ";
        ArrayList<Object> parameters = new ArrayList();

        // Set strings used in query
        for (Map.Entry<String, Object> entry : where_cond.entrySet()){
            where_str.append(entry.getKey()).append(" = ?").append(sep);
            parameters.add(entry.getValue());
        }

        // Ignore the last sep
        where_str.setLength(where_str.length() - sep.length());

        // Create and execute parameterized query
        String query = String.format("DELETE FROM %s WHERE %s", table_name, where_str);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            for (int i = 0; i < parameters.size(); ++i)
                stmt.setObject(i + 1, parameters.get(i));
            
            return stmt.executeUpdate() > 0;
        }
    }

    protected boolean update(HashMap<String, Object> col_val_map, HashMap<String, Object> where_cond) throws SQLException{
        StringBuilder update_str = new StringBuilder(),
                      where_str = new StringBuilder();
        String update_sep = ", ";
        String where_sep = " AND ";
        ArrayList<Object> parameters = new ArrayList<>();

        // Create update string and add part of params
        for (Map.Entry<String, Object> update_entry : col_val_map.entrySet()){
            update_str.append(update_entry.getKey()).append(" = ?").append(update_sep);
            parameters.add(update_entry.getValue());
        }

        // Create where string and add rest of params
        for (Map.Entry<String, Object> where_entry : where_cond.entrySet()){
            where_str.append(where_entry.getKey()).append(" = ?").append(where_sep);
            parameters.add(where_entry.getValue());
        }

        // Ignore last sep
        update_str.setLength(update_str.length() - update_sep.length());
        where_str.setLength(where_str.length() - where_sep.length());

        // Create and execute parameterized query
        String query = String.format("UPDATE %s SET %s WHERE %s", table_name, update_str, where_str);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            for (int i = 0; i < parameters.size(); ++i)
                stmt.setObject(i + 1, parameters.get(i));

            return stmt.executeUpdate() > 0;
        }
    }

    protected ArrayList<T> select(ArrayList<String> col_list, HashMap<String, Object> where_cond) throws SQLException{
        StringBuilder where_str = new StringBuilder();
        
        // Default selects everything
        String col_str = "*";
        if (col_list != null)
            col_str = String.join(", ", col_list);

        String sep = " AND ";
        ArrayList<Object> parameters = new ArrayList<>();

        for (Map.Entry<String, Object> where_entry : where_cond.entrySet()){
            where_str.append(where_entry.getKey()).append(" = ?").append(sep);
            parameters.add(where_entry.getValue());
        }

        where_str.setLength(where_str.length() - sep.length());
        String query = String.format("SELECT %s FROM %s WHERE %s", col_str, table_name, where_str);

        System.out.println(query);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){
            for (int i = 0; i < parameters.size(); ++i)
                stmt.setObject(i + 1, parameters.get(i));
            
            ResultSet res = stmt.executeQuery();

            // Go through all result sets, convert them to the corresponding entity and add them to result
            ArrayList<T> conv_res = new ArrayList<>();
            while (res.next())
                conv_res.add(mapRowToEntity(res));
            
            return conv_res;
        }
    }
    protected ArrayList<T> select(ArrayList<String> col_list) throws SQLException{
        // Default selects everything
        String col_str = "*";
        if (col_list != null)
            col_str = String.join(", ", col_list);

        String query = String.format("SELECT %s FROM %s", col_str, table_name);

        System.out.println(query);
        try(PreparedStatement stmt = DBConn.instance().prepareStatement(query)){  
            ResultSet res = stmt.executeQuery();

            // Go through all result sets, convert them to the corresponding entity and add them to result
            ArrayList<T> conv_res = new ArrayList<>();
            while (res.next())
                conv_res.add(mapRowToEntity(res));
            
            return conv_res;
        }
    }
}