package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Benny Chen
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        _M = readConfig();
        if (_input.hasNext("[^\\*]")) {
            throw error("Input must start with a setting line");
        }
        while (_input.hasNextLine()) {
            String curLine = _input.nextLine();
            if (!curLine.equals("")) {
                setUp(_M, curLine);
            } else {
                _output.print("\n");
                continue;
            }
            while (_input.hasNextLine()
                    && !_input.hasNext("\\*")) {
                String next = _input.nextLine();
                printMessageLine(next);
            }
            _tally = 0;
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = "";
            String alphaString = "^\\s*[A-Z]\\s*-\\s*[A-Z]";
            Matcher matAlphaPattern = createMatcher(alphaString, alpha);

            while (_config.hasNext() && !matAlphaPattern.matches()) {
                alpha += _config.next();
                matAlphaPattern = createMatcher(alphaString, alpha);
            }

            if (!matAlphaPattern.matches()) {
                throw error("Configuration file alphabet is incorrect");
            }

            String num = "";
            String numString = "[0-9]+\\s[0-9]+\\s";
            Matcher matNum = createMatcher(numString, num);

            while (_config.hasNext() && !matNum.matches()) {
                num = num + _config.next() + " ";
                matNum = createMatcher(numString, num);
            }

            if (!matNum.matches()) {
                throw error("Need to have rotors and pawl numbers");
            }

            Matcher rotorsAndPawls = createMatcher("[0-9]+\\s[0-9]+\\s", num);
            rotorsAndPawls.matches();
            String[] numArr = num.split("\\s");
            ArrayList<Rotor> allRotors = new ArrayList<Rotor>();
            _alphabet = new CharacterRange(alpha.charAt(0), alpha.charAt(2));

            while (_config.hasNext()) {
                Rotor r = readRotor();
                _allRotors.add(r);
                _allRotorNames.add(r.name());
            }

            return new Machine(_alphabet, Integer.parseInt(numArr[0]),
                    Integer.parseInt(numArr[1]), _allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name = _config.next();
            String typeNotch = _config.next();
            String perm = "";
            while (_config.hasNext("([(][A-Z]+[)]?)*")) {
                perm += _config.next();
            }

            Permutation p = new Permutation(perm, _alphabet);

            if (typeNotch.charAt(0) == 'M') {
                char[] notches = new char[typeNotch.length() - 1];
                System.arraycopy(typeNotch.toCharArray(), 1, notches, 0,
                        typeNotch.length() - 1);
                String notch = new String(notches);
                return new MovingRotor(name, p, notch);
            } else if (typeNotch.charAt(0) == 'N') {
                return new FixedRotor(name, p);
            } else {
                return new Reflector(name, p);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        settings = settings.replaceAll("\\s+\\*", "* ");
        String[] mSettings = settings.split("\\s+");

        for (int i = 1; i < 1 + M.numRotors(); i += 1) {
            int rotorIndex = _allRotorNames.indexOf(mSettings[i]);
            if (rotorIndex == -1) {
                throw error("bad rotor name");
            }

            Rotor curRotor = _allRotors.get(rotorIndex);

            if (i == 1) {
                if (!(curRotor instanceof Reflector)) {
                    throw error ("First rotor must be reflector");
                }
            }
        }
        String[] rotors = new String[M.numRotors()];
        if (mSettings.length > 2 + M.numRotors()) {
            String[] plugboard = new String[mSettings.length
                    - M.numRotors() - 2];

            System.arraycopy(mSettings, M.numRotors() + 2, plugboard,
                    0, plugboard.length);

            String pbPermString = "";
            for (String c : plugboard) {
                pbPermString += c;
            }
            Permutation pbPerm = new Permutation(pbPermString, _alphabet);
            M.setPlugboard(pbPerm);
        }

        try {
            System.arraycopy(mSettings, 1, rotors, 0, M.numRotors());
        } catch (ArrayIndexOutOfBoundsException excp) {
            throw error("Wrong number of rotors");
        }

        M.insertRotors(rotors);
        M.setRotors(mSettings[M.numRotors() + 1]);
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {

        String result = _M.convert(msg.toUpperCase());

        for (int i = 0; i < result.length(); i += 1) {
            if (_tally == 5) {
                _output.print(" ");
                _tally = 0;
            }
            _output.print(result.charAt(i));
            _tally += 1;
        }
        _output.print("\n");
        _tally = 0;
    }
    /** Returns a Matcher with the pattern from S matching the TARGET string. */
    private Matcher createMatcher(String s, String target) {
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(target);
        return m;
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** The machine to set up. */
    private Machine _M;

    /** Contains all rotors from _config. */
    private ArrayList<Rotor> _allRotors = new ArrayList<Rotor>();

    /** Contains the all rotor names from config as String. */
    private ArrayList<String> _allRotorNames = new ArrayList<String>();

    /** Count every five prints. */
    private int _tally;
}
