import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
public class IntervalsTest {
    int[][] intervals1 = new int[][]{{1, 10}, {11, 20}, {2, 5}};
    int[][] intervals2 = new int[][]{{1, 20}, {3, 7}, {9, 21}, {22, 30}, {51, 100}, {10, 13}};
    int[][] intervals3 = new int[][]{{1, 5}};


   @Test
   public void testTripleSize() {
       timer(intervals3);
       timer(intervals1);
       timer(intervals2);
   }

   void timer(int[][] a) {
       long start = System.currentTimeMillis();
       int length = Intervals.coveredLength(Arrays.asList(a));
       long finish = System.currentTimeMillis();
       long timeElapsed = finish - start;
       System.out.println(a.length + " intervals took: " + timeElapsed + "ms. Length covered is " + length);
   }
}
