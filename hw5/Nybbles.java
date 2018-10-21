/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        }
        int whichInt = _data[k / 8];
        int start = 31 - 4 * k - 3;
        int getInt = (whichInt >>> start) & ((1<<4) - 1);
        int leftMask = 1 << 3;
        String x = Integer.toBinaryString(whichInt);

        if ((getInt & leftMask) == 8) {
            String toReturn = Integer.toBinaryString((getInt << 28) >> 28);
            String toReturnComplement = Integer.toBinaryString(((getInt << 28) >> 28));
            return (getInt << 28) >> 28;
        }
        return getInt;
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < 4; i += 1) {
                int mask = 1 << i;
                int newDigit = val & mask;
                int alignLeft = newDigit << ((32 - (4 * (k + 1))));

                String m = Integer.toBinaryString(mask);
                String newDigit1 = Integer.toBinaryString(newDigit);
                String aLeft = Integer.toBinaryString(alignLeft);
                _data[k / 8] = (_data[k / 8] & ~(mask << (32 - (4 * (k + 1))))) | alignLeft;

                String dataK = Integer.toBinaryString(_data[k / 8]);
                String x = "x";
            }
        }
    }

    public int[] getData() {
        return _data;
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
