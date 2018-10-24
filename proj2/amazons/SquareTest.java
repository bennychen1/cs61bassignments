package amazons;
import org.junit.Test;
import static org.junit.Assert.*;

public class SquareTest {
    @Test
    public void testIndex(){
        Square ten = Square.sq(0, 1);
        assertEquals("a2", ten.toString());
    }

    @Test
    public void testGetByColRow() {
        Square six = Square.sq(6, 0);
        assertTrue(six == Square.sq(6));

        Square ninetyNine = Square.sq(9, 9);
        assertTrue(ninetyNine == Square.sq(99));
        assertEquals("j10", ninetyNine.toString());
    }
}
