import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Client class used to receive simulation request and execute simulation task.
 * 
 * Client class includes methods for receiving message via queue (message
 * consumer) and sending message via topic (result producer).
 * 
 * @author yixia
 * 
 */
public class Client {
	// URL of the JMS server
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will receive messages from
	private static String subject = "SimulationRequest";

	private static MessageConsumer consumer = null;

	/**
	 * Method used to receive simulation request and calculate one payout and
	 * return to Server via specific topic.
	 * 
	 * @throws JMSException
	 */
	public void messageConsumer() throws JMSException {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// Getting the queue 'SimulationRequest'
		Destination destination = session.createQueue(subject);

		// consumer is used for receiving (consuming) messages
		// we use factory design pattern to guarantee only have one consumer
		// instance here
		if (consumer == null)
			consumer = session.createConsumer(destination);

		// Here we receive the message
		// Use MessageListenr Class and override onMessage method
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {

				String resultString = new String();

				TextMessage textMessage = (TextMessage) message;
				try {
					// System.out.println("Received message '"
					// + textMessage.getText() + "'");
					String[] arg = textMessage.getText().split(" ");

					if (arg[4].equals("European")) {
						StockPathGenerator spg = new StockPathGenerator(Double
								.parseDouble(arg[0]), Double
								.parseDouble(arg[1]), Double
								.parseDouble(arg[2]),
								new RandomVectorGeneratorImpl());
						EuroCallPayOut ecpo = new EuroCallPayOut(Double
								.parseDouble(arg[3]));
						System.out.println("European Call Payout is: "
								+ ecpo.getPayout(spg));
						resultString = String.valueOf(ecpo.getPayout(spg));
					}

					else if (arg[4].equals("Asian")) {
						StockPathGenerator spg = new StockPathGenerator(Double
								.parseDouble(arg[0]), Double
								.parseDouble(arg[1]), Double
								.parseDouble(arg[2]),
								new RandomVectorGeneratorImpl());
						AsianCallPayOut acpo = new AsianCallPayOut(Double
								.parseDouble(arg[3]));
						System.out.println("Asian Call Payout is: "
								+ acpo.getPayout(spg));
						resultString = String.valueOf(acpo.getPayout(spg));
					} else {
						System.out.println("Wrong option type!");
					}
					System.out.println("waiting...");

					// send our payout result once client receive a simulation
					// request
					resultProducer(resultString);

				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Method used to send the payout result to specific topic.
	 * 
	 * @param result
	 * @throws JMSException
	 */
	public void resultProducer(String result) throws JMSException {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("IBM result");
		MessageProducer producer = session.createProducer(topic);
		TextMessage message = session.createTextMessage(result);
		producer.send(message);
		connection.close();
	}

	/**
	 * Main: start client and execute messageConsumer (receive message from
	 * queue and send the payout result to topic)
	 * 
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {

		// ConnectionFactory connectionFactory = new
		// ActiveMQConnectionFactory(url);
		// connection = connectionFactory.createConnection();
		// connection.start();

		Client client = new Client();

		client.messageConsumer();

	}

}
