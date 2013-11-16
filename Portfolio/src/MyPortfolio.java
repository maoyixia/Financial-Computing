import java.util.LinkedList;
import java.util.Iterator;

/**
 * Class used to model portfolio with arbitrary number of positions, contains baisc operations for a portfolio
 * I use LinkedList to implement MyPortfolio because its fast add and remove
 * 
 * @author yixia
 *
 */

public class MyPortfolio implements Portfolio {
	
	LinkedList<MyPosition> p = new LinkedList<MyPosition>();
	
	/**
	 * Update the portfolio info after conducting a new trade
	 * When the new trade position exists in the portfolio, add the quantity to the existing position.
	 * If the updating quantity equals 0, remove it from the portfolio
	 * If the new trade position does not exist in the portfolio, add it to the portfolio.
	 * 
	 * @param symbol	The symbol of new trade position
	 * @param quantity	The quantity of new trade position
	 */
	public void newTrade(String symbol, int quantity) {
		MyPosition n = new MyPosition(symbol, quantity);
	
		//signal: 1 indicates that we find same symbol in MyPortfolio; 0 indicates that newTrade position is not in MyPortfolio
		int sign = 0;
		
		//Check if the new trade Position has already in MyPortfolio
		for (MyPosition i : p) {
			if (i.getSymbol().equals(n.getSymbol()) == true) {
				i.setQuantity(n.getQuantity());
				sign = 1;		
			}
		}
		
		//If the new trade Position is not in MyPortfolio, add it in
		if (sign == 0) {
			p.add(n);
		}
		
		//If the quantity of Position is 0, remove it from MyPortfolio 
		Iterator<MyPosition> iter = p.iterator();
		while(iter.hasNext()) {
			if(iter.next().getQuantity() == 0) {
				iter.remove();
			}
		}
	}

	/**
	 * @return The next position in the portfolio and null if we already iterated over all the position
	 */
	public PositionIter getPositionIter() {
		MyPositionIter positer = new MyPositionIter(p);
		return positer;
	}

}
