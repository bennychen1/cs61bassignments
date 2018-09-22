package image;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Benny Chen FIXME
 */

public class MatrixUtilsTest {
    @Test
    public void testAccumulateVertical() {
        double[][]e = new double[][]{{1000000, 1000000, 1000000, 1000000},
                               {1000000, 75990, 30003, 1000000},
                               {1000000, 30002, 103046, 1000000}};
        double[][]accumE = new double[][]{{1000000, 1000000, 1000000, 1000000},
                                    {2000000, 1075990, 1030003, 2000000},
                                    {2075990, 1060005, 1133049, 2030003}};
        assertArrayEquals(accumE, MatrixUtils.accumulateVertical(e));
    }
    /** FIXME
     */

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
