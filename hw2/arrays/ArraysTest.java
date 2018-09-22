package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Benny Chen
 */

public class ArraysTest {
    /** FIXME
     */
    @Test
    public void testCatenate() {
        int[] A = new int[] {1, 2, 3};
        int[]B = new int[] {4, 5, 6};
        int[] resultAB = new int[] {1, 2, 3, 4, 5, 6};
        int[] nullArray = new int[]{};

        assertArrayEquals(resultAB, Arrays.catenate(A, B));
        assertEquals(A, Arrays.catenate(nullArray, A));
    }

    @Test
    public void testRemove() {
        int[] A = new int[] {1, 2, 3, 4, 5, 6};
        int[] aCopy = new int[] {1, 2, 3, 4, 5, 6};
        int[] resultA = new int[] {1, 2, 6};
        int[] nullArray = new int[] {};
        assertArrayEquals(resultA, Arrays.remove(A, 2, 3));
        assertArrayEquals(aCopy, A);
        assertArrayEquals(A, Arrays.remove(A,5, 10));
        assertArrayEquals(aCopy, Arrays.remove(A, 2, 0));
    }

    @Test
    public void testNaturalRuns() {
        int[] A = new int[] {1, 3, 7, 5, 4, 6, 9, 10};
        int[] same = new int[]{1, 1, 1, 1, 1};
        int[][] result = new int[][]{{1, 3, 7}, {5}, {4, 6, 9, 10}};
        int[] zeroArray = new int[]{};
        assertArrayEquals(result, Arrays.naturalRuns(A));
        assertArrayEquals(new int[][]{{1}, {1}, {1}, {1}, {1}}, Arrays.naturalRuns(same));
        assertArrayEquals(new int[][]{{}}, Arrays.naturalRuns(zeroArray));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
