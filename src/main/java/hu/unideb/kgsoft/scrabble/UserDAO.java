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

import hu.unideb.kgsoft.scrabble.utils.Utils;
import static hu.unideb.kgsoft.scrabble.Main.logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The {@code UserDAO} class implements simple registering and logging in for
 * users. Handling game loading and saving is done by the {@code SavedGameDAO}
 * class.
 * 
 * @author kadar_000
 *
 */
public class UserDAO {

	/**
	 * Simple authentication based on a user name and password. The method
	 * returns {@code true} if the given user name exists in the database and
	 * the given password is correct. Passwords are stored encrypted in the
	 * database, and only encrypted passwords are compared. See the
	 * {@link Utils} class for details about encryption. If the file containing
	 * the user name and password for the main database is missing,
	 * {@code FileNotFoundException} is thrown.
	 * 
	 * @param uname
	 *            the name of the user
	 * @param passwd
	 *            the password of the user
	 * @return {@code true} if the authentication was successful, {@code false}
	 *         otherwise
	 * @throws IOException
	 *             when the file containing the user name and password for the
	 *             main database is missing
	 */
	public boolean authenticateUser(String uname, String passwd)
			throws IOException {
		String processedPasswd = Utils.processPassword(passwd);
		if (processedPasswd == null) {
			logger.error("User's password could not be encrypted.");
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
			}
		} catch (SQLException e) {
			logger.warn("Couldn't connect to the database.");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Creates a new user in the central database with the given user name and
	 * password. Returns {@code true} if the registration of the user was
	 * successful. Passwords are stored encrypted. See the {@link Utils} class
	 * for details about encryption. If there is already a user registered with
	 * the given user name, {@code ExistingUserException} is thrown. If the file
	 * containing the user name and password for the main database is missing,
	 * {@code FileNotFoundException} is thrown.
	 * 
	 * @param uname
	 *            the user name to register
	 * @param passwd
	 *            the password of the new user
	 * @return {@code true} if the user was successfully registered
	 * @throws IOException
	 *             when the file containing the user name and password for the
	 *             main database is missing
	 * @throws ExistingUserException
	 *             when there is already a user in the database with the given
	 *             name
	 */
	public boolean registerUser(String uname, String passwd)
			throws IOException, ExistingUserException {
		String processedPasswd = Utils.processPassword(passwd);
		if (processedPasswd == null) {
			logger.error("User's password could not be encrypted.");
			return false;
		}

		try (Connection connection = ConnectionFactory.getConnection()) {
			Statement st = connection.createStatement();
			ResultSet rset = st.executeQuery(String.format(
					"select count(username) " + "from game_users "
							+ "where username='%s'", uname));
			if (rset.next() && rset.getInt(1) > 0) {				
				throw new ExistingUserException();
			}
			st.executeUpdate(String.format(
					"insert into game_users values('%s', '%s')", uname,
					processedPasswd));
		} catch (SQLException e) {
			logger.warn("Couldn't connect to the database.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
