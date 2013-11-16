import java.util.Iterator;

import orderGenerator.Message;
import orderGenerator.OrdersIterator;

/**
 * The Runner class used to simulate the exchange process and print the process
 * and final state of the book.
 * 
 * @author yixia
 * 
 */
public class Runner {

	/**
	 * Main used to simulate exchange process.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Runner run = new Runner();
		run.runner();
	}

	/**
	 * runner method used to replay the supplied iterator and after processing
	 * every order, print top of the book and print the state of the book
	 * finally.
	 */
	public void runner() {
		ExchangeManager exchange = new ExchangeManager();
		Iterator<Message> iter = OrdersIterator.getIterator();
		while (iter.hasNext()) {
			exchange.processOrder(iter.next(), true);
		}

		// print the state of the book
		exchange.printState();
	}

}
