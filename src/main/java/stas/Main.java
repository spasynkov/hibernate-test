package stas;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import stas.entity.Message;

public class Main {
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
			Persistence.createEntityManagerFactory("MySuperUnitName");

	public static void main(String[] args) {
		saveMessageToDb();
		List<Message> messages = readMessagesFromDb();
		messages.forEach(System.out::println);
	}

	private static void saveMessageToDb() {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			Message message = new Message();
			message.setText("test");

			entityManager.persist(message);
			transaction.commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();

			transaction.rollback();
			entityManager.close();
		}
	}

	private static List<Message> readMessagesFromDb() {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.createQuery("select m from Message m").getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			entityManager.close();
			return Collections.emptyList();
		}
	}

	private static EntityManager getEntityManager() {
		return ENTITY_MANAGER_FACTORY.createEntityManager();
	}
}
