import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.
        assertEquals(0, 0); */
        assertEquals(0, CompoundInterest.numYears(2018));
        assertEquals(-1, CompoundInterest.numYears(2017));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(0, CompoundInterest.futureValue(0, 100, 2050), tolerance);
        assertEquals(9.41, CompoundInterest.futureValue(10, -3, 2020), tolerance);
        assertEquals(100, CompoundInterest.futureValue(100, 100, 2018), tolerance);
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2020), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.80, CompoundInterest.futureValueReal(10, 12, 2020, 3), tolerance);
        assertEquals(12.544, CompoundInterest.futureValueReal(10, 12, 2020, 0), tolerance);
        assertEquals(11.255, CompoundInterest.futureValueReal(10, 3, 2020, -3), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2020, 10), tolerance);
        assertEquals(14554.50, CompoundInterest.totalSavings(5000, 2020, -3), tolerance);
        assertEquals(5000, CompoundInterest.totalSavings(5000, 2018, 10), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(16395.67, CompoundInterest.totalSavingsReal(5000, 2020, 3, -3), tolerance);
        assertEquals(15571.90, CompoundInterest.totalSavingsReal(5000, 2020, 10, 3), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
