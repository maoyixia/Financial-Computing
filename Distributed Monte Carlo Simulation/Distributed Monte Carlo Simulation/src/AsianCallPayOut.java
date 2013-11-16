import java.util.List;

import org.joda.time.DateTime;

/**
 * Class used to implement Asian Call Option's payout
 * 
 * @author yixia
 *
 */
public class AsianCallPayOut implements PayOut {
	
	private double X;    //strike price
	
	public AsianCallPayOut(double X) {
		this.X = X;
	}


	@Override
	public double getPayout(StockPath path) {
		
		double sum = 0.0;
		double[] payout = new double[252];
		List<Pair<DateTime, Double>> stockPath = path.getPrices();
		
		for(int i=0;i<252;i++) {
			payout[i] = stockPath.get(i).getValue();
			sum += payout[i];
		}
		
		double avg = sum/payout.length;
		
		return Math.max(avg-X, 0);    //Asian call's payout is the maximum between 0 and the average price minus strike price 
	}

}
