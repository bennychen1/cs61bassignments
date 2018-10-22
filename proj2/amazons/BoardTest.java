package amazons;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void testInit() {
        Board newBoard = new Board();
        newBoard.init();
        newBoard.put(Piece.BLACK, 0, 0);

        assertTrue(newBoard.get(3, 0).toString() == "B");
        assertTrue(newBoard.get(0, 0).toString() == "B");

        newBoard.init();

        assertTrue(newBoard.get(0, 0) == null);

    }
}

// Put square already occupied (isLegal)
