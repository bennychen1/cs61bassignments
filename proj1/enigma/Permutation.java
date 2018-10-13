package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Benny Chen
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
        _reverseCycles = new char[size()];
        for (int i = 0; i < _cycles.length; i += 1) {
            _cycles[i] = alphabet.toChar(i);
            _reverseCycles[i] = alphabet.toChar(i);
        }

        cycles = cycles.replaceAll("[ ]+", " ");

        try {
            if (!checkCycles(cycles)) {
                throw new EnigmaException("Cycles in wrong format");
            }
        } catch (EnigmaException e) {
            throw error("Cycles in wrong format");
        }

        cycles = cycles.replaceAll("[(]", "");
        cycles = cycles.replaceAll("[)]", " ");
        String[] splitCycles = cycles.split(" ");
        for (String c : splitCycles) {
            addCycle(c);
        }
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

        for (int j = cycle.length() - 1; j >= 0; j -= 1) {
            int index = _alphabet.toInt(cycle.charAt(j));
            if (j == 0) {
                _reverseCycles[index] = cycle.charAt(cycle.length() - 1);
            } else {
                _reverseCycles[index] = cycle.charAt(j - 1);
            }
        }
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
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        p = wrap(p);
        char permuted = _cycles[p];
        return _alphabet.toInt(permuted);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        c = wrap(c);
        char permuted = _reverseCycles[c];
        return _alphabet.toInt(permuted);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int index = _alphabet.toInt(p);
        return _cycles[index];
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        int index = _alphabet.toInt(c);
        return _reverseCycles[index];
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** The permutation. */
    private char[] _cycles;

    /** The inverse permutations. */
    private char[] _reverseCycles;

    /** Returns the cycles of the permutation as a char array. */
    char[] getCycles() {
        char[] result = new char[_cycles.length];
        System.arraycopy(_cycles, 0, result, 0, _cycles.length);
        return result;
    }

    /** Returns the reverse cycles of the machine as a char array. */
    char[] getRCycles() {
        char[] result = new char[_reverseCycles.length];
        System.arraycopy(_reverseCycles, 0, result, 0, _reverseCycles.length);
        return result;
    }

    /**Checks if the cycles C is in correct format
     * Returns false if not. */
    boolean checkCycles(String c) {
        if (c.equals("")) {
            return true;
        }
        String cNew = c.replaceAll("[ ]", "");
        cNew = cNew.replaceAll("[)]", ") ");
        String[]cArr = cNew.split("[ ]");
        for (String s : cArr) {
            if (!s.matches("[(][A-Z ]+[)]")) {
                return false;
            }
        }

        return true;
    }
}
