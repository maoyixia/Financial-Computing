import java.util.Iterator;

import orderGenerator.Message;
import orderGenerator.OrdersIterator;

/**
 * The SilentRunner class used to simulate the exchange process and not print
 * the process, but print final state of the book.
 * 
 * @author yixia
 * 
 */
public class SilentRunner {

	/**
	 * Main used to simulate exchange process.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SilentRunner run = new SilentRunner();
		run.silentRunner();
	}

	/**
	 * silentRunner method used to replay the iterator but will not print the
	 * process. It is used for efficiency testing.
	 */
	public void silentRunner() {
		ExchangeManager exchange = new ExchangeManager();
		Iterator<Message> iter = OrdersIterator.getIterator();
		while (iter.hasNext()) {
			exchange.processOrder(iter.next(), false);
		}

		// print the state of the book
		exchange.printState();
	}

}
