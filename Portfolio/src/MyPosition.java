/**
 * Class used to model a position, contains elements and operations for a position
 * 
 * @author Yixia
 *
 */

public class MyPosition implements Position {
	
	private String symbol;
	private int quantity;

	
	/**
	 * Constructor used to create symbol and quantity for a position
	 * 
	 * @param symbol
	 * @param quantity
	 */
	public MyPosition (String symbol, int quantity) {
		this.symbol = symbol;
		this.quantity = quantity;
	}
	
	/**
	 * Get quantity value of a position
	 * 
	 * @return int
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Set quantity of a MyPosition if its value changed by new trade
	 * @param change	The quantity of new trade position
	 * @return the updated quantity of MyPosition in MyPortfolio
	 */
	public void setQuantity(int change) {
		quantity += change;
	}

	/**
	 * Get symbol from MyPosition
	 * 
	 * @return String
	 * @throws NullPointerException		When symbol is null, throw NullPointerException
	 */
	public String getSymbol() throws NullPointerException {
		if (symbol != null){
			return symbol;
		}
		else{
			throw new NullPointerException();
		}
	}
	
	/**
	 * Override equals method of MyPosition class for the convenience of test
	 * Two MyPosition are equal if and only if they have same symbol and equal quantity
	 */
	@Override
	public boolean equals (Object p) {
		Position pos=(Position) p;
		if (this.getSymbol().equals(pos.getSymbol()) && this.getQuantity() == pos.getQuantity()) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

