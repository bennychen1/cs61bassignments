package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {
    /** FIXME
     */
    @Test
    public void testNaturalRuns() {
        IntList L = IntList.list(1, 2, 3, 2, 7, 10, 11, 5);
        int[][] newL = new int[][]{
                {1, 2, 3},
                {2},
                {7, 10, 11},
                {5}
        };
        IntList sameL = IntList.list(1, 2, 3, 2, 7, 10, 11, 5);

        IntList same = IntList.list(1, 1, 1, 1, 1);
        int[][] newSame = new int[][]{
                {1}, {1}, {1}, {1}, {1}
        };

        IntList oneElement = new IntList(3, null);
        int[][] newOneElement = new int[][]{{3}};

        assertEquals(IntListList.list(newSame), Lists.naturalRuns(same));
        assertEquals(IntListList.list(newOneElement), Lists.naturalRuns(oneElement));
        assertEquals(IntListList.list(newL), Lists.naturalRuns(L));
        assertFalse(L.equals(sameL));
    }

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
