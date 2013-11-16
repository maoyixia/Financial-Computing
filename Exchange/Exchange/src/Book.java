import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * The Book class represents exchange books. It can be either ASK book or BID
 * book of a specific symbol.
 * 
 * The Book class includes methods for executing a new order, for adding an
 * order to the book, for output the state of a book, and for output the top of
 * a book.
 * 
 * @author yixia
 * 
 */
public class Book {

	private String symbol;
	private BookType type;

	// book data structure
	TreeMap<Double, LinkedList<NewOrderImpl>> book = new TreeMap<Double, LinkedList<NewOrderImpl>>();

	// used to display two decimal of the price
	DecimalFormat df = new DecimalFormat("##.00");

	/**
	 * Constructs a new book by passing its symbol and book type
	 * 
	 * @param symbol
	 *            symbol of the book
	 * @param type
	 *            type of the book
	 */
	public Book(String symbol, BookType type) {
		this.symbol = symbol;
		this.type = type;
	}

	/**
	 * Executes a new order. If it is a market order, execute it immediately
	 * until it is consumed. If it is a limit order, find the best price in the
	 * book and trade it until trade with all the order better or equal than
	 * limit price.
	 * 
	 * @param myOrder
	 *            received order
	 * @param print
	 *            signal for output, true for output (runner) and false for no
	 *            output (silent runner)
	 */
	public void executeNewOrder(NewOrderImpl myOrder, boolean print) {

		Double neworderLimitPrice = myOrder.getLimitPrice();
		int neworderSize = myOrder.getSize();

		// market order
		if (neworderLimitPrice.isNaN()) {

			Iterator<Double> iterBook = book.keySet().iterator();
			if (type == BookType.BID) {
				iterBook = book.descendingKeySet().iterator();
			}

			// loop price of the book
			out: while (iterBook.hasNext()) {

				Double priceLevel = iterBook.next();
				Iterator<NewOrderImpl> iterOrderQueue = book.get(priceLevel)
						.iterator();
				// loop orders in order queue of a specific price
				while (iterOrderQueue.hasNext()) {
					NewOrderImpl order = iterOrderQueue.next();
					// remove the dead order in the front of order queue, this
					// takes O(1) time
					if (order.getSize() == 0) {
						book.get(priceLevel).remove();
						continue;
					}

					// after trade, new order is not consumed to empty
					if ((neworderSize - order.getSize()) > 0) {
						if (print == true) {
							System.out.println("Order " + myOrder.getOrderId()
									+ " traded with order "
									+ order.getOrderId());
							System.out.println();
						}
						myOrder.setSize(neworderSize - order.getSize());
						// set order size to 0, make it dead
						order.setSize(0);
					}

					// after trade, new order is consumed to empty, order is
					// empty, perfectly execute
					else if ((neworderSize - order.getSize()) == 0) {
						if (print == true) {
							System.out.println("Order " + myOrder.getOrderId()
									+ " traded with order "
									+ order.getOrderId());
							System.out.println();
						}
						// set new order size to 0, make it dead
						myOrder.setSize(0);
						// set order size to 0, make it dead
						order.setSize(0);
						break out;
					}

					// after trade, new order is consumed empty, order has left
					// volume
					else {
						if (print == true) {
							System.out.println("Order " + myOrder.getOrderId()
									+ " traded with order "
									+ order.getOrderId());
							System.out.println();
						}
						// set new order size to 0, make it dead
						myOrder.setSize(0);
						order.setSize(order.getSize() - neworderSize);
						break out;
					}
				}
			}
		}

		// limit order
		else {
			Iterator<Double> iterBook = book.keySet().iterator();
			if (type == BookType.BID) {
				iterBook = book.descendingKeySet().iterator();
			}

			// loop price of the book
			out: while (iterBook.hasNext()) {
				Double priceLevel = iterBook.next();
				// if priceLevel does not exceed limit price
				if (type == BookType.ASK && priceLevel <= neworderLimitPrice
						|| type == BookType.BID
						&& priceLevel >= neworderLimitPrice) {
					Iterator<NewOrderImpl> iterOrderQueue = book
							.get(priceLevel).iterator();

					// loop orders in order queue of a specific price
					while (iterOrderQueue.hasNext()) {
						NewOrderImpl order = iterOrderQueue.next();
						// remove the dead order in the front of order queue,
						// this takes O(1) time
						if (order.getSize() == 0) {
							book.get(priceLevel).remove();
							continue;
						}

						// after trade, new order is not consumed to empty
						if ((neworderSize - order.getSize()) > 0) {
							if (print == true) {
								System.out.println("Order "
										+ myOrder.getOrderId()
										+ " traded with order "
										+ order.getOrderId());
								System.out.println();
							}
							myOrder.setSize(neworderSize - order.getSize());
							order.setSize(0);
							order = iterOrderQueue.next();
						}

						// after trade, new order is consumed to empty, order is
						// empty, perfectly execute
						else if ((neworderSize - order.getSize()) == 0) {
							if (print == true) {
								System.out.println("Order "
										+ myOrder.getOrderId()
										+ " traded with order "
										+ order.getOrderId());
								System.out.println();
							}
							myOrder.setSize(0);
							order.setSize(0);
							break out;
						}

						// after trade, new order is consumed empty, order has
						// left
						// volume
						else {
							if (print == true) {
								System.out.println("Order "
										+ myOrder.getOrderId()
										+ " traded with order "
										+ order.getOrderId());
								System.out.println();
							}
							myOrder.setSize(0);
							order.setSize(order.getSize() - neworderSize);
							break out;
						}
					}
					priceLevel = iterBook.next();
				}

				// if priceLevel exceeds limit price, break
				else
					break;
			}
		}

	}

