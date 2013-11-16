import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author yixia
 *
 */
public class EuroCallPayOutTest {
	
	final double r = 0.0001;    //interest rate
	final double sigma = 0.01;    //stock volatility
	final double s0 = 152.35;    //start price

	double X = 165;    //strike price
	
	
	
	@Test
	public void test() {
		
		StockPathGenerator spg = new StockPathGenerator(r,sigma,s0,new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl()));
		EuroCallPayOut ecpo = new EuroCallPayOut(X);
		
		System.out.println("Euro Call Payout is: " + ecpo.getPayout(spg));
	}

}
