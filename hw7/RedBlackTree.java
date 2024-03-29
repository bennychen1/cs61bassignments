/**
 * Simple Red-Black tree implementation, where the keys are of type T.
 @ author
 */
public class RedBlackTree<T extends Comparable<T>> {

    /** Root of the tree. */
    private RBTreeNode<T> root;

    /**
     * Empty constructor.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Constructor that builds this from given BTree (2-3-4) tree.
     *
     * @param tree BTree (2-3-4 tree).
     */
    public RedBlackTree(BTree<T> tree) {
        BTree.Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /**
     * Builds a RedBlack tree that has isometry with given 2-3-4 tree rooted at
     * given node r, and returns the root node.
     *
     * @param r root of the 2-3-4 tree.
     * @return root of the Red-Black tree for given 2-3-4 tree.
     */
    RBTreeNode<T> buildRedBlackTree(BTree.Node<T> r) {
        if (r.getItemCount() == 1) {
            insert(r.getItemAt(0));
        } else if (r.getItemCount() == 2 || r.getItemCount() == 3) {
            insert(r.getItemAt(1));
        }

        for (int c = 0; c < r.getChildrenCount(); c += 1) {
           BTree.Node<T> bNode = r.getChildAt(c);
           buildRedBlackTree(bNode);
        }
        // YOUR CODE HERE
        return root;
    }

    /**
     * Rotates the (sub)tree rooted at given node to the right, and returns the
     * new root of the (sub)tree. If rotation is not possible somehow,
     * immediately return the input node.
     *
     * @param node root of the given (sub)tree.
     * @return new root of the (sub)tree.
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        if (node.left != null && node.left.left != null
                && isRed(node.left) && isRed(node.left.left)) {
            RBTreeNode leftChild = node.left;
            node.left = leftChild.right;
            leftChild.right = node;
            boolean isNodeBlack = node.isBlack;
            node.isBlack = true;
            leftChild.isBlack = isNodeBlack;
            return node;
        }
        return node;
    }

    /**
     * Rotates the (sub)tree rooted at given node to the left, and returns the
     * new root of the (sub)tree. If rotation is not possible somehow,
     * immediately return the input node.
     *
     * @param node root of the given (sub)tree.
     * @return new root of the (sub)tree.
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        if (node.right != null && node.left != null
                && !isRed(node.left) && isRed(node.right)) {
            RBTreeNode rightChild = node.right;
            node.right = rightChild.left;
            rightChild.left = node;
            boolean isNodeBlack = node.isBlack;
            node.isBlack = true;
            rightChild.isBlack = isNodeBlack;
            return node;
        }
        // YOUR CODE HERE
        return node;
    }

    /**
     * Flips the color of the node and its children. Assume that the node has
     * both left and right children.
     *
     * @param node tree node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Returns whether a given node is red. null nodes (children of leaf) are
     * automatically considered black.
     *
     * @param node node
     * @return node is red.
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Insert given item into this tree.
     *
     * @param item item
     */
    void insert(T item) {
        root = insert(root, item);

    }

    /**
     * Recursivelty insert item into this tree. returns the (new) root of the
     * subtree rooted at given node after insertion. node == null implies that
     * we are inserting a new node at the bottom.
     *
     * @param node node
     * @param item item
     * @return (new) root of the subtree rooted at given node.
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {

        // Insert (return) new red leaf node.
        if (node == null) {
            node = new RBTreeNode<T>(false,item, null, null);
            return node;
            // YOUR CODE HERE
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            insert(node.left, item);
            // YOUR CODE HERE

        } else {
            // YOUR CODE HERE
            insert(node.right, item);

        }

        // handle case C and "Right-leaning" situation.
        if (isRed(node.right) && !isRed(node.left)) {
            rotateLeft(node);
            // YOUR CODE HERE


        }

        // handle case B
        if (isRed(node.left) && isRed(node.left.left)) {
            rotateRight(node);
            // YOUR CODE HERE

        }

        // handle case A
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
            // YOUR CODE HERE

        }
        return node;
    }


    /**
     * RedBlack tree node.
     *
     * @param <T> type of item.
     */
    static class RBTreeNode<T> {

        /** Item. */
        protected final T item;

        /** True if the node is black. */
        protected boolean isBlack;

        /** Pointer to left child. */
        protected RBTreeNode<T> left;

        /** Pointer to right child. */
        protected RBTreeNode<T> right;

        /**
         * A node that is black iff BLACK, containing ITEM, with empty
         * children.
         */
        RBTreeNode(boolean black, T item) {
            this(black, item, null, null);
        }

        /**
         * Node that is black iff BLACK, contains ITEM, and has children
         * LEFT AND RIGHT.
         */
        RBTreeNode(boolean black, T item, RBTreeNode<T> left,
            RBTreeNode<T> right) {
            this.isBlack = black;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

}
