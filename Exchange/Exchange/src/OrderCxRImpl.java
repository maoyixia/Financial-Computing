import orderGenerator.OrderCxR;

/**
 * The OrderCxRImpl class implements OrderCxR Interface, it represents a cancel
 * or replace order.
 * 
 * The OrderCxRImpl class includes methods for getting size, orderId and
 * limitPrice of an order.
 * 
 * @author yixia
 * 
 */
public class OrderCxRImpl implements OrderCxR {

	private int size;
	private String orderId;
	private double limitPrice;

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

}
