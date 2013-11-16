import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Class used to calculate stock price by taking random vector into stock price model
 * 
 * @author yixia
 *
 */
public class StockPathGenerator implements StockPath {
	
	private double r;
	private double sigma;
	private double s0;
	private RandomVectorGenerator rvg; 
	

	public StockPathGenerator(double r, double sigma, double s0, RandomVectorGenerator rvg) {
		
		this.r = r;
		this.sigma = sigma;
		this.s0 = s0;
		this.rvg = rvg;
	}
	
	@Override
	public List<Pair<DateTime,Double>> getPrices() {
		
		double[] vectorArray = rvg.getVector();
		List<Pair<DateTime,Double>> stockpath = new ArrayList<Pair<DateTime,Double>>();
		double prices = s0;
		
		for (int i=0;i<252;i++) {
			//Set today as the start date of the stock price simulation 
			DateTime dateTime = new DateTime();
			Double d = new Double(prices);
			stockpath.add(new Pair<DateTime,Double>(dateTime.plusDays(i), d));
			
			//Stock prices model
			prices = prices*Math.exp((r-sigma*sigma/2) + sigma*vectorArray[i]);
		}
		
		return stockpath;
	}

}
