package amazons;

// NOTICE:
// This file is a SUGGESTED skeleton.  NOTHING here or in any other source
// file is sacred.  If any of it confuses you, throw it out and do it your way.

import static java.lang.Math.*;

import static amazons.Piece.*;
import static amazons.Utils.iterable;
import java.util.Iterator;
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
        findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        /**if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        } */
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

        if (sense == 1) {
            int bestVal = Integer.MIN_VALUE;
            Iterator<Move> maxMovesIter = board.legalMoves(_myPiece);
            ArrayList<Move> maxMoves = board.listOfMoves(maxMovesIter);
            for (Move m : maxMoves) {
                board.makeMove(m);
                bestVal = Math.max(bestVal, findMove(board, depth - 1, false,
                        -1 * sense, alpha, beta));
                board.undoAMove(m.from(), m.to(), m.spear());
                if (bestVal > beta) {
                    if (saveMove) {
                        _lastFoundMove = m;
                    }
                    return bestVal;
                }

                if (saveMove) {
                    _lastFoundMove = m;
                }

                alpha = Math.max(bestVal, alpha);
            }

            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;
            Iterator<Move> minMovesIter = board.legalMoves(_myPiece.opponent());
            ArrayList<Move> minMoves = board.listOfMoves(minMovesIter);
            for (Move m : minMoves) {
                board.makeMove(m);
                bestVal = Math.min(bestVal, findMove(board, depth - 1, false, -1 * sense, alpha, beta));
                if (bestVal < alpha) {
                    if (saveMove) {
                        _lastFoundMove = m;
                    }
                    return bestVal;
                }

                beta = Math.min(beta, bestVal);
            }

            return bestVal;
        }



        // FIXME
        //return 0;
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();

        if (N < 10) {
            return 2;
        } else if (N < 30) {
            return 3;
        } else {
            return 5;
        }

        //return 2; // FIXME
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == BLACK) {
            return -WINNING_VALUE;
        } else if (winner == WHITE) {
            return WINNING_VALUE;
        } else {
            int numMyMoves = board.numLegalMoves(_myPiece);
            int numOpponentMoves = board.numLegalMoves(_myPiece.opponent());
            return numMyMoves - 2 * numOpponentMoves;
        }

        // FIXME
        //return 0;
    }


}
