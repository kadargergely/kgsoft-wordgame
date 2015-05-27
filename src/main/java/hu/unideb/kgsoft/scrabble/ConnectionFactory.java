package hu.unideb.kgsoft.scrabble;

/*
 * #%L
 * kgsoft-scrabble
 * %%
 * Copyright (C) 2015 kgsoft
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;

import static hu.unideb.kgsoft.scrabble.Main.logger;

/**
 * The <code>ConnectionFactory</code> class provides access to the game
 * database, which can contain saved games for registered users.
 * 
 * @author gergo
 *
 */
public class ConnectionFactory {

    private static ConnectionFactory factory = new ConnectionFactory();

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
        Properties prop = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(
                "/oracleUser.properties");

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException();
        }

        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String passwd = prop.getProperty("passwd");

        Connection connection = null;
        OracleConnection oraConn = null;
        try {
            connection = DriverManager.getConnection(url, user, passwd);
            oraConn = connection.unwrap(OracleConnection.class);
            logger.info("Connection to the database created.");
        } catch (SQLException e) {
            logger.error(String.format(
                    "Failed to create connection to the database: ",
                    e.getMessage()));
            e.printStackTrace();
        }
        return oraConn;
    }

    /**
     * Returns a new connection of type <code>OracleConnection</code>. All
     * communication with the game database is performed through connections
     * returned by this method.
     * <p>
     * Access to the Oracle server can be gained with a user name and a
     * password. A separate property file contains these. If the file cannot be
     * found, an exception will be thrown.
     * 
     * @return an <code>OracleConnection</code> object providing the
     *         communication channel with the game server
     * @throws IOException
     *             when the file containing the database user name and password
     *             is missing
     */
    public static OracleConnection getConnection() throws IOException {
        return factory.createConnection();
    }
}
