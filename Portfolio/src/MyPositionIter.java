import java.util.LinkedList;
import java.util.Iterator;

/**
 * Class used to perform iterator function of portfolio, use Adapter design pattern here
 * 
 * @author yixia
 *
 */

public class MyPositionIter implements PositionIter {

	IteratorAdapter adapter = new IteratorAdapter();
	LinkedList<MyPosition> l = new LinkedList<MyPosition>();
	Iterator<MyPosition> iter;
	
	/**
	 * Constructor used to pass parameter to MyPositionIter 
	 * @param LinkedList p
	 */
	public MyPositionIter (LinkedList<MyPosition> p) {
		this.l = p;
		iter = adapter.adapt(l);
	}
	
	/**
	 * Call iterator of LinkedList to perform iterator in portfolio using Adapter
	 * 
	 * @return iter.next and null if the portfolio has no more position
	 */
	public Position getNextPosition() {
		if (iter.hasNext() != false) {
			return iter.next();
		}
		
		else {
			return null;
		}
	}
}


