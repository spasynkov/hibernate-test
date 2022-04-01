package stas;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HibernateConnector {
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
			Persistence.createEntityManagerFactory("MySuperUnitName");

	private EntityManager entityManager;

	public void save(Object o) {
		executeWithTransaction(() -> entityManager.persist(o));
	}

	public void update(Object o) {
		executeWithTransaction(() -> entityManager.merge(o));
	}

	public <T> List<T> getAll(Class<T> type) {
		prepareConnection();
		try {
			String query = "select m from " + type.getSimpleName() + " m";
			return (List<T>) entityManager.createQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			entityManager.close();
			return Collections.emptyList();
		}
	}

	private void prepareConnection() {
		entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
	}

	private void executeWithTransaction(Runnable lambda) {
		prepareConnection();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			lambda.run();

			transaction.commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();

			transaction.rollback();
			entityManager.close();
		}
	}
}
