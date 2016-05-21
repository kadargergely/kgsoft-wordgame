package hu.unideb.kgsoft.scrabble.model.db;

import static hu.unideb.kgsoft.scrabble.Main.logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import hu.unideb.kgsoft.scrabble.ExistingUserException;
import hu.unideb.kgsoft.scrabble.model.UserEntity;
import hu.unideb.kgsoft.scrabble.utils.Utils;

public class UserDAO {

	public static boolean authenticateUser(String uname, String passwd) throws DBConnectionException {
		String processedPasswd = Utils.processPassword(passwd);
		if (processedPasswd == null) {
			logger.error("User's password could not be encrypted.");
			return false;
		}
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			TypedQuery<Long> query = manager.createQuery(
					"SELECT count(u.userId) FROM UserEntity u WHERE u.username = :username AND u.password = :passwd",
					Long.class);
			query.setParameter("username", uname);
			query.setParameter("passwd", processedPasswd);
			if (query.getSingleResult() != 1) {
				return false;
			}
		} catch (PersistenceException e) {
			throw new DBConnectionException();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}			
		}

		return true;
	}
	
	public static boolean registerUser(String uname, String passwd) throws ExistingUserException, DBConnectionException {
		String processedPasswd = Utils.processPassword(passwd);
		if (processedPasswd == null) {
			logger.error("User's password could not be encrypted.");
			return false;
		}
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			TypedQuery<Long> query = manager.createQuery(
					"SELECT count(u.userId) FROM UserEntity u WHERE u.username = :uname", Long.class);
			query.setParameter("uname", uname);
			if (query.getSingleResult() != 0) {
				throw new ExistingUserException();
			}
			manager.getTransaction().begin();
			manager.persist(new UserEntity(0, uname, processedPasswd, null));
			manager.getTransaction().commit();
		} catch (PersistenceException e) {
			throw new DBConnectionException();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
		
		return true;
	}
}
