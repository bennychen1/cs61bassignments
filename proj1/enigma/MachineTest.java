package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import static enigma.TestUtils.*;

public class MachineTest {
    private List rotorList = new ArrayList();
    private Machine M;
    Reflector B = (Reflector) getRotor("B", NAVALA, "", "reflector");
    FixedRotor Beta = (FixedRotor) getRotor("Beta", NAVALA, "", "fixed");
    MovingRotor I = (MovingRotor) getRotor("I", NAVALA, "A", "moving");

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

    private Machine createTestMachine() {
        rotorList.add(B);
        rotorList.add(Beta);
        rotorList.add(I);
        M = new Machine(UPPER, 3, 1, rotorList);
        return M;
    }

    @Test
    public void testInsertRotors() {
        M = createTestMachine();
        M.insertRotors(new String[]{"B", "Beta", "I"});
        assertArrayEquals(new Rotor[]{B, Beta, I}, M.getMachineRotors());
    }

    @Test
    public void testSetRotors() {
        M = createTestMachine();
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setRotors("QS");
        assertEquals('Q', UPPER.toChar(M.getMachineRotors()[1].setting()));
        assertEquals('S', UPPER.toChar(M.getMachineRotors()[2].setting()));
    }

    @Test
    public void testConvertChar() {
        M = createTestMachine();
        M.insertRotors(new String[]{"B", "Beta", "I"});
        M.setPlugboard(new Permutation("(AB) (HLZ)", UPPER));
        M.setRotors("BB");
        assertEquals(7, M.convert(1));
    }






}
