package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.util.Stack;

public class BoardTest {
    Board nB = new Board();
    @Test
    public void testInit() {
        nB.put(Piece.BLACK, 0, 0);
        nB.put(Piece.SPEAR, 7, 2);

        assertTrue(nB.get(3, 0).toString().equals( "W"));
        assertTrue(nB.get(0, 0).toString().equals("B"));
        assertTrue(nB.get(7, 2).toString().equals("S"));
        assertTrue(nB.getInitBoard()[0][0].toString().equals("-"));

        nB.init();

        assertTrue(nB.get(0, 0).toString().equals("-"));
    }

    @Test
    public void testPutAndGet() {
        nB.init();

        Square get1 = Square.sq(6, 9);

        assertEquals("B", nB.get(get1).toString());

        nB.put(Piece.WHITE, get1);

        assertEquals(nB.get(get1), nB.get(6, 9));

        assertEquals("W", nB.get(get1).toString());
    }

    @Test
    public void testOccupied() {
        nB.init();
        nB.put(Piece.SPEAR, 3, 9);
        assertEquals("S", nB.get(3, 9).toString());
    }

    @Test
    public void testUnBlockedMoveQueen() {
        nB.init();
        Square from = Square.sq(6, 0);
        Square to = Square.sq(5, 0);
        Square distance = Square.sq(6, 7);
        Square occupied = Square.sq(6, 9);
        Square invalDir = Square.sq(7, 2);

        assertTrue(nB.isUnblockedMove(from, to, null));
        assertTrue(nB.isUnblockedMove(from, distance, null));
        assertFalse(nB.isUnblockedMove(from, invalDir, null));

        nB.put(Piece.WHITE, 3, 0);

        Square to2 = Square.sq(2, 0);

        assertFalse(nB.isUnblockedMove(to, to2, null));
        assertFalse(nB.isUnblockedMove(from, occupied, null));
    }

    @Test
    public void testHorizUB() {
        nB.init();

        Square from = Square.sq(6, 0);
        Square left = Square.sq(5, 0);
        Square right = Square.sq(7, 0);
        Square between = Square.sq(0, 0);
        Square between2 = Square.sq(9, 0);

        nB.put(Piece.SPEAR, 8, 0);

        assertTrue(nB.isUnblockedMove(from, left, null));
        assertTrue(nB.isUnblockedMove(from, right, null));
        assertFalse(nB.isUnblockedMove(from, between, null));
        assertFalse(nB.isUnblockedMove(from, between2, null));
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

        nB.setTurn(Piece.BLACK);
        assertTrue(nB.isLegal(defaultSquare));

        nB.setTurn(Piece.WHITE);
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

        nB.setTurn(Piece.WHITE);

        assertFalse(nB.isLegal(from2, occupied));
        assertFalse(nB.isLegal(from2, illegalMove));
        assertFalse(nB.isLegal(from2, pieceBetween));
    }

    @Test
    public void testIsLegalMove() {
        nB.init();

        Square from = Square.sq(9, 6);
        Square to = Square.sq(8, 7);
        Square spear = Square.sq(1, 7);
        Move m = Move.mv(from, to, spear);

        Square spearInval = Square.sq(7, 9);
        Move mInval = Move.mv(from, to, spearInval);

        nB.setTurn(Piece.BLACK);
        assertTrue(nB.isLegal(m));

        assertFalse(nB.isLegal(mInval));
    }

    @Test
    public void testIsLegalMoveString() {
        String m = "g1-h1(g1)";
        //String m2 = "g1-g9(g10)";

        Move move1 = Move.mv(m);
        //Move move2 = Move.mv(m2);

        assertTrue(nB.isLegal(move1));
        //assertFalse(nB.isLegal(move2));
    }

