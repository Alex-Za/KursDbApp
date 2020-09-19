package dao;

import entities.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDao {
    private Connection connection;

    public CountryDao(Connection connection) {
        this.connection = connection;
    }
    public List<Country> findAll(){
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from country");
            List<Country> result = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String shortName = rs.getString("short_name");
                result.add(new Country(id, name,shortName));
            }
            return result;
        } catch (SQLException ex) {
            return null;
        }
    }
    public int add(Country country) {
        try(PreparedStatement ps = connection.prepareStatement("insert into country (name, short_name) values (?,?)")) {
            ps.setString(1, country.getName());
            ps.setString(2, country.getShortName());
            ps.executeUpdate();
            return 0;
        } catch (SQLException throwables) {
            return -1;
        }
    }
}
