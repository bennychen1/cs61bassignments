import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author You
 */
class AlternatingFilter<Value> extends Filter<Value> {

    int _curIndex;

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input); //FIXME?
        // FIXME
    }

    @Override
    protected boolean keep() {
        if (_curIndex % 2 == 0) {
            _curIndex += 1;
            return true;
        } else {
            _curIndex += 1;
            return false;  // FIXME
        }
    }

    // FIXME

}
