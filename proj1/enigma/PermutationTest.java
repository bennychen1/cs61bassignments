package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;
    private Permutation p = makeAlphabetAndPerm("(BA)", 'a', 'c');
    private Permutation p2 = makeAlphabetAndPerm("   (B)  (CA)   ", 'a', 'c');
    private Permutation p3 = makeAlphabetAndPerm("(KN)(LPQ)", 'k', 'q');

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    private Permutation makeAlphabetAndPerm(String c, char from, char to) {
        CharacterRange pAlphabet = new CharacterRange(from, to);
        Permutation p1 = new Permutation(c, pAlphabet);
        return p1;
    }

    /* ***** TESTS ***** */


    @Test
    public void testAddCycles() {
        char[] expected = new char[]{'B', 'A', 'C'};
        assertArrayEquals(expected, p.getCycles());

        char[]expected2 = new char[]{'C', 'B', 'A'};
        assertArrayEquals(expected2, p2.getCycles());

        char[]expected3 = new char[]{'N', 'P', 'M', 'K', 'O', 'Q', 'L'};
        assertArrayEquals(expected3, p3.getCycles());

        char[] rexpected = new char[]{'N', 'Q', 'M', 'K', 'O', 'L', 'P'};
        assertArrayEquals(rexpected, p3.getRCycles());
    }

    @Test
    public void testPermuteInt() {
        Permutation pm = new Permutation("(AE)", new CharacterRange('a', 'g'));

        assertEquals(4, pm.permute(0));

        assertEquals(6, p3.permute(12));
        assertEquals(6, p3.permute(-9));
        assertEquals(2, p3.permute(2));
    }

    @Test
    public void testInvertInt() {
        assertEquals(1, p3.invert(-9));
    }

    @Test
    public void testPermuteChar() {
        assertEquals('Q', p3.permute('P'));
    }

    @Test
    public void testInvertChar() {
        assertEquals('L', p3.invert('P'));
    }


    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

}
