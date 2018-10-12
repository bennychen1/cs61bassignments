package enigma;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MachineTests2 {
    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'Z');
        Rotor one = new Reflector("B", new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP)" +
                " (RX) (SZ) (TV)", ac));
        Rotor two = new FixedRotor("Beta", new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", ac));
        Rotor three = new MovingRotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", ac), "Q");
        Rotor four = new MovingRotor("II", new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", ac), "E");
        Rotor five = new MovingRotor("III", new Permutation("((ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", ac), "V");
        String setting = "AAAA";
        Rotor[] machineRotors = {one, two, three, four, five};
        String[] rotors = {"B", "Beta", "I", "II", "III"};
        Machine mach = new Machine(ac, 5, 3, new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        //mach.setPlugboard(new Permutation("(AC)", ac));

        assertEquals("AAAAA", getSetting(ac, machineRotors));
        assertEquals("H",mach.convert("I"));

    }

    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }
}
