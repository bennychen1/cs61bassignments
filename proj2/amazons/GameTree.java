package amazons;
import java.util.ArrayList;
import java.lang.Math.*;

public class GameTree {

    GameTree() {
        _root = null;
    }

    GameTree(Node n) {
        _root = n;
    }

    void addNode(Node n) {
        if (_root == null) {
            _root = n;
        } else {
            _root._childs.add(n);
        }
    }

    class Node {
        public Node(Board b, boolean isMax) {
            _b = b;
            _score = 0;
            _alpha = Integer.MIN_VALUE;
            _beta = Integer.MAX_VALUE;
            _childs = new ArrayList<Node>();
            _maximizer = isMax;
        }

        void addNode(Node n) {
            this._childs.add(n);
        }

        /**
         * The alpha value of this node.
         */

        /** The score of the current node. */
        private int _score;

        private int _alpha;

        /**
         * The beta value of this node.
         */
        private int _beta;

        /**
         * A list of this node's child nodes.
         */
        private ArrayList<Node> _childs;

        /**
         * True iff the node is a maximizer, else node
         * is a minimizer.
         */
        private boolean _maximizer;

        /** The board represented by this node. */
        private Board _b;
    }

    /** The root of the game tree. */
    private Node _root;
}
