package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import static enigma.TestUtils.*;
import java.util.Arrays;

public class MachineTest {
    private List<Rotor> rotorList = new ArrayList<Rotor>();
    private Machine _M;
    private Permutation _plugBoard = new Permutation("(AB) (EH) (OI)", UPPER);
    private Permutation _pB2 = new Permutation("(AB) (LH) (OI)", UPPER);
    private Reflector _B = (Reflector) getRotor("B", NAVALA, "", "reflector");
    private FixedRotor _beta = (FixedRotor) getRotor("Beta", NAVALA,
            "", "fixed");
    private MovingRotor _I = (MovingRotor) getRotor("I", NAVALA,
            "B", "moving");
    private MovingRotor _V = (MovingRotor) getRotor("V", NAVALA, "Q", "moving");
    private MovingRotor _ii = (MovingRotor) getRotor("II", NAVALA,
            "BC", "moving");

    private Rotor getRotor(String name, HashMap<String, String> rotors,
                          String notches, String rotorType) {
        Rotor r;
        if (rotorType.equals("reflector")) {
            r = new Reflector(name, new Permutation(rotors.get(name), UPPER));
        } else if (rotorType.equals("fixed")) {
            r = new FixedRotor(name, new Permutation(rotors.get(name), UPPER));
        } else {
            r = new MovingRotor(name, new Permutation(rotors.get(name), UPPER),
                    notches);
        }
        return r;
    }

    private Machine createTestMachine(int numRotors, int numPawls) {
        rotorList.add(_B);
        rotorList.add(_beta);
        rotorList.add(_I);
        rotorList.add(_V);
        rotorList.add(_ii);
        _M = new Machine(UPPER, numRotors, numPawls, rotorList);
        return _M;
    }

    private int getRotorSetting(Machine M, int i) {
        return M.getMachineRotors()[i].setting();
    }

    @Test
    public void testInsertRotors() {
        _M = createTestMachine(3, 1);
        _M.insertRotors(new String[]{"B", "beta", "I"});
        assertArrayEquals(new Rotor[]{_B, _beta, _I}, _M.getMachineRotors());
    }

    @Test(expected = EnigmaException.class)
    public void testInsertRotorsError() {
        _M = createTestMachine(2, 1);
        _M.insertRotors(new String[] {"B", "Beta"});
    }

    @Test(expected = EnigmaException.class)
    public void testWrongMovingRotors() {
        _M = createTestMachine(3, 2);
        _M.insertRotors(new String[]{"B", "Beta", "I", "V"});
    }

    @Test(expected = EnigmaException.class)
    public void testNotReflector() {
        _M = createTestMachine(3, 2);
        _M.insertRotors(new String[]{"Beta", "II", "I"});
    }

    @Test(expected = EnigmaException.class)
    public void testRepeatRotors() {
        _M = createTestMachine(3, 2);
        _M.insertRotors(new String[]{"B", "II", "II"});
    }

    @Test
    public void testSetRotors() {
        _M = createTestMachine(3, 1);
        _M.insertRotors(new String[]{"B", "Beta", "I"});
        _M.setRotors("QS");
        assertEquals('Q', UPPER.toChar(_M.getMachineRotors()[1].setting()));
        assertEquals('S', UPPER.toChar(_M.getMachineRotors()[2].setting()));
    }

    @Test(expected = EnigmaException.class)
    public void testSetRotorsError() {
        _M = createTestMachine(3, 1);
        _M.insertRotors(new String[]{"B", "Beta", "I"});
        _M.setRotors("Q");
    }

    @Test
    public void testConvertChar() {
        _M = createTestMachine(3, 1);
        _M.insertRotors(new String[]{"B", "Beta", "I"});
        _M.setPlugboard(_pB2);
        _M.setRotors("BB");
        assertEquals(1, _M.convert(7));
    }


    @Test
    public void testFindRotation() {
        _M = createTestMachine(4, 2);
        _M.insertRotors(new String[] {"B", "Beta", "I", "V"});
        _M.setRotors("EAQ");

        assertArrayEquals(new int[]{0, 0, 1, 1}, _M.findRotation());
    }

    @Test
    public void testMachineAdvance() {
        _M = createTestMachine(4, 2);
        _M.insertRotors(new String[]{"B", "Beta", "I", "V"});
        _M.setRotors("EAP");
        _M.machineAdvance();
        assertEquals(16, getRotorSetting(_M, 3));
        assertEquals(0, getRotorSetting(_M, 2));
        assertEquals(4, getRotorSetting(_M, 1));

        _M.machineAdvance();
        assertEquals(17, getRotorSetting(_M, 3));
        assertEquals(1, getRotorSetting(_M, 2));

        _M.machineAdvance();
        assertEquals(18, getRotorSetting(_M, 3));
        assertEquals(1, getRotorSetting(_M, 2));
    }

    @Test
    public void testMachineAdDouble() {
        _M = createTestMachine(5, 3);
        _M.insertRotors(new String[]{"B", "Beta", "I", "II", "V"});
        _M.setRotors("ESAP");

        _M.machineAdvance();
        _M.machineAdvance();

        assertEquals(17, getRotorSetting(_M, 4));
        assertEquals(1, getRotorSetting(_M, 3));

        _M.machineAdvance();

        assertEquals(19, getRotorSetting(_M, 2));
        assertEquals(2, getRotorSetting(_M, 3));
        assertEquals(18, getRotorSetting(_M, 4));

        _M.machineAdvance();
        assertEquals(20, getRotorSetting(_M, 2));
    }

    @Test
    public void testConvertMsg() {
        _M = createTestMachine(3, 1);
        _M.insertRotors(new String[]{"B", "Beta", "I"});
        _M.setPlugboard(_plugBoard);
        _M.setRotors("BA");
        assertEquals("HI", _M.convert("AZ"));

        _M.setRotors("BA");
        assertEquals("HI", _M.convert("   A Z  20 "));
    }

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1", new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2", new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3", new Permutation("(ABCD)", ac), "C");
        Rotor four = new MovingRotor("R4", new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        mach.setPlugboard(new Permutation("(AC)", ac));

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');

    }

    /**Returns a string of the settings of rotors
     * in MACHINEROTORS, changing the int setting
     * to a char according to ALPH. */
    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }


}
