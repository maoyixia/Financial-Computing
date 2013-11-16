import java.util.HashMap;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.joda.time.DateTime;

/**
 * Class used to run the simulation and decide when to stop
 * 
 * @author yixia
 *
 */
public class SimulationManager {

	private StockPath path;
	private StatsCollector stats;
	final double accuracy = 0.01;
	final double probability = 0.96;
	
	//Use NormalDistribution in Commons Math to calculate y
	NormalDistribution nd = new NormalDistribution();
	final double y = nd.inverseCumulativeProbability((probability + 1) / 2);

	public SimulationManager(StockPath path,StatsCollector stats) {
		this.path = path;
		this.stats = stats;		
	}

	public double CalculateOption(PayOut payout) {
		
		int N;
		double variance;
		double error = 10;
		
		//When the estimated error is smaller than accuracy, stop simulation
		while (Math.abs(error - accuracy) > 0.001) {
			stats.AddValue(payout.getPayout(path));
			variance = stats.getVariance();
			N = stats.getN();
			error = y*Math.sqrt(variance)/Math.sqrt(N);
	//		System.out.println(payout.getPayout(path));
		}
		
		System.out.println("Simulated " + stats.getN() + " times.");
		
		return stats.getMean();
	}
}
