package enigma;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _machineRotors = new Rotor[numRotors];

        for(Iterator<Rotor> e = allRotors.iterator(); e.hasNext();) {
            Rotor cur = e.next();
            _allRotorNames.add(cur.name());
            _rotorsArr.add(cur);
        }

        _numPawls = pawls;
        _machineRotors = new Rotor[_numRotors];

        // FIXME
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors; // FIXME
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls; // FIXME
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (rotors.length != _numRotors) {
            throw error("Mismatch in expected number of rotors");
        }

        for (int i = 0; i < rotors.length; i += 1) {
            for (int j = 1; j < rotors.length; j += 1) {
                if (rotors[i].equals(rotors[j])) {
                    throw error("Can not have duplicate rotors");
                }
            }
        }

        int movingRotorCount = 0;

        for (int i = 0; i < numRotors(); i += 1) {
            int rotorIndex = _allRotorNames.indexOf(rotors[i]);
            _machineRotors[i] = _rotorsArr.get(rotorIndex);
            _machineRotors[i].set(0);

            if (_rotorsArr.get(rotorIndex) instanceof MovingRotor) {
                movingRotorCount += 1;
            }
        }

        if (!(_machineRotors[0] instanceof Reflector)) {
            throw error("First rotor must be the reflector");
        }

        if (movingRotorCount != numPawls()) {
            throw error("Number of pawls must match the number of moving rotors");
        }

        // FIXME
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw error("Incorrect number of rotors to set");
        }
        for (int i = 1; i < _machineRotors.length; i += 1) {
            _machineRotors[i].set(setting.charAt(i - 1));
        }
        // FIXME
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
        // FIXME
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing

     *  the machine. */
    int convert(int c) {

        machineAdvance();

        c = _plugboard.permute(c);

        for(int i = _numRotors -1; i > 0; i -= 1) {
            c = _machineRotors[i].convertForward(c);
        }

        for (int i = 0; i < _numRotors; i += 1) {
            c = _machineRotors[i].convertBackward(c);
        }
        return _plugboard.invert(c); // FIXME
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        msg = msg.replaceAll("\\s", "");
        msg = msg.replaceAll("[^A-Z]", "");
        char[] result = new char[msg.length()];

        for (int i = 0; i < msg.length(); i += 1) {
            char curChar = msg.charAt(i);
            int curInt = _alphabet.toInt(curChar);
            result[i] = _alphabet.toChar(convert(curInt));
        }
        return new String(result); // FIXME
    }

    /** Advances the rotors of the machine per each character. */
    int[] findRotation() {
        int[] toRotate = new int[_numRotors];
        int movingStart = _numRotors - _numPawls;

        for (int rIndex = movingStart; rIndex < _numRotors - 1; rIndex += 1) {

            MovingRotor curRotor = (MovingRotor) _machineRotors[rIndex];
            MovingRotor rRotor = (MovingRotor) _machineRotors[rIndex + 1];

            String curString = rotorSetting(curRotor);
            String rightString = rotorSetting(rRotor);

            if (rIndex == movingStart) {
                if (rRotor.getNotch().contains(rightString)) {
                    toRotate[rIndex] = 1;
                }
            } else if (curRotor.getNotch().contains(curString) ||
                    rRotor.getNotch().contains(rightString)) {
                toRotate[rIndex] = 1;
            }
        }

        toRotate[_numRotors - 1] = 1;
        return toRotate;
    }

    void machineAdvance() {
        int[] toRotate = findRotation();
        for (int i = 0; i < _numRotors; i += 1) {
            if (toRotate[i] == 1) {
                _machineRotors[i].advance();
            }
        }
    }

    String rotorSetting(Rotor r) {
        char rSetting = _alphabet.toChar(r.setting());
        String result = "";
        result += rSetting;
        return result;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.

    Rotor[] getMachineRotors() {
        return _machineRotors;
    }

    private int _numRotors;
    private int _numPawls;
    private ArrayList<String> _allRotorNames = new ArrayList<String>();
    private ArrayList<Rotor> _rotorsArr = new ArrayList<Rotor>();
    private Rotor[] _machineRotors;
    private Permutation _plugboard;

}
