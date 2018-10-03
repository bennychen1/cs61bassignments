package enigma;

import static enigma.EnigmaException.*;
import java.util.ArrayList;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = new char[_alphabet.size()];
        for (int i = 0; i < _cycles.length; i += 1) {
            _cycles[i] = alphabet.toChar(i);
        }
        cycles = cycles.replaceAll("[(]", "");
        cycles = cycles.replaceAll("[)]", " ");
        String[] splitCycles = cycles.split(" ");
        for (String c : splitCycles) {
            addCycle(c);
        }
        // FIXME
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        for (int i = 0; i < cycle.length(); i += 1) {
            int index = _alphabet.toInt(cycle.charAt(i));
            if (i == cycle.length() - 1) {
                _cycles[index] = cycle.charAt(0);
            } else {
                _cycles[index] = cycle.charAt(i + 1);
            }
        }

        // FIXME
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size(); // FIXME
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        return 0;  // FIXME
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        return 0;  // FIXME
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return 0;  // FIXME
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        return 0;  // FIXME
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        return true;  // FIXME
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** The cycles of this permutation. */
    private char[] _cycles;

    /** Returns the cycles of the permutation. */
    char[] get_cycles() {
        char[] result = new char[_cycles.length];
        System.arraycopy(_cycles, 0, result, 0, _cycles.length);
        return result;
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED


}
