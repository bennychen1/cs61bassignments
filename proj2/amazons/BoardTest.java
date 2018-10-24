package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BoardTest {
    Board nB = new Board();
    @Test
    public void testInit() {
        Board newBoard = new Board();
        newBoard.put(Piece.BLACK, 0, 0);
        newBoard.put(Piece.SPEAR, 7, 2);

        assertTrue(newBoard.get(3, 0).toString().equals( "W"));
        assertTrue(newBoard.get(0, 0).toString().equals("B"));
        assertTrue(newBoard.get(7, 2).toString().equals("S"));
        assertTrue(newBoard.getInitBoard()[0][0].toString().equals("-"));

        newBoard.init();

        assertTrue(newBoard.get(0, 0).toString().equals("-"));
    }

    @Test
    public void testOccupied() {
        nB.init();
        nB.put(Piece.SPEAR, 3, 9);
        assertEquals("B", nB.get(3, 9).toString());
    }

    @Test
    public void testUnBlockedMoveQueen() {
        nB.init();
        Square from = Square.sq(6, 0);
        Square to = Square.sq(5, 0);
        Square distance = Square.sq(6, 7);
        Square occupied = Square.sq(6, 9);

        assertTrue(nB.isUnblockedMove(from, to, null));
        assertTrue(nB.isUnblockedMove(from, distance, null));
        assertFalse(nB.isUnblockedMove(from, Square.sq(7, 2), null));

        nB.put(Piece.WHITE, 3, 0);

        Square to2 = Square.sq(2, 0);

        assertFalse(nB.isUnblockedMove(to, to2, null));
        assertFalse(nB.isUnblockedMove(from, occupied, null));
    }

    @Test
    public void testUnBlockedMoveSpear() {
        nB.init();
        Square from = Square.sq(9, 0);
        Square to = Square.sq(6, 0);
        Square asEmpty = to;

        assertTrue(nB.isUnblockedMove(from, to, asEmpty));

        Square from2 = Square.sq(7, 4);
        nB.put(Piece.SPEAR, 8, 5);
        String spear = nB.get(8, 5).toString();
        Square to2 = Square.sq(9, 6);
        Square to3 = Square.sq(9, 3);
        Square asEmpty2 = to2;

        assertFalse(nB.isUnblockedMove(from2, to2, asEmpty));
        assertFalse(nB.isUnblockedMove(from2,to2, asEmpty));
    }

    @Test
    public void testIsLegalStart() {
        nB.init();
        nB.put(Piece.WHITE, 2, 1);

        Square defaultSquare = Square.sq(9, 6);
        Square newPut = Square.sq(2, 1);
        Square emptySquare = Square.sq(2, 2);

        assertTrue(nB.isLegal(defaultSquare));
        assertTrue(nB.isLegal(newPut));
        assertFalse(nB.isLegal(emptySquare));
    }


    @Test
    public void testIsLegalFirst() {
        nB.init();
        Square defaultSquare = Square.sq(6, 0);
        Square occupied = Square.sq(6, 9);
        Square inval = Square.sq(7, 2);
        Square goodMove = Square.sq(6, 5);
        Square pieceBetween = Square.sq(5, 1);
        Square wPieceBetween = Square.sq(1, 5);
        Square emptySquare = Square.sq(0, 0);

        nB.put(Piece.WHITE, 5, 1);

        assertTrue(nB.isLegal(defaultSquare, goodMove));

        assertFalse(nB.isLegal(emptySquare, defaultSquare));
        assertFalse(nB.isLegal(defaultSquare, occupied));
        assertFalse(nB.isLegal(defaultSquare, inval));
        assertFalse(nB.isLegal(defaultSquare, wPieceBetween));
    }

    @Test
    public void testIsLegalSpear() {
        nB.init();

        Square from = Square.sq(6, 0);
        Square to = Square.sq(8, 2);
        Square spear = Square.sq(5, 2);
        Square spearFrom = Square.sq(6, 0);
        Square spearBetween = Square.sq(8, 4);
        Square spearOccupied = Square.sq(8, 3);
        Square spearInval = Square.sq(3, 3);

        nB.put(Piece.WHITE, 8, 3);

        assertTrue(nB.isLegal(from, to, spear));
        assertTrue(nB.isLegal(from, to, spearFrom));

        assertFalse(nB.isLegal(from, to,spearBetween));
        assertFalse(nB.isLegal(from, to, spearOccupied));
        assertFalse(nB.isLegal(from, to, spearInval));
    }

    @Test
    public void testIsLegals() {
        nB.init();

        Square start = Square.sq(2, 1);
        Square horiz = Square.sq(1, 1);
        Square backDiag = Square.sq(0, 1);
        Square forDiag = Square.sq(6, 5);

        nB.put(Piece.WHITE, 2, 1);

        assertTrue(nB.isLegal(start));
        assertTrue(nB.isLegal(start, horiz));
        assertTrue(nB.isLegal(start, backDiag));
        assertFalse(nB.isLegal(forDiag));

        nB.init();

        Square from2 = Square.sq(6, 9);
        Square occupied = Square.sq(6, 0);
        Square illegalMove = Square.sq(5, 2);
        Square pieceBetween = Square.sq(2, 5);

        nB.put(Piece.WHITE, 3, 6);

        assertFalse(nB.isLegal(from2, occupied));
        assertFalse(nB.isLegal(from2, illegalMove));
        assertFalse(nB.isLegal(from2, pieceBetween));
    }
}

// Put square already occupied (isLegal)
