import java.util.HashMap;
import java.util.Iterator;

import orderGenerator.Message;
import orderGenerator.NewOrder;
import orderGenerator.OrderCxR;

/**
 * The ExchangeManager class implements the whole process when received an
 * order, including execute order and add order if necessary.
 * 
 * The ExchangeManager class includes methods for processing an order, and for
 * output the state of all the books in the exchange.
 * 
 * @author yixia
 * 
 */
public class ExchangeManager {

	// store all the ask books
	HashMap<String, Book> ask = new HashMap<String, Book>();
	// store all the bid books
	HashMap<String, Book> bid = new HashMap<String, Book>();
	// Map OrderId to order
	HashMap<String, NewOrderImpl> orderDictionary = new HashMap<String, NewOrderImpl>();

	/**
	 * Process a new order, first check empty book situation, then execute the
	 * new order, and add to corresponding book if necessary.
	 * 
	 * @param message
	 *            received order
	 * @param print
	 *            signal for output, true for output (runner) and false for no
	 *            output (silent runner)
	 */
	public void processOrder(Message message, boolean print) {
		if (message instanceof NewOrder) {
			NewOrder newOrder = (NewOrder) message;
			String symbol = newOrder.getSymbol();
			int size = newOrder.getSize();
			String orderId = newOrder.getOrderId();
			Double limitPrice = newOrder.getLimitPrice();

			// create a copy of the new order which is a instance of
			// NewOrderImpl
			NewOrderImpl myOrder = new NewOrderImpl(symbol, Math.abs(size),
					orderId, limitPrice);

			// buy order
			if (size >= 0) {
				// if ask book is empty, create new ask book and add order to
				// bid book
				if (!ask.containsKey(symbol)) {
					Book newAsk = new Book(symbol, BookType.ASK);
					ask.put(symbol, newAsk);

					// if bid book is empty, create new bid book
					if (!bid.containsKey(symbol)) {
						Book newBid = new Book(symbol, BookType.BID);
						bid.put(symbol, newBid);
					}

					// add order to bid book
					bid.get(symbol).addOrder(myOrder);
					// add order to orderDictionary
					orderDictionary.put(orderId, myOrder);
				}
				// if ask book is not empty, execute order
				else {
					if (print == true) {
						ask.get(symbol).executeNewOrder(myOrder, true);
					} else {
						ask.get(symbol).executeNewOrder(myOrder, false);
					}
					// if order is not fully traded, put it to bid book
					if (myOrder.getSize() > 0) {
						if (!bid.containsKey(symbol)) {
							Book newBid = new Book(symbol, BookType.BID);
							bid.put(symbol, newBid);
						}

						// add order to bid book
						bid.get(symbol).addOrder(myOrder);
						// add order to orderDictionary
						orderDictionary.put(orderId, myOrder);
					}
				}
				if (print) {
					ask.get(symbol).printTop();
					bid.get(symbol).printTop();
				}
			}

			// sell order
			else {
				if (!bid.containsKey(symbol)) {
					Book newBid = new Book(symbol, BookType.BID);
					bid.put(symbol, newBid);

					if (!ask.containsKey(symbol)) {
						Book newAsk = new Book(symbol, BookType.ASK);
						ask.put(symbol, newAsk);
					}

					// add order to ask book
					ask.get(symbol).addOrder(myOrder);
					// add order to orderDictionary
					orderDictionary.put(orderId, myOrder);

				} else {
					if (print == true) {
						bid.get(symbol).executeNewOrder(myOrder, true);
					} else {
						bid.get(symbol).executeNewOrder(myOrder, false);
					}
					if (myOrder.getSize() > 0) {
						if (!ask.containsKey(symbol)) {
							Book newAsk = new Book(symbol, BookType.ASK);
							ask.put(symbol, newAsk);
						}

						// add order to ask book
						ask.get(symbol).addOrder(myOrder);
						// add order to orderDictionary
						orderDictionary.put(orderId, myOrder);
					}
				}

				if (print) {
					ask.get(symbol).printTop();
					bid.get(symbol).printTop();
				}
			}
		}

		else if (message instanceof OrderCxR) {
			OrderCxR orderCxR = (OrderCxR) message;
			String orderIdCxR = orderCxR.getOrderId();
			int sizeCxR = orderCxR.getSize();
			Double limitPriceCxR = orderCxR.getLimitPrice();

			// find the same orderId to replace
			NewOrderImpl replaceOrder = orderDictionary.get(orderIdCxR);

			int sizeReplaceOrder = replaceOrder.getSize();
			String symbolReplaceOrder = replaceOrder.getSymbol();

			// create a new order denote the replace order we will add
			NewOrderImpl newReplaceOrder = new NewOrderImpl(symbolReplaceOrder,
					Math.abs(sizeCxR), orderIdCxR, limitPriceCxR);

			// search from bid book
			if (sizeReplaceOrder >= 0) {
				bid.get(symbolReplaceOrder).addOrder(newReplaceOrder);
				// set size of replaceOrder to 0, make it dead
				replaceOrder.setSize(0);
				orderDictionary.put(orderIdCxR, newReplaceOrder);
			}
			// search form ask book
			else {
				ask.get(symbolReplaceOrder).addOrder(newReplaceOrder);
				// set size of replaceOrder to 0, make it dead
				replaceOrder.setSize(0);
				orderDictionary.put(orderIdCxR, newReplaceOrder);
			}

			if (print) {
				ask.get(symbolReplaceOrder).printTop();
				bid.get(symbolReplaceOrder).printTop();
			}
		}
	}

	/**
	 * Print the state of all the books in the following format: price, bid/ask,
	 * size, where price is to two decimals places and size is an integer.
	 */
	public void printState() {
		System.out.println("State of the book: ");
		Iterator<String> iterBooks = ask.keySet().iterator();
		while (iterBooks.hasNext()) {
			String symbol = iterBooks.next();
			System.out.println("********** " + symbol + " **********");
			ask.get(symbol).printState();
			bid.get(symbol).printState();
		}
	}
}
