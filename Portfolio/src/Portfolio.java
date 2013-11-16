/**
 * Portfolio interface
 */

public interface Portfolio {
	
	public abstract void newTrade (String symbol, int quantity);
	public abstract PositionIter getPositionIter();
}
