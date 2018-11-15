package amazons;

import static java.lang.Math.*;

import static amazons.Piece.*;
import java.util.Iterator;
import java.util.ArrayList;

/** A Player that automatically generates moves.
 *  @author Benny Chen
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A magnitude representing a low number of moves. */
    private static final int SMALL = 20;

    /** A magnitude representing a large number of moves. */
    private static final int LARGE = 72;

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
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
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
            Iterator<Move> maxMovesIter;
            ArrayList<Move> maxMoves;
            board.setTurn(Piece.WHITE);
            maxMovesIter = board.legalMoves(Piece.WHITE);
            maxMoves = board.listOfMoves(maxMovesIter);
            for (Move m : maxMoves) {
                board.makeMove(m);
                bestVal = Math.max(bestVal, findMove(board, depth - 1, false,
                        -1, alpha, beta));
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
            board.setTurn(Piece.BLACK);
            Iterator<Move> minMovesIter = board.legalMoves(Piece.BLACK);
            ArrayList<Move> minMoves = board.listOfMoves(minMovesIter);
            for (Move m : minMoves) {
                board.makeMove(m);
                bestVal = Math.min(bestVal, findMove(board, depth - 1, false, 1,
                        alpha, beta));
                System.out.println(m.toString() + bestVal);
                board.undoAMove(m.from(), m.to(), m.spear());
                if (bestVal < alpha) {
                    if (saveMove) {
                        _lastFoundMove = m;
                    }
                    return bestVal;
                }
                if (saveMove) {
                    _lastFoundMove = m;
                }
                beta = Math.min(beta, bestVal);
            }

            return bestVal;
        }
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();

        if (N < SMALL) {
            return 1;
        } else if (N >= SMALL && N < LARGE) {
            return 2;
        } else {
            return 5;
        }
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == WHITE) {
            return WINNING_VALUE;
        } else if (winner == BLACK) {
            return -WINNING_VALUE;
        } else {
            if (board.numMoves() < LARGE) {
                int numWhite = 0;
                int numBlack = 0;
                for (int i = 0; i < 100; i += 1) {
                    if (board.get(Square.sq(i)) == WHITE) {
                        Iterator<Square> wR = board.reachableFrom(Square.sq(i),
                                null);
                        numWhite += board.iteratorNexts(wR);

                    } else if (board.get(Square.sq(i)) == BLACK) {
                        Iterator<Square> bR = board.reachableFrom(Square.sq(i),
                                null);
                        numBlack += board.iteratorNexts(bR);
                    }
                }
                return numBlack - numWhite;
            } else {
                int numMyMoves = board.numLegalMoves(WHITE);
                int numOpponentMoves = board.numLegalMoves(BLACK);
                return numMyMoves - numOpponentMoves;
            }
        }
    }


}
