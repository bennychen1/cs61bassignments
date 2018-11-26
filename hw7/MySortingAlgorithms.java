import java.util.Arrays;

/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int[] sortedArr = new int[k];

            for (int i = 0; i < k; i += 1) {
                sortedArr[i] = Integer.MAX_VALUE;
            }

            for (int i = 0; i < k; i += 1) {
                sortedArr[k - 1] = array[i];
                int start = k - 1;
                int compareTo = start - 1;
                while (compareTo >= 0) {
                    if (sortedArr[compareTo] > array[i]) {
                        int temp = sortedArr[compareTo];
                        sortedArr[compareTo] = array[i];
                        sortedArr[start] = temp;
                        start -= 1;
                        compareTo -= 1;
                    } else {
                        break;
                    }
                }
            }
            System.arraycopy(sortedArr, 0, array, 0, k);
            // FIXME
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = k - 1; i > 0; i -= 1) {
                int maxIndex = i;
                for (int compareTo = i - 1; compareTo >= 0; compareTo -= 1) {
                    if (array[compareTo] > array[maxIndex]) {
                        maxIndex = compareTo;
                    }
                }
                int temp = array[i];
                array[i] = array[maxIndex];
                array[maxIndex] = temp;
            }
            // FIXME
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            if (array.length <= 1 || k <= 0) {
                return ;
            }

            int[] toSort = new int[k];
            System.arraycopy(array, 0, toSort, 0, k);

            int midPoint = toSort.length / 2;

            int[] left = new int[midPoint];
            int[] right = new int[toSort.length - midPoint];

            System.arraycopy(toSort, 0, left, 0, midPoint);
            System.arraycopy(toSort, midPoint, right, 0, right.length);

            sort(left, midPoint);
            sort(right, right.length);

            toSort = merge(left, right);

            System.arraycopy(toSort, 0, array, 0, k);
            // FIXME
        }

        // may want to add additional methods

        public int[] merge(int[] l, int[] r) {
            if (l == null && r == null) {
                return null;
            }

            if (l == null) {
                return r;
            }

            if (r == null) {
                return l;
            }

            int[] result = new int[l.length + r.length];

            int leftIndex = 0;
            int rightIndex = 0;
            int resultIndex = 0;

            while (leftIndex < l.length && rightIndex < r.length) {
                if (l[leftIndex] < r[rightIndex]) {
                    result[resultIndex] = l[leftIndex];
                    leftIndex += 1;
                } else {
                    result[resultIndex] = r[rightIndex];
                    rightIndex += 1;
                }

                resultIndex += 1;
            }

            if (leftIndex < l.length) {
                System.arraycopy(l, leftIndex, result, resultIndex, l.length - leftIndex);
            } else {
                System.arraycopy(r, rightIndex, result, resultIndex, r.length - rightIndex);
            }
            return result;
        }

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int[] toSort = new int[k];
            System.arraycopy(array, 0, toSort, 0, k);

            int largest = 0;
            int left = largest * 2;
            int right = (largest * 2) + 1;
            // FIXME
        }


        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {

            if (k <= 0) {
                return ;
            }

            if (array.length <= 1) {
                return ;
            }
            int[] toSort = new int[k];
            System.arraycopy(array, 0, toSort, 0, k);

            int pivot = toSort[0];
            int stopPoint = 1;

            for (int i = 1; i < toSort.length; i += 1) {
                if (toSort[i] < pivot) {
                    int temp = toSort[i];
                    toSort[i] = toSort[stopPoint];
                    toSort[stopPoint] = temp;
                    stopPoint += 1;
                }
            }

            int temp = toSort[0];
            toSort[0] = toSort[stopPoint - 1];
            toSort[stopPoint - 1] = temp;

            int[] left = new int[stopPoint - 1];
            int[] right = new int[toSort.length - stopPoint];

            System.arraycopy(toSort, 0, left, 0, left.length);
            System.arraycopy(toSort, stopPoint, right, 0, right.length);

            sort(left, left.length);
            sort(right, right.length);

            System.arraycopy(left, 0, toSort, 0, left.length);
            System.arraycopy(right, 0, toSort, stopPoint,  right.length);

            System.arraycopy(toSort, 0, array, 0, k);
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int[] toSort = new int[k];
            System.arraycopy(a, 0, toSort, 0, k);

            for (int i = 0; i < 32; i += 2) {
                int[] count = new int[4];
                int[] ordered = new int[k];
                int mask = 3 << i;
                String m = Integer.toBinaryString(mask);
                for (int j = 0; j < toSort.length; j += 1) {
                    int countIndex = (toSort[j] & mask) >>> i;
                    count[countIndex] += 1;
                }
                for (int index = 0; index < count.length; index += 1) {
                    if (index == 0) {
                        count[index] -= 1;
                    } else {
                        count[index] = count[index - 1] + count[index];
                    }
                }

                for (int s = ordered.length - 1; s >= 0; s -= 1) {
                    int toPut = toSort[s];
                    int putIndex = count[(toPut & mask) >>> i];
                    count[(toPut & mask) >>> i] -= 1;
                    ordered[putIndex] = toPut;
                }

                System.arraycopy(ordered, 0, toSort, 0, toSort.length);
            }

            System.arraycopy(toSort, 0, a, 0, k);
            // FIXME
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }
    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
