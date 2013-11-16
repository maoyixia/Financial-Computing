import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test for Asian Call Option's payout
 * 
 * @author yixia
 *
 */
public class AsianCallPayOutTest {

	final double r = 0.0001;    //interest rate
	final double sigma = 0.01;    //stock volatility
	final double s0 = 152.35;    //start price

	double X = 164;    //strike price
	
	@Test
	public void test() {
		
		StockPathGenerator spg = new StockPathGenerator(r,sigma,s0,new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl()));
		AsianCallPayOut ecpo = new AsianCallPayOut(X);
		
		System.out.println("Asian Call Payout is: " + ecpo.getPayout(spg));
		
	}
}
