package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Benny Chen
 */
class Arrays {
    /* C. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        if (A.length == 0) {
            return B;
        }

        if (B.length == 0) {
            return A;
        }
        int[] result = new int[A.length + B.length];
        System.arraycopy(A, 0, result, 0, A.length);
        System.arraycopy(B, 0, result, A.length, B.length);
        return result;
    }

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START.
     */
    static int[] remove(int[] A, int start, int len) {
        if (start == 0) {
            int index = start + len;
            int[] result = new int[]{};
            System.arraycopy(A, index, result, 0, A.length - len);
            return result;
        }else if (start + len > A.length) {
            return A;
        } else {
            int resultLength = A.length;
            if (A.length > len) {
                resultLength -= len;
            }
            int[] result = new int[resultLength];
            int resultIndex = 0;
            for(int i = 0; i < A.length; i += 1) {
                if(i < start || i > start + len - 1) {
                    result[resultIndex] = A[i];
                    resultIndex += 1;
                }
            }

            return result;
        }
    }

    /* E. */

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
       int index = 0;
       int numRuns = 0;
       while (index < A.length - 1) {
           if (A[index] >= A[index+1]) {
             numRuns += 1;
           }
           index += 1;
       }
       int[][]result = new int[numRuns + 1][];
       index = 0;
       int start = 0;
       for(int i=0; i < result.length; i += 1) {
           while (index < A.length - 1 && A[index] < A[index + 1]) {
               index += 1;
           }
           if (index == A.length -2 && A[index] < A[index+1]) {
               result[i] = Utils.subarray(A, start, index - start + 2);
           } else {
               result[i] = Utils.subarray(A, start, index - start + 1);
           }
           index += 1;
           start = index;
       }
       return result;
    }
}
