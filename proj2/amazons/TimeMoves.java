package amazons;

import java.util.ArrayList;
import java.util.Iterator;

public class TimeMoves {
    static Board b = new Board();
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        /**Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        Iterator<Move> legalMovesb = b.legalMoves(Piece.BLACK);
        ArrayList<Move> allMoves = new ArrayList<Move>();
        ArrayList<Move> allMovesB = new ArrayList<Move>();
        while (legalMoves.hasNext() && legalMovesb.hasNext()) {
            allMoves.add(legalMoves.next());
            allMovesB.add(legalMovesb.next());
        } */

        b.makeMove(Move.mv("g1-g2(g1)"));
        b.makeMove(Move.mv("g10-g9(g10)"));

        b.makeMove(Move.mv("g2-h2(g2)"));
        b.makeMove(Move.mv("g9-h9(g9)"));


        Iterator<Move> legalMoves2 = b.legalMoves(Piece.WHITE);
        Iterator<Move> legalMovesb2 = b.legalMoves(Piece.BLACK);
        ArrayList<Move> allMoves2 = new ArrayList<Move>();
        ArrayList<Move> allMovesB2 = new ArrayList<Move>();
        while (legalMoves2.hasNext() && legalMovesb2.hasNext()) {
            allMoves2.add(legalMoves2.next());
            allMovesB2.add(legalMovesb2.next());
        }

        long finishTime = System.currentTimeMillis();
        System.out.print(finishTime - startTime);
    }
}
