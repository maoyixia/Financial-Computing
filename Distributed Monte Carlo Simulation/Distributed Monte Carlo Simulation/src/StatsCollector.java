import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to keep track of stats like mean and variance
 * 
 * @author yixia
 * 
 */
public class StatsCollector {

	private int N = 1;

	double mean = 0.0;
	double variance = 0.0;

	/**
	 * Method used to update mean and variance when adding a new payout
	 * 
	 * @param value
	 *            new payout
	 */
	public void AddValue(double value) {

		mean = (double) N / (N + 1) * mean + value / (N + 1);
		variance = (double) N / (N + 1) * variance + Math.pow(value, 2)
				/ (N + 1);
		N++;
	}

	public int getN() {
		return N;
	}

	public double getMean() {
		return mean;
	}

	public double getVariance() {
		return variance;
	}

}
