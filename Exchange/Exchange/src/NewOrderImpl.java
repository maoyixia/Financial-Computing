import orderGenerator.NewOrder;

/**
 * The NewOrderImpl class implements NewOrder Interface, it represents a new
 * order.
 * 
 * The NewOrderImpl class includes methods for getting and setting symbol, size,
 * orderId and limitPrice of an order.
 * 
 * @author yixia
 * 
 */
public class NewOrderImpl implements NewOrder {

	private String symbol;
	private int size;
	private String orderId;
	private double limitPrice;

	/**
	 * Constructs a new order by passing its symbol, size, orderId and
	 * limitPrice.
	 * 
	 * @param symbol
	 * @param size
	 * @param orderId
	 * @param limitPrice
	 */
	public NewOrderImpl(String symbol, int size, String orderId,
			double limitPrice) {
		this.symbol = symbol;
		this.size = size;
		this.orderId = orderId;
		this.limitPrice = limitPrice;
	}

	/**
	 * Get symbol of an order.
	 */
	@Override
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Get size of an order.
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Get orderId of an order.
	 */
	@Override
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Get limitPrice of an order.
	 */
	@Override
	public double getLimitPrice() {
		return limitPrice;
	}

	/**
	 * Set symbol of an order.
	 */
	public void setSymbol(String newSymbol) {
		symbol = newSymbol;
	}

	/**
	 * Set size of an order.
	 */
	public void setSize(int newSize) {
		size = newSize;
	}

	/**
	 * Set orderId of an order.
	 */
	public void setOrderId(String newOrderId) {
		orderId = newOrderId;
	}

	/**
	 * Set limitPrice of an order.
	 */
	public void setLimitPrice(double newLimitPrice) {
		limitPrice = newLimitPrice;
	}
}
