package hu.unideb.kgsoft.scrabble.model.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import hu.unideb.kgsoft.scrabble.model.FieldEntity;
import hu.unideb.kgsoft.scrabble.model.NoSavedGameException;
import hu.unideb.kgsoft.scrabble.model.SavedGame;
import hu.unideb.kgsoft.scrabble.model.UserEntity;

public class SavedGameDAO {

	public static SavedGame getSavedGameByUserName(String username) throws NoSavedGameException, DBConnectionException {
		SavedGame savedGame = null;
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			TypedQuery<SavedGame> query = manager.createQuery(
					"SELECT sg FROM SavedGame sg WHERE sg.user.username = :username", SavedGame.class);
			savedGame = query.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			throw new NoSavedGameException();
	    } catch (PersistenceException e) {
			throw new DBConnectionException();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
		return savedGame;
	}

	public static void newSavedGame(String username, SavedGame savedGame) throws DBConnectionException {
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			TypedQuery<UserEntity> userQuery = manager
					.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class);
			UserEntity user = userQuery.setParameter("username", username).getSingleResult();
			savedGame.setUser(user);
			SavedGame oldSavedGame = user.getSavedGame();
			if (oldSavedGame == null) {
				manager.getTransaction().begin();
				manager.persist(savedGame);
				manager.getTransaction().commit();
			} else {
				manager.getTransaction().begin();
				List<FieldEntity> oldFields = oldSavedGame.getFields();
				for (FieldEntity field : oldFields) {
					manager.remove(field);
				}
				manager.remove(oldSavedGame);
				manager.persist(savedGame);
				manager.getTransaction().commit();
			}
		} catch (PersistenceException e) {
			throw new DBConnectionException();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}		
	}

}
