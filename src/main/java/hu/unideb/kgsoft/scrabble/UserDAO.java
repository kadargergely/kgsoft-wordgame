package hu.unideb.kgsoft.scrabble;

import hu.unideb.kgsoft.scrabble.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public boolean authenticateUser(String uname, String passwd)
            throws IOException {
        String processedPasswd = Utils.processPassword(passwd);
        if (processedPasswd == null) {
            // TODO log nem sikerult kodolni a jelszot
            return false;
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rset = st.executeQuery(String.format(
                    "select username, passwd " + "from game_users "
                            + "where username='%s' and passwd='%s'", uname,
                    processedPasswd));

            if (!rset.next()) {
                return false;
                // TODO log es throw new UserDAOException
            }
        } catch (SQLException e) {
            // TODO log nem lehet kapcsolodni
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean registerUser(String uname, String passwd)
            throws IOException, ExistingUserException {
        String processedPasswd = Utils.processPassword(passwd);
        if (processedPasswd == null) {
            // TODO log nem sikerult kodolni a jelszot
            return false;
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rset = st.executeQuery(String.format(
                    "select count(username) " + "from game_users "
                            + "where username='%s'", uname));
            if (rset.next() && rset.getInt(1) > 0) {
                // TODO log
                throw new ExistingUserException();
            }
            st.executeUpdate(String.format(
                    "insert into game_users values('%s', '%s')", uname,
                    processedPasswd));
        } catch (SQLException e) {
            // TODO log - kapcsolodasi hiba
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}
