import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

/** Tests of range queries in BSTStringSet.
 *  @author P. N. Hilfinger
 */
public class BSTStringSetRangeTest {

    /** Initialize test data WORDS1 to N random, 4-character strings,
     *  and WORDS2 to the subset of WORDS within bounds _L and _U.
     *  Use SEED to seed the PRNG used to generate random strings. */
    private static void initWords(long seed, int N) {
        WORDS1.clear();
        for (int i = 0; i < N; i += 1) {
            WORDS1.add(StringUtils.randomString(4));
        }
        Collections.sort(WORDS1);
        String last;
        last = "";
        for (String s : WORDS1) {
            if (s.compareTo(_low) >= 0) {
                if (s.compareTo(_high) >= 0) {
                    break;
                }
                if (!last.equals(s)) {
                    WORDS2.add(s);
                }
            }
            last = s;
        }
        Collections.shuffle(WORDS1, new Random(seed));
    }

    @Test(timeout = 100)
    public void testGetRange() {
        ArrayList<String> found = new ArrayList<>();

        for (Iterator<String> it = _S.iterator(_low, _high);
             it.hasNext(); ) {
            found.add(it.next());
        }

        assertEquals(WORDS2, found);
    }

    /** ARGS = [ random seed, size of WORDS1, low bound, high bound ]. */
    public static void main(String[] args) {
        long seed = Long.parseLong(args[0]);
        int N = Integer.parseInt(args[1]);
        _low = args[2];
        _high = args[3];
        initWords(seed, N);
        for (String i : WORDS1) {
            _S.put(i);
        }
        System.exit(ucb.junit.textui.runClasses(BSTStringSetRangeTest.class));
    }

    @Test
    public void testBSTRange() {
        BSTStringSet b = new BSTStringSet();
        b.put("aardvark");
        b.put("bob");
        b.put("cat");
        b.put("dog");
        b.put("enter");
        b.put("free");
        b.put("good");
        b.put("ardvark");

        Iterator<String> bIterator = b.iterator("a", "focus");

        ArrayList<String> s= new ArrayList<String>();

        while (bIterator.hasNext()) {
            s.add(bIterator.next());
        }

        for (String x : s) {
            System.out.println(x);
        }
    }

    private static final ArrayList<String> WORDS1 = new ArrayList<>();
    private static final ArrayList<String> WORDS2 = new ArrayList<>();
    private static String _low, _high;
    private static BSTStringSet _S = new BSTStringSet();


}
