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
 *  @author
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
            setUp(_M, _input.nextLine());
        }

        // FIXME
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = "";
            String alphaString = "^\\s*[A-Z]\\s*-\\s*[A-Z]";
            Matcher matAlphaPattern = createMatcher(alphaString, alpha);

            while (_config.hasNext() && !matAlphaPattern.matches()) {
                matAlphaPattern = createMatcher(alphaString, alpha += _config.next());
            }

            if (!matAlphaPattern.matches()) {
                throw error("Configuration file alphabet is incorrect");
            }

            String num = "";
            String numString = "[0-9]*\\s[0-9]*";
            Matcher matNum = createMatcher(numString, num);

            while (_config.hasNext() && !matNum.matches()) {
                matNum = createMatcher(numString, num = num + _config.next() + " ");
            }

            if (!matNum.matches()) {
                throw error ("Need to have rotors and pawl numbers");
            }

            Matcher rotorsAndPawls = createMatcher("[0-9]+", num);

            ArrayList<Rotor> allRotors = new ArrayList<Rotor>();

            while (_config.hasNext()) {
                Rotor r = readRotor();
                allRotors.add(r);
            }

            _alphabet = new CharacterRange(alpha.charAt(0), alpha.charAt(2));
            return new Machine(_alphabet, Integer.parseInt(rotorsAndPawls.group(0)),
                    Integer.parseInt(rotorsAndPawls.group(1)), allRotors);
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
            while (_config.hasNext("[(][A-Z]*[)]")) {
                perm += _config.next();
            }

            Permutation p = new Permutation(perm, _alphabet);

            if (typeNotch.charAt(0) == 'M') {
                char[] notches = new char[typeNotch.length() - 1];
                System.arraycopy(typeNotch.toCharArray(), 1, notches, 0,
                        typeNotch.length() - 1 );
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
        
        // FIXME
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        // FIXME
    }

    private String skipEmptyLines(Scanner s) {
        String emptyLine = "^\\s*$";
        Pattern p = Pattern.compile(emptyLine);
        String curLine = s.nextLine();
        Matcher mat = p.matcher(curLine);
        while(s.hasNextLine() && mat.matches()) {
            curLine = s.nextLine();
            mat = p.matcher(curLine);
        }
        return curLine;
    }

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

    private Machine _M;
}
