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

    @Test
    public void testSquarePOSN() {
        assertEquals(Square.sq(9, 8), Square.sq("j9"));
        assertEquals(Square.sq(81), Square.sq("b9"));
        assertEquals(Square.sq(1), Square.sq("b1"));
    }

    @Test
    public void testQueenMove() {
        Square from = Square.sq(6, 0);
        Square to = Square.sq(8, 2);

        assertEquals(to, from.queenMove(1, 2));
        assertEquals(null, from.queenMove(10, 3));
        assertEquals(null, from.queenMove(0, 10));
    }
}
