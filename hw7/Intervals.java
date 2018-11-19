import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/** HW #7, Sorting ranges.
 *  @author
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. */
    public static int coveredLength(List<int[]> intervals) {
        // REPLACE WITH APPROPRIATE STATEMENTS.
       ArrayList<Integer> r = new ArrayList<Integer>(intervals.size() * 2);
       int index = 0;
       for (int i = 0; i < intervals.size(); i += 1) {
           int[] curArray = intervals.get(i);
           r.add(curArray[0]);
           r.add(-1 * curArray[1]);
       }

       Collections.sort(r, new Comparator<Integer>() {
           @Override
           public int compare(Integer o1, Integer o2) {
               Integer x = Math.abs(o1);
               Integer y = Math.abs(o2);
               return x.compareTo(y);
           }
       });

       int length = 0;
       int start = 0;
       int counter = 0;

       for (int i = 0; i < r.size(); i += 1) {
           if (r.get(i) > 0) {
               counter += 1;
           } else {
               counter -= 1;
           }

           if (counter <= 0) {
               length += Math.abs(r.get(i)) - r.get(start);
               if (i < r.size() - 1) {
                   start = i + 1;
                   counter = 0;
               }
           }
       }

        return length;
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
