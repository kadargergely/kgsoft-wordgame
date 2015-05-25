package hu.unideb.kgsoft.scrabble;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;

import static hu.unideb.kgsoft.scrabble.Main.logger;

public class ConnectionFactory {   
    
    private static ConnectionFactory factory = new ConnectionFactory();
    
    static final String DB_URL = "jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g";
    static final String DB_USER = "H_AENX85";
    static final String DB_PASSWD = "2481632";
    
    private ConnectionFactory() {      
        try {            
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            logger.info("Oracle JDBC driver successfully registered.");
        } catch (SQLException e) {
            logger.error("Couldn't register JDBC driver.");
            e.printStackTrace();         
        }
    }
    
    private OracleConnection createConnection() throws IOException {        
//        Properties prop = new Properties();
//        InputStream inputStream = this.getClass().getResourceAsStream("/oracleUser.properties");
//        
//        if (inputStream != null) {
//            prop.load(inputStream);
//        } else {            
//            throw new FileNotFoundException("property file 'oracleUser.properties' not found in the classpath");
//        }
//        
//        String url = prop.getProperty("url");
//        String user = prop.getProperty("user");
//        String passwd = prop.getProperty("passwd");
        
        Connection connection = null;
        OracleConnection oraConn = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            oraConn = connection.unwrap(OracleConnection.class);
            logger.info("Connection to the database created.");
        } catch (SQLException e) {
            logger.error(String.format("Failed to create connection to the database: ", e.getMessage()));
            e.printStackTrace();            
        }
        return oraConn;
    }
    
    public static OracleConnection getConnection() throws IOException {
        return factory.createConnection();
    }
}
