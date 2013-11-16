import java.util.LinkedList;
import junit.framework.Assert;
import org.junit.Test;


/**
 * Test for the business logic of MyPortfolio
 * 
 * @author yixia
 *
 */

public class MyPortfolioTest {
	
	MyPortfolio portfolio = new MyPortfolio();
	
	/**
	 * Check the normal scenario: the creation of the portfolio with different positions
	 */
	@Test
	public void testNewTrade1() {
		LinkedList<Position> actual = new LinkedList<Position>();
		LinkedList<Position> expected = new LinkedList<Position>();
		
		portfolio.newTrade("Google", 200);
		portfolio.newTrade("IBM", 100);
		portfolio.newTrade("MSFT", -300);
		
		PositionIter iter = portfolio.getPositionIter();
		Position pos = iter.getNextPosition();
		
		while (pos != null) {
			actual.add(pos);
			pos = iter.getNextPosition();
		}
		
		expected.add(new MyPosition("Google", 200));
		expected.add(new MyPosition("IBM", 100));
		expected.add(new MyPosition("MSFT", -300));
		
		Assert.assertEquals(expected, actual);	
	}
	
	/**
	 * Check the scenario: add a new position has the same symbol (security name) as the old position in portfolio
	 */
	@Test
	public void testNewTrade2() {
		LinkedList<Position> actual = new LinkedList<Position>();
		LinkedList<Position> expected = new LinkedList<Position>();
		
		portfolio.newTrade("Google", 200);
		portfolio.newTrade("IBM", 100);
		portfolio.newTrade("MSFT", -300);
		portfolio.newTrade("Google", 300);			//here add a new position has the existing symbol name
		
		PositionIter iter=portfolio.getPositionIter();
		Position pos=iter.getNextPosition();
		
		while (pos != null) {
			actual.add(pos);
			pos = iter.getNextPosition();
		}
		
		expected.add(new MyPosition("Google", 500));
		expected.add(new MyPosition("IBM", 100));
		expected.add(new MyPosition("MSFT", -300));
		
		Assert.assertEquals(expected, actual);	
	}
	
	/**
	 * Check the scenario: when the updating quantity equals 0, remove it from the portfolio
	 */
	@Test
	public void testNewTrade3() {
		LinkedList<Position> actual = new LinkedList<Position>();
		LinkedList<Position> expected = new LinkedList<Position>();
		
		portfolio.newTrade("Google", 200);
		portfolio.newTrade("IBM", 100);
		portfolio.newTrade("MSFT", -300);
		portfolio.newTrade("Google", -200);
		
		PositionIter iter = portfolio.getPositionIter();
		Position pos = iter.getNextPosition();
		
		while (pos != null) {
			actual.add(pos);
			pos = iter.getNextPosition();
		}
		
		expected.add(new MyPosition("IBM", 100));
		expected.add(new MyPosition("MSFT", -300));
		
		Assert.assertEquals(expected, actual);	
	}

	/**
	 * Check the scenario: when the there is no position in the portfolio, return a void LinkedList
	 */
	@Test
	public void testNewTrade4() {
		LinkedList<Position> actual = new LinkedList<Position>();
		
		portfolio.newTrade("Google", 200);
		portfolio.newTrade("IBM", 100);
		portfolio.newTrade("MSFT", -300);
		portfolio.newTrade("Google", -200);
		portfolio.newTrade("IBM", -100);
		portfolio.newTrade("MSFT", 300);
		
		PositionIter iter = portfolio.getPositionIter();
		Position pos = iter.getNextPosition();
		
		while (pos != null) {
			actual.add(pos);
			pos = iter.getNextPosition();
		}
		
		LinkedList<Position> expected = new LinkedList<Position>();
		
		Assert.assertEquals(expected, actual);	
	}
}
