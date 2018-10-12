package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import static enigma.TestUtils.*;
import java.util.Arrays;

public class MachineTest {
    private List rotorList = new ArrayList();
    private Machine M;
    private Permutation plugBoard = new Permutation("(AB) (EH) (OI)", UPPER);
    Reflector B = (Reflector) getRotor("B", NAVALA, "", "reflector");
    FixedRotor Beta = (FixedRotor) getRotor("Beta", NAVALA, "", "fixed");
    MovingRotor I = (MovingRotor) getRotor("I", NAVALA, "B", "moving");
    MovingRotor V = (MovingRotor) getRotor("V", NAVALA, "Q", "moving");
    MovingRotor II = (MovingRotor) getRotor("II", NAVALA, "BC", "moving");

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
        rotorList.add(B);
        rotorList.add(Beta);
        rotorList.add(I);
        rotorList.add(V);
        rotorList.add(II);
        M = new Machine(UPPER, numRotors, numPawls, rotorList);
        return M;
    }

    private int getRotorSetting(Machine M, int i) {
        return M.getMachineRotors()[i].setting();
    }

    @Test
    public void testInsertRotors() {
        M = createTestMachine(3,1);
        M.insertRotors(new String[]{"B", "beta", "I"});
        assertArrayEquals(new Rotor[]{B, Beta, I}, M.getMachineRotors());
    }

    @Test(expected = EnigmaException.class)
    public void testInsertRotorsError() {
        M = createTestMachine(2, 1);
        M.insertRotors(new String[] {"B", "Beta"});
    }

    @Test(expected = EnigmaException.class)
    public void testWrongMovingRotors() {
        M = createTestMachine(3,2);
        M.insertRotors(new String[]{"B", "Beta", "I", "V"});
    }

    @Test(expected=EnigmaException.class)
    public void testNotReflector() {
        M = createTestMachine(3,2);
        M.insertRotors(new String[]{"Beta", "II", "I"});
    }

    @Test(expected = EnigmaException.class)
    public void testRepeatRotors() {
        M = createTestMachine(3,2);
        M.insertRotors(new String[]{"B", "II", "II"});
    }

    @Test
    public void testSetRotors() {
        M = createTestMachine(3,1);
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setRotors("QS");
        assertEquals('Q', UPPER.toChar(M.getMachineRotors()[1].setting()));
        assertEquals('S', UPPER.toChar(M.getMachineRotors()[2].setting()));
    }

    @Test(expected = EnigmaException.class)
    public void testSetRotorsError() {
        M = createTestMachine(3,1);
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setRotors("Q");
    }

    @Test
    public void testConvertChar() {
        M = createTestMachine(3,1);
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setPlugboard(plugBoard);
        M.setRotors("BB");
        assertEquals(7, M.convert(1));
    }

    @Test
    public void testRSetting() {
        M = createTestMachine(1,1);
        String result = M.rotorSetting(I);
        assertEquals("A", result);

    }

    @Test
    public void testFindRotation() {
        M = createTestMachine(4,2);
        M.insertRotors(new String[] {"B", "Beta", "I", "V"});
        M.setRotors("EAQ");

        assertArrayEquals(new int[]{0, 0, 1, 1}, M.findRotation());
    }

    @Test
    public void testMachineAdvance() {
        M = createTestMachine(4,2);
        M.insertRotors(new String[]{"B", "Beta", "I", "V"});
        M.setRotors("EAP");
        M.machineAdvance();
        assertEquals(16, getRotorSetting(M, 3));
        assertEquals(0, getRotorSetting(M, 2));
        assertEquals(4, getRotorSetting(M, 1));

        M.machineAdvance();
        assertEquals(17, getRotorSetting(M, 3));
        assertEquals(1, getRotorSetting(M, 2));

        M.machineAdvance();
        assertEquals(18, getRotorSetting(M, 3));
        assertEquals(1, getRotorSetting(M, 2));
    }

    @Test
    public void testMachineAdDouble() {
        M = createTestMachine(5,3);
        M.insertRotors(new String[]{"B", "Beta", "I", "II", "V"});
        M.setRotors("ESAP");

        M.machineAdvance();
        M.machineAdvance();

        assertEquals(17, getRotorSetting(M, 4));
        assertEquals(1, getRotorSetting(M, 3));

        M.machineAdvance();

        assertEquals(19, getRotorSetting(M, 2));
        assertEquals(2, getRotorSetting(M, 3));
        assertEquals(18, getRotorSetting(M, 4));

        M.machineAdvance();
        assertEquals(20, getRotorSetting(M, 2));
    }

    @Test
    public void testConvertMsg() {
        M = createTestMachine(3,1);
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setPlugboard(plugBoard);
        M.setRotors("BA");
        assertEquals("HI", M.convert("AZ"));

        M.setRotors("BA");
        assertEquals("HI", M.convert("   A Z  20 "));
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
        Machine mach = new Machine(ac, 4, 3, new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        mach.setPlugboard(new Permutation("(AC)", ac));

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');

    }

    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }


}
