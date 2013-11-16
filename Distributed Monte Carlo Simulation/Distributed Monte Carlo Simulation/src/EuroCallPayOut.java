/**
 * 
 */

/**
 * @author yixia
 *
 */
public class EuroCallPayOut implements PayOut {

	private double X;    //strike price
	
	public EuroCallPayOut(double X) {
		this.X = X;
	}
	
	@Override
	public double getPayout(StockPath path) {
		
		double sT = path.getPrices().get(251).getValue();
	//	System.out.println(sT);
		return Math.max(sT-X, 0);    //Euro call's payout is the maximum between 0 and last day (expiration) price minus strike price
	}

}
