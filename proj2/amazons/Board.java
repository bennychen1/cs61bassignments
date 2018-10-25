package amazons;

// NOTICE:
// This file is a SUGGESTED skeleton.  NOTHING here or in any other source
// file is sacred.  If any of it confuses you, throw it out and do it your way.

import java.util.Collections;
import java.util.Iterator;
import java.util.Formatter;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Arrays;

import static amazons.Utils.*;

import static amazons.Piece.*;
import static amazons.Move.mv;


/** The state of an Amazons Game.
 *  @author Benny Chen
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        _turn = model._turn;
        _winner = model._winner;
        _boardArr = model._boardArr;
        _initBoard = model._initBoard;
        _numMoves = model._numMoves;
        // FIXME
    }

    /** Clears the board to the initial position. */
    void init() {
        // FIXME
        _turn = WHITE;
        _winner = EMPTY;
        _initBoard = new Piece[SIZE][SIZE];
        _boardArr = new Piece[SIZE][SIZE];

        for (int i = 0; i < _initBoard.length; i += 1) {
            Arrays.fill(_initBoard[i], Piece.EMPTY);
        }

        _initBoard[0][3] = Piece.WHITE;
        _initBoard[0][6] = Piece.WHITE;
        _initBoard[3][0] = Piece.WHITE;
        _initBoard[3][9] = Piece.WHITE;

        _initBoard[6][0] = Piece.BLACK;
        _initBoard[6][9] = Piece.BLACK;
        _initBoard[9][3] = Piece.BLACK;
        _initBoard[9][6] = Piece.BLACK;

        for (int i = 0; i < _boardArr.length; i += 1) {
            System.arraycopy(_initBoard[i], 0,
                    _boardArr[i], 0, _boardArr[i].length);
        }

        _numMoves = 0;
        _moves = new Stack<Move>();
    }

    /** Returns the initial board. */
    Piece[][] getInitBoard() {
        return _initBoard;
    }

    /** Returns the current board array. */
    Piece[][] getBoard() {
        return _boardArr;
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Set the turn to the color of P for testing. */
    void setTurn(Piece p) {
        _turn = p;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _numMoves;
        // FIXME
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        return _winner;
        // FIXME
    }

    /**Returns the current moves stack. */
    Stack<Move> getMovesStack() {
        Stack<Move> moveCopy = new Stack<Move>();
        moveCopy.addAll(_moves);
        return moveCopy;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return get(s.col(), s.row());
        // FIXME
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW < 9. */
    final Piece get(int col, int row) {
        if (col < 0 || row < 0
                || col > 9 || row > 9) {
            return null;
        }
        return _boardArr[row][col];
        // FIXME
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        put(p, s.col(), s.row());
        // FIXME
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        if (col < 0 || row < 0
                || col > 9 || row > 9) {
            return;
        }

        _boardArr[row][col] = p;
        _winner = EMPTY; // FIXME
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {

        if (!(get(to).toString().equals("-")) && to != asEmpty) {
            return false;
        }
        if (asEmpty == null) {
            asEmpty = from;
        }
        String[] directions = new String[]{"horizLeft", "horizRight",
                "vertUp", "vertDown",
                                            "leftUpDiag", "leftDownDiag",
                                            "rightUpDiag", "rightDownDiag"};
        for (String direction : directions) {
            if (isUnblockedMoveDirection(from, to, asEmpty, direction)) {
                return true;
            }
        }
        return false;
    }

    /** Returns true iff FROM - TO is unblocked queen move in the direction
     * of DIRECTION. The square of ASEMPTY is treated as empty. */
    boolean isUnblockedMoveDirection(Square from, Square to, Square asEmpty,
                                     String direction) {
        if (from == to) {
            return true;
        }
        if (from != asEmpty && !(get(from)).toString().equals("-")) {
            return false;
        }
        if (from.col() < 0 || from.col() > 9 || from.row() < 0
                || from.row() > 9 || to.col() < 0 || to.col() > 9
                || to.row() < 0 || to.row() > 9) {
            return false;
        }
        if (direction.equals("horizLeft")) {
            return isUBHorizL(from, to, asEmpty, direction);
        } else if (direction.equals("horizRight")) {
            return isUBHorizR(from, to, asEmpty, direction);
        } else if (direction.equals("vertDown")) {
            return isUBVertD(from, to, asEmpty, direction);
        } else if (direction.equals("vertUp")) {
            if (from.row() == 9) {
                return false;
            }
            Square fromUp = Square.sq(from.col(), from.row() + 1);
            return isUnblockedMoveDirection(fromUp, to, asEmpty, direction);
        } else if (direction.equals("leftUpDiag")) {
            if (from.col() == 0 || from.row() == 9) {
                return false;
            }
            Square fromUpDiag = Square.sq(from.col() - 1, from.row() + 1);
            return isUnblockedMoveDirection(fromUpDiag, to, asEmpty, direction);
        } else if (direction.equals("leftDownDiag")) {
            if (from.col() == 9 || from.row() == 0) {
                return false;
            }
            Square fromDownDiag = Square.sq(from.col() + 1, from.row() - 1);
            return isUnblockedMoveDirection(fromDownDiag, to,
                    asEmpty, direction);
        } else if (direction.equals("rightDownDiag")) {
            if (from.col() == 0 || from.row() == 0) {
                return false;
            }
            Square fromDownDiag = Square.sq(from.col() - 1, from.row() - 1);
            return isUnblockedMoveDirection(fromDownDiag, to,
                    asEmpty, direction);
        } else {
            if (from.col() == 9 || from.row() == 9) {
                return false;
            }
            Square fromUpDiag = Square.sq(from.col() + 1, from.row() +  1);
            return isUnblockedMoveDirection(fromUpDiag, to,
                    asEmpty, direction);
        }
    }

    /**Return true iff FROM-TO is a valid first part of move
     * in the horizontal DIRECTION moving left. Take ASEMPTY to
     * be empty square. */
    boolean isUBHorizL(Square from, Square to, Square asEmpty,
                       String direction) {
        if (from.col() == 0) {
            return false;
        }
        Square fromLeft = Square.sq(from.col() - 1, from.row());
        return isUnblockedMoveDirection(fromLeft, to, asEmpty, direction);
    }

    /**Return true iff FROM-TO is a valid first part of move
     * in the horizontal DIRECTION moving right. Take ASEMPTY to
     * be empty square. */
    boolean isUBHorizR(Square from, Square to, Square asEmpty,
                       String direction) {
        if (from.col() == 9) {
            return false;
        }
        Square fromRight = Square.sq(from.col() + 1, from.row());
        return isUnblockedMoveDirection(fromRight, to, asEmpty, direction);
    }

    /**Return true iff FROM-TO is a valid first part of move
     * in the vertical DIRECTION moving down. Take ASEMPTY to
     * be empty square. */
    boolean isUBVertD(Square from, Square to, Square asEmpty,
                      String direction) {
        if (from.row() == 0) {
            return false;
        }
        Square fromDown = Square.sq(from.col(), from.row() - 1);
        return isUnblockedMoveDirection(fromDown, to, asEmpty, direction);
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return get(from).toString().equals(_turn.toString());
        // FIXME
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        if (isLegal(from)) {
            if (isUnblockedMove(from, to, null)) {
                return true;
            }
        }
        return false;  // FIXME
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (isLegal(from, to)) {
            if (isUnblockedMove(to, spear, from)) {
                return true;
            }
        }
        return false;
        // FIXME
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        Square from = move.from();
        Square to = move.to();
        Square spear = move.spear();
        return isLegal(from, to, spear);
        // FIXME
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {
        if (isLegal(from, to, spear)) {
            Move m = Move.mv(from, to, spear);
            _moves.push(m);
            Piece p = get(from);
            put(p, to);
            put(Piece.SPEAR, spear);
            if (spear != from) {
                put(Piece.EMPTY, from);
            }
            _numMoves += 1;
            _turn = _turn.opponent();
        }
        // FIXME
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        Square from = move.from();
        Square to = move.to();
        Square spear = move.spear();
        makeMove(from, to, spear);
        // FIXME
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if (numMoves() >= 2) {
            Move move2 = _moves.pop();
            Move move1 = _moves.pop();

            Square from2 = move2.from();
            Square to2 = move2.to();
            Square spear2 = move2.spear();

           undoAMove(from2, to2, spear2);

            Square from1= move1.from();
            Square to1 = move1.to();
            Square spear1 = move1.spear();

            undoAMove(from1, to1, spear1);
        }
        // FIXME
    }

    /** Undoes a move by returning the piece at TO back to
     *  FROM, puts an empty piece at TO, and nulls the spear
     *  square if it does not equal FROM. Subtract number
     *  of moves by 1.*/
    void undoAMove(Square from, Square to, Square spear) {
        put(get(to), from);
        put(Piece.EMPTY, to);

        if (from != spear) {
            put(Piece.EMPTY, spear);
        }
        _numMoves -= 1;
    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            return null;   // FIXME
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            // FIXME
        }

        /** Starting square. */
        private Square _from;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;
    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return false;  // FIXME
        }

        @Override
        public Move next() {
            return null;  // FIXME
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            // FIXME
        }

        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
    }

    @Override
    public String toString() {
        return ""; // FIXME
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;

    /** Represents the board squares. Keep track of occupied
     * squares. */
    private Piece[][] _boardArr;

    /** Sets up the initial positions and
     * stays at initial positions. */
    private Piece[][] _initBoard;

    /** Keeps count of the moves made. */
    private int _numMoves;

    /** Keeps track of the moves made. */
    private Stack<Move> _moves;
}
