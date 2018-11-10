package amazons;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import java.util.Arrays;
import java.util.ArrayList;

import static amazons.Utils.*;

import static amazons.Piece.*;

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
        _moves = model._moves;
    }

    /** Clears the board to the initial position. */
    void init() {
        _turn = WHITE;
        _winner = null;
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
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        return _winner;
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
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        if (col < 0 || row < 0
                || col > 9 || row > 9) {
            return null;
        }
        return _boardArr[row][col];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        put(p, s.col(), s.row());
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        if (col < 0 || row < 0
                || col > 9 || row > 9) {
            return;
        }

        _boardArr[row][col] = p;
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

        for (int i = 0; i < 8; i += 1) {
            if (isUBDirection(from, to, asEmpty, i)) {
                return true;
            }
        }
        return false;
    }

    /** Returns true iff FROM - TO is an unblocked queen move in the
     * direction of DIRECTION(0 is North, 2 is Northeast, and so on
     * clockwise up to 7). The square of ASEMPTY is
     * treated as empty. */
    boolean isUBDirection(Square from, Square to, Square asEmpty,
                          int direction) {
        if (!(get(to).toString().equals("-")) && to != asEmpty) {
            return false;
        }

        if (from == to) {
            return true;
        }

        if (asEmpty == null) {
            asEmpty = from;
        }
        if (from != asEmpty && !(get(from)).toString().equals("-")) {
            return false;
        }

        Square moveTo = from.queenMove(direction, 1);
        if (moveTo != null) {
            return isUBDirection(moveTo, to, asEmpty, direction);
        }

        return false;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return get(from).toString().equals(_turn.toString());
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        if (isLegal(from)) {
            if (isUnblockedMove(from, to, null)) {
                return true;
            }
        }
        return false;
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
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        Square from = move.from();
        Square to = move.to();
        Square spear = move.spear();
        return isLegal(from, to, spear);
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

            Iterator<Move> checkWinO = legalMoves(_turn.opponent());
            if (!checkWinO.hasNext()) {
                _winner = _turn;
            }

            _turn = _turn.opponent();
        } else {
            System.out.println("Invalid move");
        }
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        Square from = move.from();
        Square to = move.to();
        Square spear = move.spear();
        makeMove(from, to, spear);
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

            Square from1 = move1.from();
            Square to1 = move1.to();
            Square spear1 = move1.spear();

            undoAMove(from1, to1, spear1);
        }
    }

    /** Undoes a move by returning the piece at TO back to
     *  FROM, puts an empty piece at TO, and nulls the SPEAR
     *  square if it does not equal FROM. Subtract number
     *  of moves by 1.*/
    void undoAMove(Square from, Square to, Square spear) {
        put(get(to), from);
        put(Piece.EMPTY, to);

        if (from != spear) {
            put(Piece.EMPTY, spear);
        }
        _numMoves -= 1;
        _turn = _turn.opponent();
        _winner = null;
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
            if (_dir < 8) {
                Square moveTo = _from.queenMove(_dir, _steps);
                if (moveTo != null) {
                    if (isUBDirection(_from, moveTo, _asEmpty, _dir)) {
                        return true;
                    } else {
                        toNext();
                        return hasNext();
                    }
                } else {
                    toNext();
                    return hasNext();
                }
            }
            return _dir < 8;
        }

        @Override
        public Square next() {
            if (hasNext()) {
                Square toReturn = _from.queenMove(_dir, _steps);
                _steps += 1;
                return toReturn;
            } else {
                throw error("No more reachable squares");
            }
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            _dir += 1;
            _steps = 1;
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
            if (_start == null && _nextSquare == null) {
                return false;
            }

            if (!_spearThrows.hasNext()) {
                toNextPiece();
                return hasNext();
            }
            return true;
        }

        @Override
        public Move next() {
            if (hasNext()) {
                Move m = Move.mv(_start, _nextSquare, _spearThrows.next());
                return m;
            } else {
                throw error("No legal moves");
            }
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if (_startingSquares.hasNext()) {
                _start = _startingSquares.next();
                if (get(_start) == _fromPiece) {
                    _pieceMoves = reachableFrom(_start, null);
                    if (_pieceMoves.hasNext()) {
                        _nextSquare = _pieceMoves.next();
                        _spearThrows = reachableFrom(_nextSquare, _start);
                    } else {
                        toNext();
                    }
                } else {
                    toNext();
                }
            } else {
                _start = null;
                _nextSquare = null;
            }
        }

        /** Advance only _nextSquare and set _spearThrows to be
         * an iterator of valid spear moves over the new _nextSquare.
         */
        private void toNextPiece() {
            if (_pieceMoves.hasNext()) {
                _nextSquare = _pieceMoves.next();
                _spearThrows = reachableFrom(_nextSquare, _start);
            } else {
                toNext();
            }
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

    /** Returns the number of legal moves that side P has. */
    public int numLegalMoves(Piece p) {
        Iterator<Move> legalMovesIter = legalMoves(p);
        int numMoves = 0;
        while (legalMovesIter.hasNext()) {
            legalMovesIter.next();
            numMoves += 1;
        }

        return numMoves;
    }

    /** Return an ArrayList of the legal moves
     * from legal moves iterator I. */
    public ArrayList<Move> listOfMoves(Iterator<Move> i) {
        ArrayList<Move> movesList = new ArrayList<Move>();
        while (i.hasNext()) {
            Move m = i.next();
            if (_numMoves < 3) {
                Square mTo = m.to();
                int c = mTo.col();
                int r = mTo.row();
                if (c < 4 || c > 7 || r < 5 || r > 7) {
                    continue;
                }
            }
            movesList.add(m);
        }
        return movesList;
    }

    /** Returns the number of times Iterator I of type T can call next. */
    public <T> int iteratorNexts(Iterator<T> i) {
        int total = 0;
        while (i.hasNext()) {
            i.next();
            total += 1;
        }

        return total;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 9; i >= 0; i -= 1) {
            Piece[] r = _boardArr[i];
            result += " " + " " + " ";
            for (int j = 0; j < r.length; j += 1) {
                String toAdd = r[j].toString();
                if (j == 9) {
                    result += toAdd + "\n";
                } else {
                    result += toAdd + " ";
                }
            }
        }
        return result;
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

    /** The eight possible directions of moving. */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Pattern for a move as a1 a2 a1. */
    static final String PAT1 = String.format("(%s)\\s+(%s)\\s+(%s)",
            Square.SQ, Square.SQ, Square.SQ);

    /** Pattern for a move as a1-a2(a1). */
    static final String PAT2 = String.format("%s-%s\\(%s\\)",
            Square.SQ, Square.SQ, Square.SQ);
}
