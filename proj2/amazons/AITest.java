package amazons;

import org.junit.Test;
import static org.junit.Assert.*;


public class AITest {
    @Test
    public void testFindMove() {
        Board b = new Board();

        for (int i = 1; i < 100; i += 1) {
            Square s = Square.sq(i);
            if (b.get(s) == Piece.EMPTY) {
                b.put(Piece.SPEAR, s);
            }

        }

        b.put(Piece.EMPTY, 9, 5);
        b.put(Piece.EMPTY, 9, 4);
        b.put(Piece.EMPTY, 8, 7);

        AI aiOne = new AI();

        Controller c = new Controller(null, null, null,
                aiOne, aiOne);

        AI testAI = new AI(Piece.BLACK, c);

        Move wMove = Move.mv("j4-j5(j4)");

        b.makeMove(wMove);

        String bMove = testAI.myMove();

        assertTrue(bMove.equals("j7-j6(j7)"));
        
    }
}
