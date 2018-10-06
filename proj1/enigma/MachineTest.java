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

    @Test
    public void testInsertRotors() {
        Reflector B = (Reflector) getRotor("B", NAVALA, "", "reflector");
        FixedRotor Beta = (FixedRotor) getRotor("Beta", NAVALA, "", "fixed");
        MovingRotor I = (MovingRotor) getRotor("I", NAVALA, "A", "moving");
        rotorList.add(B);
        rotorList.add(Beta);
        rotorList.add(I);
        M = new Machine(UPPER, 3, 1, rotorList);
        M.insertRotors(new String[]{"B", "Beta", "I"});
        assertArrayEquals(new Rotor[]{B, Beta, I}, M.getMachineRotors());
    }






}
