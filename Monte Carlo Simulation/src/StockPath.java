import java.util.List;
import org.joda.time.DateTime;

/**
 * @author yixia
 *
 */
public interface StockPath {
	public List<Pair<DateTime,Double>> getPrices();
}
