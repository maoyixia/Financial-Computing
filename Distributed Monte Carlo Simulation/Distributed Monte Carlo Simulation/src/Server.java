import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Server class used to send the simulation request and gather payout results
 * until reach convergence accuracy.
 * 
 * Server class includes methods for sending message via queue (message
 * producer), for receiving message via topic (result consumer).
 * 
 * @author yixia
 * 
 */
public class Server {

	private String type; // option type

	// URL of the JMS server. DEFAULT_BROKER_URL will just mean
	// that JMS server is on localhost
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will be sending messages to
	private static String subject = "SimulationRequest";
	private static Connection connection;

	private static MessageConsumer consumer = null;

	final static String r = "0.0001"; // interest rate
	final String sigma = "0.01"; // stock volatility
	final String s0 = "152.35"; // start price
	final String euro_X = "165"; // Euro call strike price
	final String asian_X = "164"; // Asian call strike price

	private static int batchSize = 100;

	/**
	 * Constructs a new server by initializing an option type (European/Asian).
	 * 
	 * @param type
	 */
	public Server(String type) {
		this.type = type;
	}

	/**
	 * Method used to produce a batch of messages for simulation requests.
	 * 
	 * @throws JMSException
	 */
	public void messageProducer() throws JMSException {

		// ConnectionFactory connectionFactory = new
		// ActiveMQConnectionFactory(url);
		// Connection connection = connectionFactory.createConnection();
		// connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		MessageProducer producer = session.createProducer(destination);

		System.out.println("Send a batch of simulation requests");

		if (type.equals("European")) {
			for (int i = 0; i < batchSize; i++) {
				TextMessage message = session.createTextMessage(r + " " + sigma
						+ " " + s0 + " " + euro_X + " " + type);
				producer.send(message);
				// System.out.println("Sent message '" + message.getText() +
				// "'");
			}
		}

		else if (type.equals("Asian")) {
			for (int i = 0; i < batchSize; i++) {
				TextMessage message = session.createTextMessage(r + " " + sigma
						+ " " + s0 + " " + asian_X + " " + type);
				producer.send(message);
				// System.out.println("Sent message '" + message.getText() +
				// "'");
			}
		}

		else {
			// connection.close();
			System.out.println("Wrong option type!");
		}
	}

	/**
	 * Method used to receive payout calculated by client.
	 * 
	 * We can use different topic name to denote specific company's request.
	 * 
	 * @return payout calculated by client and received from specific topic
	 * @throws JMSException
	 */
	public double resultConsumer() throws JMSException {

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("IBM result");

		if (consumer == null)
			consumer = session.createConsumer(topic);
		Message message = consumer.receive();
		TextMessage textMessage = (TextMessage) message;
		// System.out.println(textMessage.getText());

		return Double.parseDouble(textMessage.getText());

	}

	/**
	 * Main: used to start connection and test for simulation manager
	 * 
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {

		// Set up connection here
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		StatsCollector stats = new StatsCollector();

		final double accuracy = 0.01;
		final double probability = 0.96;

		// Use NormalDistribution in Commons Math to calculate y
		NormalDistribution nd = new NormalDistribution();
		final double y = nd.inverseCumulativeProbability((probability + 1) / 2);

		int N;
		double variance;
		double error = 10;
		double price;

		// We can choose the option type of European or Asian here
		Server server = new Server("European");
		// Server server = new Server("Asian");

		// Simulation Manager
		while (Math.abs(error - accuracy) > 0.001) {
			server.messageProducer();
			for (int j = 0; j < batchSize; j++) {
				stats.AddValue(server.resultConsumer());
			}
			variance = stats.getVariance();
			N = stats.getN();
			error = y * Math.sqrt(variance) / Math.sqrt(N);
		}

		// calculate final option price
		price = Math.exp(-Double.parseDouble(r) * 252) * (stats.getMean());
		System.out.println("Option price is: " + price);

		// close connection when finish simulation
		connection.close();

	}
}
