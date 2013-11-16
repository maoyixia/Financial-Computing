import java.util.LinkedList;
import java.util.Iterator;

/**
 * Class act as an Adapter 
 * 
 * @author yixia
 *
 */

public class IteratorAdapter {
	
	/**
	 * Adapt the iterator of LinkedList to PositionIter
	 * @param LinkedList l
	 * @return iterator of LinkedList
	 */
	public Iterator<MyPosition> adapt(LinkedList<MyPosition> l) {
		return l.iterator();
	}
}