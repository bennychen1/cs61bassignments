package amazons;

// NOTICE:
// This file is a SUGGESTED skeleton.  NOTHING here or in any other source
// file is sacred.  If any of it confuses you, throw it out and do it your way.

import static java.lang.Math.*;

import static amazons.Piece.*;
import static amazons.Utils.iterable;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/** A Player that automatically generates moves.
 *  @author
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** The current depth of the search. */
    private static int _depth = 1;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        //if (_myPiece == _myPiece) {
        _depth = maxDepth(b);
        findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
       // } else {
            //findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        //}
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }

        int bestValue;

        _depth -= 1;

        if (sense == 1) {
            bestValue = Integer.MIN_VALUE;
            ArrayList<Move> myMoves = new ArrayList<Move>();
            Iterator<Move> legalMovesIter = board.legalMoves(_myPiece);

            while (legalMovesIter.hasNext()) {
                myMoves.add(legalMovesIter.next());
            }

            for (Move m : myMoves) {
                board.makeMove(m);
                bestValue = Math.max(bestValue, findMove(board, maxDepth(board), false, -1 * sense, alpha, beta));
                board.undoASingleMove();
                if (bestValue > beta) {
                    if (saveMove) {
                        _lastFoundMove = m;
                    }

                    return bestValue;
                }

                alpha = Math.max(alpha, bestValue);
                if (saveMove) {
                    _lastFoundMove = m;
                }
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            ArrayList<Move> minMoves = new ArrayList<Move>();
            Iterator<Move> minLegalMoves = board.legalMoves(_myPiece.opponent());

            while (minLegalMoves.hasNext()) {
                minMoves.add(minLegalMoves.next());
            }

            for (Move m : minMoves) {
                board.makeMove(m);
                bestValue = Math.min(bestValue, findMove(board, maxDepth(board), false, -1 * sense, alpha, beta));
                board.undoASingleMove();
                if (bestValue < alpha) {
                    if (saveMove) {
                        _lastFoundMove = m;
                    }

                    return bestValue;
                }

                beta = Math.min(beta, bestValue);
                if (saveMove) {
                    _lastFoundMove = m;
                }
            }
        }

        return bestValue;

        // FIXME
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();

       if (N == 2) {
           return 2;
       } else if (N == 10) {
           return 2;
       } else if (N == 20) {
           return 2;
       } else if (N == 30) {
           return 0;
       } else {
           return _depth;
       }

       // return 5 - N; // FIXME
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == _myPiece) {
            return WINNING_VALUE;
        } else if (winner == _myPiece.opponent()) {
            return -WINNING_VALUE;
        } else {
            Iterator<Move> myMoves = board.legalMoves(_myPiece);
            int numMyMoves = 0;
            while (myMoves.hasNext()) {
                myMoves.next();
                numMyMoves += 1;
            }

            Iterator<Move> oppMoves = board.legalMoves(_myPiece.opponent());
            int numOppMoves = 0;
            while (oppMoves.hasNext()) {
                oppMoves.next();
                numOppMoves += 1;
            }

            return numMyMoves - numOppMoves;
        }
        // FIXME
    }


}
