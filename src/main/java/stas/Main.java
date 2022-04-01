package stas;

import java.util.List;

import stas.entity.Message;

public class Main {
	private static final HibernateConnector CONNECTOR = new HibernateConnector();

	public static void main(String[] args) {
		Message message = new Message();
		message.setText("test");

		CONNECTOR.save(message);

		List<Message> messages = CONNECTOR.getAll(Message.class);
		printMessages(messages);

		message = messages.get(0);
		message.setText("Hello World");

		CONNECTOR.update(message);

		messages = CONNECTOR.getAll(Message.class);
		printMessages(messages);
	}

	private static void printMessages(Iterable<Message> messages) {
		messages.forEach(System.out::println);
		System.out.println();
	}
}
