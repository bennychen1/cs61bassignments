import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of a BST based String Set.
 * @author
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public void put(String s) {
        root = putHelper(root, s);
        // FIXME
    }

    public Node putHelper(Node n, String s) {
       if (n == null) {
           n = new Node(s);
       } else if (n.s.equals(s)) {
       } else if (n.s.compareTo(s) < 0) {
           n.left = putHelper(n.left, s);
       } else {
           n.right = putHelper(n.right, s);
       }
       return n;
    }

    @Override
    public boolean contains(String s) {
        return containsHelper(root, s);
    }

    public boolean containsHelper(Node n, String s) {
        if (n == null) {
            return false;
        } else if (n.s.equals(s)) {
            return true;
        } else if (n.s.compareTo(s) < 0) {
            return containsHelper (n.left, s);
        } else {
            return containsHelper (n.right, s);
        }
    }

    @Override
    public List<String> asList() {
        List<String> result = new ArrayList<String>();
        if (root == null) {
            return result;
        }
        result.addAll(listHelper(root.left));
        result.add(root.s);
        result.addAll(listHelper(root.right));
        return result; // FIXME
    }

    public List<String> listHelper(Node n) {
        List<String> result = new ArrayList<String>();
        if (n == null) {
            return result;
        } else if (n.left == null) {
            result.add(n.s);
            result.addAll(listHelper(n.right));
        } else {
            result.addAll(listHelper(n.left));
            result.add(n.s);
            result.addAll(listHelper(n.right));
        }
        return result;
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
    private List<String> treeList;
}
