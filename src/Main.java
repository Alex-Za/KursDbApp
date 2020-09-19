import dao.CountryDao;
import entities.Country;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketOption;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private CountryDao countryDao;
    public static void main(String[] args) throws Exception {
        new Main().run();


    }
    private void run() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileInputStream("watches.properties"));
        try (Connection connection = DriverManager.getConnection(props.getProperty("url"), props)) {
            countryDao = new CountryDao(connection);
            processDBQueries();
        }

    }

    private void processDBQueries() {
        Scanner in = new Scanner(System.in);
        main: while(true) {
            System.out.println("0. exit");
            System.out.println("1. country list");
            System.out.println("2. addcountry");
            int a = in.nextInt();
            switch (a) {
                case 0: break main;
                case 1: listCountries(); break;
                case 2: addCountries(); break;
            }

        }
    }

    private void addCountries() {
        int result = countryDao.add(new Country(2, "Brazil","Bra"));
        if(result!=-1){
            System.out.println("Country successfully added");
        } else {
            System.out.println("Country was not added");
        }

    }

    private void listCountries() {
        System.out.println("-- All countries --");
        List<Country> countries = countryDao.findAll();
        countries.forEach(System.out::println);
    }
//    private void run() {
//        Properties props = new Properties();
//        props.setProperty("user", "Alex-Za");
//        props.setProperty("password", "11201513");
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/te2", props);
//            //insert(connection);
//            select(props, connection);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void insert(Connection connection) throws SQLException {
//        PreparedStatement ps = connection.prepareStatement(
//                "insert into truck (model, vin_num, year, volume) values (?, ?, ?, ?)");
//        ps.setString(1, "Volvo");
//        ps.setString(2, "99999999");
//        ps.setInt(3, 2003);
//        ps.setInt(4, 50);
//        int k = ps.executeUpdate();
//
//    }
//
//    private void select(Properties props, Connection connection) throws SQLException {
//        PreparedStatement ps = connection.prepareStatement("select * from truck where year > ?");
//        ps.setInt(1, 2000);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String model = resultSet.getString("model");
//            String vinNum = resultSet.getString("vin_num");
//            int year = resultSet.getInt("year");
//            int volume = resultSet.getInt("volume");
//            System.out.println(id + " " + model + " " + vinNum + " " + year + " " + volume);
//        }
//    }
}