    @Test
    public void testMakeMove() {
        nB.init();
        Square from = Square.sq(6, 0);
        Square to = Square.sq(8, 2);
        Square toInvalDir = Square.sq(9, 1);
        Square spearInvalDir = Square.sq(9, 0);
        Square spear = Square.sq(1, 9);

        Move move1 = Move.mv(from, to, spear);
        Move moveInvalDir = Move.mv(from, toInvalDir, spearInvalDir);

        nB.makeMove(move1);

        assertEquals("-", nB.get(from).toString());
        assertEquals("W", nB.get(to).toString());
        assertEquals("S", nB.get(spear).toString());
        assertEquals(1, nB.numMoves());

        nB.makeMove(moveInvalDir);

        assertEquals(1, nB.numMoves());
        assertEquals("-", nB.get(spearInvalDir).toString());



        Square fromInval = Square.sq(9, 3);
        Square toInval = Square.sq(7, 1);
        Square spearInval = Square.sq(7, 2);

        Move moveInval = Move.mv(fromInval, toInval, spearInval);

        nB.makeMove(moveInval);

        assertEquals("W", nB.get(fromInval).toString());
        assertEquals("-", nB.get(toInval).toString());
        assertEquals("-", nB.get(spearInval).toString());
        assertEquals(1, nB.numMoves());
        assertEquals("B", nB.turn().toString());

        Square from2 = Square.sq(0, 6);
        Square to2 = Square.sq(2, 6);
        Square spear2 = Square.sq(0, 6);

        Move move2 = Move.mv(from2, to2, spear2);

        assertEquals("B", nB.turn().toString());

        nB.makeMove(move2);

        assertEquals(2, nB.numMoves());
        assertEquals("W", nB.turn().toString());

        assertEquals("S", nB.get(from2).toString());

        Stack<Move> curMoves = nB.getMovesStack();

        assertTrue(curMoves.size() == 2);
        assertTrue(move2 == curMoves.pop());
    }

    @Test
    public void testMakeMoveString() {
        Move move1 = Move.mv("g1 h1 h3");
        nB.makeMove(move1);

        assertEquals(1, nB.numMoves());
        assertEquals("W", nB.get(7, 0).toString());
        assertEquals("-", nB.get(6, 0).toString());
        assertEquals("S", nB.get(7, 2).toString());

        Move move2 = Move.mv("a7-a6(a7)");
        nB.makeMove(move2);
        assertEquals("S", nB.get(0, 6).toString());

    }

    @Test
    public void testUndo() {
        nB.init();

        Move move1 = Move.mv("a4-c2(b1)");
        Move botMove1 = Move.mv("a7-a4(b3)");

        nB.makeMove(move1);
        nB.makeMove(botMove1);

       nB.undo();

       assertEquals(0, nB.numMoves());
       assertEquals("W", nB.turn().toString());

       checkWithInitBoard(nB.getBoard());
    }

    @Test
    public void testTwoMovesUndo() {
        nB.init();

        Move move1 = Move.mv("j4-h6(j8)");
        Move botMove1 = Move.mv("j7-j4(j7)");

        Move move2 = Move.mv("h6-h1(j3)");
        Move botMove2 = Move.mv("j4-j6(j4)");

        nB.makeMove(move1);
        nB.makeMove(botMove1);
        nB.makeMove(move2);
        nB.makeMove(botMove2);

        nB.undo();

        assertEquals(2, nB.numMoves());
        assertEquals("W", nB.turn().toString());
        assertEquals("B", nB.get(9, 3).toString());
        assertEquals("-", nB.get(9, 5).toString());
        assertEquals("-", nB.get(7, 0).toString());
        assertEquals("W", nB.get(7, 5).toString());
        assertEquals("-", nB.get(9, 2).toString());

        nB.undo();

        assertEquals(0, nB.numMoves());
        checkWithInitBoard(nB.getBoard());

        nB.undo();
        checkWithInitBoard(nB.getBoard());

    }

    @Test
    public void testReachableFrom() {
        nB.init();

        for (int i = 0; i < 100; i +=1) {
            Square s = Square.sq(i);
            nB.put(Piece.SPEAR, s);
        }

        nB.put(Piece.EMPTY, 9, 0);
        nB.put(Piece.EMPTY, 9, 1);
        nB.put(Piece.EMPTY, 9, 2);

        Iterator<Square> reachableIter = nB.reachableFrom(Square.sq(9, 0), null);

        Square first = reachableIter.next();
        Square second = reachableIter.next();

        assertEquals(Square.sq(9, 1), first);
        assertEquals(Square.sq(9,2), second);
    }

    @Test
    public void testReachableFromDirections() {
        nB.init();

        Square from = Square.sq(6, 5);

        int[][] directions = Square.getDIR();

        for (int[] d : directions) {
            int dx = 2 * d[0];
            int dy = 2 * d[1];
            Square s = Square.sq(from.col() + dx, from.row() + dy);

            nB.put(Piece.SPEAR, s);
        }

        assertEquals("S", nB.get(6, 7).toString());
        assertEquals("-", nB.get(7, 6).toString());

        Iterator<Square> reachbleIter = nB.reachableFrom(from, null);

        List<Square> rSquares = new ArrayList<Square>();

        while (reachbleIter.hasNext()) {
            rSquares.add(reachbleIter.next());
        }

        assertEquals(8, rSquares.size());
    }

    void checkWithInitBoard(Piece[][]b) {
        int i = 0;
        for (Piece[] p : b) {
            assertArrayEquals(p, nB.getInitBoard()[i]);
            i += 1;
        }
    }
}

// Put square already occupied (isLegal)
