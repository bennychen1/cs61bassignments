package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
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

        assertTrue(nB.get(3, 0).toString().equals("W"));
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
        assertFalse(nB.isUnblockedMove(from2, to2, asEmpty));
    }

    @Test
    public void testDiags() {
        nB.init();

        Square from = Square.sq(9, 6);
        Square to = Square.sq(8, 5);
        Square pieceBetween = Square.sq(6, 3);

        nB.put(Piece.WHITE, 7, 4);

        assertTrue(nB.isUnblockedMove(from, to, null));
        assertFalse(nB.isUnblockedMove(from, pieceBetween, null));
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

        assertFalse(nB.isLegal(from, to, spearBetween));
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
        String m2 = "g1-g9(g10)";

        Move move1 = Move.mv(m);
        Move move2 = Move.mv(m2);

        assertTrue(nB.isLegal(move1));
        assertFalse(nB.isLegal(move2));
    }

    @Test
    public void testMakeMove() {
        nB.init();
        Square from = Square.sq(6, 0);
        Square to = Square.sq(8, 2);
        Square spear = Square.sq(1, 9);

        Move move1 = Move.mv(from, to, spear);

        nB.makeMove(move1);

        assertEquals("-", nB.get(from).toString());
        assertEquals("W", nB.get(to).toString());
        assertEquals("S", nB.get(spear).toString());
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
    public void testMoveTen() {
        nB.init();
        Move m1 = Move.mv("a4-a2(a4)");
        Move m2 = Move.mv("g10-j10(h10)");

        nB.makeMove(m1);
        nB.makeMove(m2);

        assertEquals(Piece.BLACK, nB.get(9, 9));
        assertEquals(Piece.SPEAR, nB.get(7, 9));
        assertEquals(Piece.WHITE, nB.get(0, 1));
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

        nB.init();
        Move move3 = Move.mv("g1 g9 h10");
        nB.makeMove(move3);

        assertEquals(Piece.SPEAR, nB.get(7, 9));
        assertEquals(Piece.WHITE, nB.get(6, 8));
    }

    @Test
    public void testUndo() {
        nB.init();

        Move move1 = Move.mv("a4-c2(b1)");
        Move botMove1 = Move.mv("a7-a4(b3)");

        nB.makeMove(move1);
        nB.makeMove(botMove1);

        assertEquals(2, nB.numMoves());

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

        for (int i = 0; i < 100; i += 1) {
            Square s = Square.sq(i);
            nB.put(Piece.SPEAR, s);
        }

        nB.put(Piece.EMPTY, 9, 0);
        nB.put(Piece.EMPTY, 9, 1);
        nB.put(Piece.EMPTY, 9, 2);

        Iterator<Square> reachableIter = nB.reachableFrom(Square.sq(9, 0),
                null);

        Square first = reachableIter.next();
        Square second = reachableIter.next();

        assertEquals(Square.sq(9, 1), first);
        assertEquals(Square.sq(9, 2), second);
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

        addToList(rSquares, reachbleIter, 8);
    }

    @Test
    public void testRIteratorEdge() {
        nB.init();

        Square from = Square.sq(6, 0);
        Square from2 = Square.sq(9, 0);
        nB.put(Piece.WHITE, 3, 3);
        nB.put(Piece.BLACK, 8, 2);
        nB.put(Piece.SPEAR, 6, 3);

        Iterator<Square> rIter = nB.reachableFrom(from, null);

        List<Square> rSquares = new ArrayList<Square>();

        addToList(rSquares, rIter, 10);

        Iterator<Square> rIter2 = nB.reachableFrom(from2, from);

        rSquares.clear();

        addToList(rSquares, rIter2, 9);

        assertTrue(rSquares.contains(from));
    }

    @Test
    public void testReachableFromFull() {
        nB.init();

        Square from = Square.sq(1, 9);


        Iterator<Square> rIter = nB.reachableFrom(from, null);
        Iterator<Square> rIter2 = nB.reachableFrom(from, from);

        List<Square> rSquares = new ArrayList<Square>();
        List<Square> rSquares2 = new ArrayList<Square>();

        addToList(rSquares, rIter, 20);
        addToList(rSquares2, rIter2, 20);

        putPiece(Piece.EMPTY);

        List<Square> rSquares3 = new ArrayList<Square>();
        Iterator<Square> rIter3 = nB.reachableFrom(from, from);

        addToList(rSquares3, rIter3, 27);
    }

    @Test
    public void testRightCorner() {
        nB.init();
        Square corner = Square.sq(9, 0);
        Iterator<Square> rIter = nB.reachableFrom(corner, null);
        List<Square> rSquares = new ArrayList<Square>();
        addToList(rSquares, rIter, 13);
    }

    @Test
    public void testToString() {
        nB.init();
        String initBoard =
                "   - - - B - - B - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   B - - - - - - - - B\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   W - - - - - - - - W\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - W - - W - - -\n";
        assertEquals(initBoard, nB.toString());

        nB.put(Piece.SPEAR, 9, 0);
        nB.put(Piece.SPEAR, 9, 1);

        String boardSpear =
                "   - - - B - - B - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   B - - - - - - - - B\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - -\n"
                        + "   W - - - - - - - - W\n"
                        + "   - - - - - - - - - -\n"
                        + "   - - - - - - - - - S\n"
                        + "   - - - W - - W - - S\n";

        assertEquals(boardSpear, nB.toString());
    }

    @Test
    public void testLegalMovesIterator() {
        nB.init();

        putPiece(Piece.SPEAR);

        nB.put(Piece.WHITE, 0, 3);
        nB.put(Piece.EMPTY, 0, 2);
        nB.put(Piece.BLACK, 6, 9);

        Iterator<Move> legalMovesW = nB.legalMoves(Piece.WHITE);
        Iterator<Move> legalMovesB = nB.legalMoves(Piece.BLACK);

        Move oneMove = legalMovesW.next();
        Move expected = Move.mv("a4-a3(a4)");
        assertEquals(expected, oneMove);
        assertFalse(legalMovesB.hasNext());
    }

    @Test
    public void testManyLegalMoves() {
        nB.init();
        putPiece(Piece.BLACK);
        nB.put(Piece.WHITE, 0, 3);
        nB.put(Piece.WHITE, 6, 0);
        nB.put(Piece.WHITE, 9, 3);
        nB.put(Piece.EMPTY, 6, 1);
        nB.put(Piece.EMPTY, 5, 0);
        nB.put(Piece.EMPTY, 5, 1);
        nB.put(Piece.EMPTY, 7, 0);
        nB.put(Piece.EMPTY, 7, 1);

        Iterator<Move> legalMovesW = nB.legalMoves(Piece.WHITE);
        List<Move> allMoves = new ArrayList<Move>();
        addToList(allMoves, legalMovesW, 21);

        Move exampleMove = Move.mv("g1-g2(h1)");
        Move exampleMove2 = Move.mv("g1-h2(g1)");
        Move exampleMove3 = Move.mv("g1-f1(h1)");
        assertTrue(allMoves.contains(exampleMove));
        assertTrue(allMoves.contains(exampleMove2));
        assertTrue(allMoves.contains(exampleMove3));
    }

    @Test
    public void testLegalFirstMoves() {
        nB.init();
        Iterator<Move> legalMovesW = nB.legalMoves(Piece.WHITE);
        List<Move> allMovesW = new ArrayList<Move>();

        Iterator<Move> legalMovesB = nB.legalMoves(Piece.BLACK);
        List<Move> allMovesB = new ArrayList<Move>();

        while (legalMovesW.hasNext()) {
            allMovesW.add(legalMovesW.next());
        }

        while (legalMovesB.hasNext()) {
            allMovesB.add(legalMovesB.next());
        }

        assertEquals(2176, allMovesW.size());
        assertEquals(allMovesB.size(), allMovesW.size());
    }

    @Test
    public void testOneLegalMove() {
        nB.init();
        putPiece(Piece.SPEAR);
        nB.put(Piece.BLACK, 9, 6);
        nB.put(Piece.EMPTY, 9, 5);
        nB.put(Piece.EMPTY, 8, 7);
        nB.put(Piece.WHITE, 9, 4);

        Iterator<Move> allLegalMoves = nB.legalMoves(Piece.BLACK);
        ArrayList<Move> allMovesList = new ArrayList<Move>();

        while (allLegalMoves.hasNext()) {
            allMovesList.add(allLegalMoves.next());
        }
        assertEquals(2, allMovesList.size());
    }

    @Test
    public void testNumLegalMoves() {
        nB.init();
        assertEquals(2176, nB.numLegalMoves(Piece.WHITE));
    }


    /** Checks if the current board is the same as the initial board. */
    void checkWithInitBoard(Piece[][]b) {
        int i = 0;
        for (Piece[] p : b) {
            assertArrayEquals(p, nB.getInitBoard()[i]);
            i += 1;
        }
    }

    /** Adds all the squares in an iterator I to a list R.
     * Checks that r has size S after adding all the elements. */
    <T> void addToList(List<T> r, Iterator<T> i, int s) {
        while (i.hasNext()) {
            r.add(i.next());
        }
        assertEquals(s, r.size());
    }

    /** Put PIECE p into each square. */
    void putPiece(Piece p) {
        for (int i = 0; i < 100; i += 1) {
            Square s = Square.sq(i);
            nB.put(p, s);
        }
    }
}

