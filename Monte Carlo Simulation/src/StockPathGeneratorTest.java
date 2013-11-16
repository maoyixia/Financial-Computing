import static org.junit.Assert.*;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test for Random Vector and Stock Paths
 * The first two lines are two random vector, they are opposite vectors
 * The rest part is the stock path (DateTime and Stock Price)
 * 
 * @author yixia
 *
 */
public class StockPathGeneratorTest {
	
	final double r = 0.0001;
	final double sigma = 0.01;
	final double s0 = 152.35;

	@Test
	public void test() {
		AntiTheticRandomVectorGeneratorImpl rvg = new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl());
		StockPathGenerator spg = new StockPathGenerator(r,sigma,s0,new AntiTheticRandomVectorGeneratorImpl(new RandomVectorGeneratorImpl()));
		
		//Test AntiTheticRandomVectorGenerator
		for(double m:rvg.getVector()) {
			System.out.print(m+" ");
		}
		
		System.out.println();
		
		for(double n:rvg.getVector()) {
			System.out.print(n+" ");
		}
		
		System.out.println();
		System.out.println();
		
		//Test StockPathGenerator
		for(Pair<DateTime,Double> i:spg.getPrices()) {
			System.out.println(i.getKey() + "\t" + i.getValue());
		}
	}

}
