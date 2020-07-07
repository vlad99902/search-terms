package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for queries with DataBase
 */

public class DataBaseHandler extends Configs {
    /**
     * to get connection
     */
    Connection dbConnection;

    /**
     * Method to get connection
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String ConnectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?&serverTimezone=UTC&useSSL = false&verifyServerCertificate=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(ConnectionString, dbUser,
                dbPass);
        return dbConnection;
    }

    /**
     * Method to return all termins in table
     * @return
     */

    public ObservableList<Termin> getAllTerminsInfoInfo () {
        ObservableList<Termin> termins = FXCollections.observableArrayList();

        try {
            Connection connection = getDbConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT table_term_name," +
                    " table_term_parent, table_region_name, table_term_description FROM termsDB.table_term\n" +
                    "JOIN termsDB.table_region ON table_region.idtable_region = table_term.table_region_idtable_region\n" +
                    "ORDER BY table_term_name");
            while (resultSet.next()){
                String table_term_name = resultSet.getString("table_term_name");
                String table_term_parent = resultSet.getString("table_term_parent");
                String table_region_name = resultSet.getString("table_region_name");
                String table_term_description = resultSet.getString("table_term_description");

                termins.add(new Termin(table_term_name, table_term_parent, table_region_name, table_term_description));
            }
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  termins;
    }

    /**
     * Method to output termin info by name
     * @param name
     * @return
     */

    public ObservableList<Termin> getTerminsInfoByName (String name) {
        ObservableList<Termin> termins = FXCollections.observableArrayList();

        try {
            Connection connection = getDbConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT table_term_parent, table_region_name, table_term_description\n" +
                    "FROM termsDB.table_term\n" +
                    "JOIN termsDB.table_region ON table_region.idtable_region = table_term.table_region_idtable_region\n" +
                    "WHERE table_term_name = '" + name +
                    "' ORDER BY table_term_name");
            while (resultSet.next()){
                String table_term_parent = resultSet.getString("table_term_parent");
                String table_region_name = resultSet.getString("table_region_name");
                String table_term_description = resultSet.getString("table_term_description");

                termins.add(new Termin(table_term_parent, table_region_name, table_term_description));
            }
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  termins;
    }

    /**
     * Method to return only term name
     * @return
     */

    public ObservableList<Termin> getOnlyList (String tableName, String columnName) {
        ObservableList<Termin> termins = FXCollections.observableArrayList();

        try {
            Connection connection = getDbConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT " + columnName +
                    " FROM termsDB." +tableName +
                    " group by " + columnName +
                    " ORDER BY " + columnName);
            while (resultSet.next()){
                String table_term_name = resultSet.getString(columnName);
                termins.add(new Termin(table_term_name));
            }
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  termins;
    }

    /**
     * Return info about termin with filters
     * @param termin
     * @return
     */

    public ObservableList<Termin> getAllTerminsInfoInfo (Termin termin) {
        ObservableList<Termin> termins = FXCollections.observableArrayList();

        try {
            Connection connection = getDbConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT table_term_name, " +
                    "table_term_parent, table_region_name, table_term_description\n" +
                    "FROM termsDB.table_term\n" +
                    "JOIN termsDB.table_region ON table_region.idtable_region = table_term.table_region_idtable_region\n" +
                    "WHERE table_term_name LIKE '%"+ termin.getTermName() +"%' " +
                    "AND table_term_description LIKE '%"+ termin.getTermDescription()+"%' " +
                    "AND table_region_name LIKE '%"+termin.getTermRegion()+"%' " +
                    "AND table_term_parent = '" + termin.getTermParent() + "'\n" +
                    "ORDER BY table_term_name");
            while (resultSet.next()){
                String table_term_name = resultSet.getString("table_term_name");
                String table_term_parent = resultSet.getString("table_term_parent");
                String table_region_name = resultSet.getString("table_region_name");
                String table_term_description = resultSet.getString("table_term_description");

                termins.add(new Termin(table_term_name, table_term_parent, table_region_name, table_term_description));
            }
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  termins;
    }

    /**
     * Get all regions info with count info
     * @return
     */

    public ObservableList<Region> getAllRegions () {
        ObservableList<Region> regions = FXCollections.observableArrayList();

        try {
            Connection connection = getDbConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT table_region_name, " +
                    "COUNT(table_region_idtable_region) AS count\n" +
                    "FROM termsDB.table_region\n" +
                    "left outer join termsDB.table_term ON table_region.idtable_region = table_term.table_region_idtable_region\n" +
                    "group by table_region_name\n" +
                    "order by table_region_name");
            while (resultSet.next()){
                String table_region_name = resultSet.getString("table_region_name");
                Integer count = resultSet.getInt("count");
                regions.add(new Region(table_region_name, count));
            }
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  regions;
    }
}
