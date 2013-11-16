/**
 * Class act as a decorator that used to decorate AntiThetic on RandomVectorGenerator
 * 
 * @author yixia
 *
 */
abstract public class AntiTheticDecorator extends RandomVectorGeneratorImpl{
	protected RandomVectorGeneratorImpl decoratedRVG;
	
	public AntiTheticDecorator (RandomVectorGeneratorImpl decoratedRVG) {
		this.decoratedRVG = decoratedRVG;
	}
	
	public double[] getVector() {
		return decoratedRVG.getVector();
	}
}
