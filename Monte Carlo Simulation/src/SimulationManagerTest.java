import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for simulating option prices
 * First result is European call option (option1) price
 * Second result is Asian call option (option2) price
 * 
 * @author yixia
 *
 */
public class SimulationManagerTest {

/*	@Test
	public void EuroCallPriceTest() {
		
		final double r = 0.0001;    //interest rate
		final double sigma = 0.01;    //stock volatility
		final double s0 = 152.35;    //start price
		final double euro_X = 165;    //Euro call strike price
		double euroPrice;
		
		StockPathGenerator spg = new StockPathGenerator(r,sigma,s0,new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl()));
		
		SimulationManager euroOption = new SimulationManager(spg,new StatsCollector());
		euroPrice = Math.exp(-r*252) * euroOption.CalculateOption(new EuroCallPayOut(euro_X));
		
		System.out.println("Euro Call Option(Option1)'s price:" + euroPrice);
	}*/
	
	@Test
	public void AsianCallPriceTest() {
		
		final double r = 0.0001;    //interest rate
		final double sigma = 0.01;    //stock volatility
		final double s0 = 152.35;    //start price
		final double asian_X = 164;    //Asian call strike price
		double asianPrice;
		
		StockPathGenerator spg = new StockPathGenerator(r,sigma,s0,new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl()));
		
		SimulationManager asianOption = new SimulationManager(spg,new StatsCollector());
		asianPrice = Math.exp(-r*252) * asianOption.CalculateOption(new AsianCallPayOut(asian_X));
		
		System.out.println("Asian Call Option(Option2)'s price:" + asianPrice);
	}

}
