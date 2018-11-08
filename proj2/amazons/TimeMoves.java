package amazons;

import javax.crypto.AEADBadTagException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimeMoves {
    static Board b = new Board();
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<Move> myMoves = new ArrayList<Move>();
        List<Move> opponentMoves = new ArrayList<Move>();

        Iterator<Move> myLegalMoves = b.legalMoves(Piece.WHITE);
        Iterator<Move> opponentLM = b.legalMoves(Piece.BLACK);

        while (myLegalMoves.hasNext()) {
            myMoves.add(myLegalMoves.next());
        }

        while (opponentLM.hasNext()) {
            opponentMoves.add(opponentLM.next());
        }

        if (myMoves.size() > opponentMoves.size()) {
            System.out.print("a");
        } else {
            System.out.println(opponentMoves.size());
        }
        long finishTime = System.currentTimeMillis();

        System.out.print(finishTime - startTime);
    }
}