	/**
	 * Add a new order to the book if it is not consumed to empty.
	 * 
	 * @param myOrder
	 *            received order
	 */
	public void addOrder(NewOrderImpl myOrder) {

		Double limitPrice = myOrder.getLimitPrice();

		// if price of limit order in bid book has no order, create a new order
		// queue and add the new order to the last
		if (!book.containsKey(limitPrice)) {
			LinkedList<NewOrderImpl> orderQueue = new LinkedList<NewOrderImpl>();
			book.put(limitPrice, orderQueue);
			book.get(limitPrice).addLast(myOrder);
		}

		// if price of limit order in bid book has orders, add new order to the
		// last of order queue
		else {
			book.get(limitPrice).addLast(myOrder);
		}
	}

	/**
	 * Print the state of the book in the following format: price, bid/ask,
	 * size, where price is to two decimals places and size is an integer.
	 */
	public void printState() {

		Iterator<Double> iterBook = book.keySet().iterator();
		if (type == BookType.BID) {
			iterBook = book.descendingKeySet().iterator();
		}

		while (iterBook.hasNext()) {
			int sizeSum = 0;
			Double price = iterBook.next();
			Iterator<NewOrderImpl> iterOrderQueue = book.get(price).iterator();
			while (iterOrderQueue.hasNext()) {
				NewOrderImpl order = iterOrderQueue.next();
				sizeSum += order.getSize();
			}
			if (sizeSum != 0)
				System.out.println(df.format(price) + ", " + type + ", "
						+ sizeSum);
		}
	}

	/**
	 * Print the top of the book, output the best price and its size of the
	 * book.
	 */
	public void printTop() {
		Iterator<Double> iterBook = book.keySet().iterator();
		if (type == BookType.BID) {
			iterBook = book.descendingKeySet().iterator();
		}

		while (iterBook.hasNext()) {
			int sizeSum = 0;
			Double price = iterBook.next();
			Iterator<NewOrderImpl> iterOrderQueue = book.get(price).iterator();
			while (iterOrderQueue.hasNext()) {
				NewOrderImpl order = iterOrderQueue.next();
				sizeSum += order.getSize();
			}
			if (sizeSum != 0) {
				System.out.println("---------- Top of " + symbol + " " + type
						+ " Book ----------");
				System.out.println(df.format(price) + ", " + type + ", "
						+ sizeSum);
				System.out.println();
				break;
			}
		}

	}
}
