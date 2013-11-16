import org.apache.commons.math3.random.*;

/**
 * Class used to generate random vector, use RandomGenerator in Commons Math here
 * 
 * @author yixia
 *
 */
public class RandomVectorGeneratorImpl implements RandomVectorGenerator {

	
	@Override
	public double[] getVector() {
		// Create and seed a RandomGenerator
		RandomGenerator rg = new JDKRandomGenerator();
		rg.setSeed(System.currentTimeMillis());    // Set system time as seed because fixed seed means same results every time

		// Create a GassianRandomGenerator using rg as its source of randomness
		GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);
		
		double[] vector = new double[252];
		
		for(int i=0; i<252; i++) {
			vector[i] = rawGenerator.nextNormalizedDouble();
		}
		
		return vector;
	}

}
