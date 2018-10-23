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

    @Test
    public void testUnBlockedMoveQueen() {
        Board nB = new Board();
        Square from = Square.sq(0, 6);
        Square to = Square.sq(0, 5);

        assertTrue(nB.isUnblockedMove(to, from, null));
        assertFalse(nB.isUnblockedMove(to, Square.sq(7, 2), null));

        nB.put(Piece.WHITE, 0 , 3);

        Square to2 = Square.sq(0, 2);

        assertFalse(nB.isUnblockedMove(to, to2, null));
    }

    @Test
    public void testUnBlockedMoveSpear() {
        Board nB = new Board();
        Square from = Square.sq(0, 9);
        Square to = Square.sq(0, 6);
        Square asEmpty = to;

        assertTrue(nB.isUnblockedMove(from, to, asEmpty));

    }

    @Test
    public void testIsLegals() {
        Board nB = new Board();

        Square start = Square.sq(2, 1);
        Square horiz = Square.sq(1, 1);
        Square backDiag = Square.sq(0, 1);
        Square forDiag = Square.sq(6, 5);

        nB.put(Piece.WHITE, 2, 1);

        assertTrue(nB.isLegal(start));
        assertTrue(nB.isLegal(start, horiz));
        assertTrue(nB.isLegal(start, backDiag));
        assertTrue(nB.isLegal(forDiag));

        nB.init();

        Square from2 = Square.sq(6, 9);
        Square oOB = Square.sq(6, 10);
        Square occupied = Square.sq(6, 0);
        Square illegalMove = Square.sq(5, 2);
        Square pieceBetween = Square.sq(2, 5);

        assertFalse(nB.isLegal(from2, oOB));
        assertFalse(nB.isLegal(from2, occupied));
        assertFalse(nB.isLegal(from2, illegalMove));
        assertFalse(nB.isLegal(from2, pieceBetween));
    }
}

// Put square already occupied (isLegal)
