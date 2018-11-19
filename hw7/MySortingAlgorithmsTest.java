import org.junit.Test;
import static org.junit.Assert.*;

/** Correctness Tests for Sorting Algorithms.
 *  @author Josh Hug, Matthew Sit
 */

public class MySortingAlgorithmsTest {

    private SortingAlgorithm[] algorithms = {
        new MySortingAlgorithms.InsertionSort(),
        new MySortingAlgorithms.SelectionSort(),
        new MySortingAlgorithms.MergeSort(),
        // new MySortingAlgorithms.DistributionSort(),
        // new MySortingAlgorithms.HeapSort(),
        new MySortingAlgorithms.QuickSort(),
        new MySortingAlgorithms.LSDSort()};
        //new MySortingAlgorithms.MSDSort()};

    private SortingAlgorithm javaSort = new MySortingAlgorithms.JavaSort();


    /**
     * Checks the correctness of each sorting algorithm on an
     * already sorted array.
     */
    @Test
    public void alreadySortedCorrectnessTest() {
        // Test each algorithm one at a time, on the entire input array.
        for (SortingAlgorithm sa : algorithms) {

            int[] original = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] correct = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            javaSort.sort(correct, correct.length);

            int[] input = BenchmarkUtility.copy(original);
            sa.sort(input, input.length);
            assertArrayEquals("Result for " + sa + " incorrect",
                    correct, input);
        }
    }

    /**
     * Checks the correctness of each sorting algorithm
     * on a reverse-ordered array.
     */
    @Test
    public void reverseSortedCorrectnessTest() {
        // Test each algorithm one at a time, on the entire input array.
        for (SortingAlgorithm sa : algorithms) {

            int[] original = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
            int[] correct = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            javaSort.sort(correct, correct.length);

            int[] input = BenchmarkUtility.copy(original);
            sa.sort(input, input.length);
            assertArrayEquals("Result for " + sa + " incorrect",
                    correct, input);
        }
    }

    /**
     * Checks the correctness of each sorting algorithm on a simple,
     * rich example.
     */
    @Test
    public void simpleSortedCorrectnessTest() {
        // Test each algorithm one at a time, on the entire input array.
        for (SortingAlgorithm sa : algorithms) {

            int[] original = {0, 24, 13, 560, 2, 2, 1324, 5};
            int[] correct = {0, 2, 2, 13, 24, 560, 1324, 5};
            javaSort.sort(correct, correct.length);

            int[] input = BenchmarkUtility.copy(original);
            sa.sort(input, input.length);
            assertArrayEquals("Result for " + sa + " incorrect",
                    correct, input);

            // Test on the first k elements (in this case, all but the
            // last item).
            javaSort.sort(correct, correct.length - 1);

            int[] input2 = BenchmarkUtility.copy(original);
            sa.sort(input2, input2.length - 1);
            assertArrayEquals("Result for " + sa + " incorrect",
                    correct, input);
        }
    }

    /**
     * Checks the correctness of each sorting algorithm
     * by running 20 random input arrays on each.
     */
    @Test
    public void randomCorrectnessTest() {
        /* Don't set maxValue too high or Distribution Sort will use
           up all available memory and your program will crash. */
        int numInts = 50;
        int maxValue = 1000;

        // Test each algorithm one at a time, on the entire input array.
        for (SortingAlgorithm sa : algorithms) {

            // Test the algorithm for 1000 random inputs.
            for (int i = 0; i < 1000; i++) {
                int[] original = BenchmarkUtility.randomInts(numInts, maxValue);
                int[] correct = BenchmarkUtility.copy(original);
                javaSort.sort(correct, correct.length);

                int[] input = BenchmarkUtility.copy(original);
                sa.sort(input, input.length);
                assertArrayEquals("Result for " + sa + " incorrect",
                        correct, input);
            }

        }

        // Test each algorithm one at a time, on the first k elements only.
        int k = 20;

        for (SortingAlgorithm sa : algorithms) {

            // Test the algorithm for 1000 random inputs.
            for (int i = 0; i < 1000; i++) {
                int[] original = BenchmarkUtility.randomInts(numInts, maxValue);
                int[] correct = BenchmarkUtility.copy(original);
                javaSort.sort(correct, k);

                int[] input = BenchmarkUtility.copy(original);
                sa.sort(input, k);
                assertArrayEquals("Result for " + sa + " incorrect",
                        correct, input);
            }
        }
    }

    @Test
    public void testSortK() {
        int[] toSort = new int[]{5, 30, 25, 22, 29, 16, 11, 15};
        int[] toSortCopy = new int[]{5, 30, 25, 22, 29, 16, 11, 15};
        int[] sortK = new int[]{5, 22, 25, 29, 30, 16, 11, 15};
        algorithms[0].sort(toSort, 5);
        assertArrayEquals(sortK, toSort);

        System.arraycopy(toSortCopy, 0, toSort, 0, toSort.length);
        algorithms[1].sort(toSort, 5);
        assertArrayEquals(sortK, toSort);

        System.arraycopy(toSortCopy, 0, toSort, 0, toSort.length);
        algorithms[3].sort(toSort, 5);
        assertArrayEquals(sortK, toSort);

        System.arraycopy(toSortCopy, 0, toSort, 0, toSort.length);
        algorithms[2].sort(toSort, 5);
        assertArrayEquals(sortK, toSort);

        System.arraycopy(toSortCopy, 0, toSort, 0, toSort.length);
        algorithms[4].sort(toSort, 5);
        assertArrayEquals(sortK, toSort);





    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MySortingAlgorithmsTest.class));
    }
}
