package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    private static boolean set = false;

    // iL faut une base nommee testpersonne !
    private static String dbName="testpersonne";

    public static Connection getConnection() throws SQLException {
        if (connection == null || set) {

            // variables a modifier en fonction de la base
            String userName = "root";
            String password = "";
            String serverName = "localhost";
            //Attention, sous MAMP, le port est 8889
            String portNumber = "3306";
            String tableName = "personne";


            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            connection = DriverManager.getConnection(urlDB, connectionProps);
            set=false;
        }
        return connection;

    }

    public static void setNomDB(String nomDB) throws SQLException {
        // iL faut changer le nom de la db
        dbName = nomDB;
        set = true;
        DBConnection.closeConnection();
    }
    public static void closeConnection() throws SQLException {
        connection.close();
        connection=null;
    }
}
