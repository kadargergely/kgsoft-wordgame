package hu.unideb.kgsoft.scrabble.model.db;

import static hu.unideb.kgsoft.scrabble.Main.logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class DBConnection {
	
	private static EntityManagerFactory entityManagerFactory;
	
	static EntityManager getEntityManager() throws PersistenceException {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("db");
			logger.info("Database connection opened.");
		}
		return entityManagerFactory.createEntityManager();
	}
	
	public static void close() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
			logger.info("Database connection closed.");
		}
	}
}
