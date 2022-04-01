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
		Message message = new Message();
		message.setText("test");

		saveMessageToDb(message);

		List<Message> messages = readMessagesFromDb();
		printMessagesFromDb(messages);

		message = messages.get(0);
		message.setText("Hello World");

		updateMessageInDb(message);

		messages = readMessagesFromDb();
		printMessagesFromDb(messages);
	}

	private static void printMessagesFromDb(Iterable<Message> messages) {
		messages.forEach(System.out::println);
		System.out.println();
	}

	private static void updateMessageInDb(Message message) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			entityManager.merge(message);

			transaction.commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();

			transaction.rollback();
			entityManager.close();
		}
	}

	private static void saveMessageToDb(Message message) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

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
