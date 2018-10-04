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
        _cycles = new char[size()];
        __reverseCycles = new char[size()];
        for (int i = 0; i < _cycles.length; i += 1) {
            _cycles[i] = alphabet.toChar(i);
            __reverseCycles[i] = alphabet.toChar(i);
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

        for (int j = cycle.length() -1; j >= 0; j -= 1) {
            int index = _alphabet.toInt(cycle.charAt(j));
            if (j == 0) {
                __reverseCycles[index] = cycle.charAt(cycle.length() - 1);
            } else {
                __reverseCycles[index] = cycle.charAt(j - 1);
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
        p = wrap(p);
        char permuted = _cycles[p];
        return _alphabet.toInt(permuted);  // FIXME
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        c = wrap(c);
        char permuted = __reverseCycles[c];
        return _alphabet.toInt(permuted);  // FIXME
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int index = _alphabet.toInt(p);
        return _cycles[index];  // FIXME
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        int index = _alphabet.toInt(c);
        return __reverseCycles[index];  // FIXME
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

    /** The permutation. */
    private char[] _cycles;

    /** The inverse permutations. */
    private char[] __reverseCycles;

    /** Returns the cycles of the permutation. */
    char[] get_cycles() {
        char[] result = new char[_cycles.length];
        System.arraycopy(_cycles, 0, result, 0, _cycles.length);
        return result;
    }

    char[] getRCycles() {
        char[] result = new char[__reverseCycles.length];
        System.arraycopy(__reverseCycles, 0, result, 0, __reverseCycles.length);
        return result;
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED




}
