package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BoardTest {
    @Test
    public void testInit() {
        Board newBoard = new Board();
        newBoard.put(Piece.BLACK, 0, 0);

        assertTrue(newBoard.get(3, 0).toString().equals( "W"));
        assertTrue(newBoard.get(0, 0).toString().equals("B"));
        assertTrue(newBoard.getInitBoard()[0][0].toString().equals("-"));

        newBoard.init();

        assertTrue(newBoard.get(0, 0).toString().equals("-"));
    }

    @Test
    public void testOccupied() {
        Board nB = new Board();
        nB.put(Piece.SPEAR, 3, 9);
        assertEquals("B", nB.get(3, 9).toString());
    }
}

// Put square already occupied (isLegal)
