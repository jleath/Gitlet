package ngordnet;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/** An object for mapping a year number (e.g. 1996) to numerical data.
 *  Provides utility methods useful for data analysis.
 *  @author Joshua leath
 */

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {
    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super(); 
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        putAll(ts);
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super();
        SortedMap<Integer, T> withinRange = ts.subMap(startYear, endYear+1); 
        for (int i : withinRange.keySet()) {
            put(i, withinRange.get(i));
        }
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        TreeSet<Number> result = new TreeSet<Number>();
        for (T v : values()) {
            Number n = v;
            result.add(n);
        }
        return result;
    }

    /** Returns the quotient of this time series divided by the relevant value in ts. */
    public TimeSeries<Double> dividedBy(TimeSeries <? extends Number> ts) {
        Collection<Number> values = years();
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (Number n : values) {
            if (ts.containsKey(n)) {
                result.put(n.intValue(), get(n).doubleValue() / ts.get(n).doubleValue()); 
            } else {
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    /** Returns the sum of this time series with the given ts. */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (Number n : years()) {
            if (ts.containsKey(n)) { 
                result.put(n.intValue(), get(n).doubleValue() + ts.get(n).doubleValue());
            }
        }

        for (Number n : ts.years()) {
            if (!result.containsKey(n)) {
                result.put(n.intValue(), ts.get(n).doubleValue());
            }
        }
        return result;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        TreeSet<Number> result = new TreeSet<Number>();
        for (Integer i : keySet()) {
            Number n = i;
            result.add(n);
        }
        return result;
    }
}
