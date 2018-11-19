import java.util.LinkedList;
import java.util.Iterator;

/** A binary search tree with arbitrary Objects as keys.
 *  @author
 */
public class BST {
    /** Root of tree. */
    private BSTNode root;

    /** A BST containing the elements in the sorted list LIST. */
    public BST(LinkedList list) {
        root = linkedListToTree(list.iterator(), list.size());
    }

    /**
     * Build a BST node of size N from a sorted linkedList, with ITER iterating
     * over th list.
     */
    private BSTNode linkedListToTree(Iterator iter, int n) {

        if (n == 0) {
            return null;
        }

        if (n == 1) {
            BSTNode b = new BSTNode();
            b.item = iter.next();
            return b;
        }

        BSTNode b = new BSTNode();
        LinkedList left = new LinkedList();
        LinkedList right = new LinkedList();

       for (int i = 0; i < (n/2); i += 1) {
           left.add(iter.next());
       }

       b.item = iter.next();

       while (iter.hasNext()) {
           right.add(iter.next());
       }

       b.left = linkedListToTree(left.iterator(), left.size());
       b.right = linkedListToTree(right.iterator(), right.size());
       return b;


        // YOUR CODE HERE
    }

    /**
     * Prints the tree to the console.
     */
    private void print() {
        print(root, 0);
    }

    /** Print NODE and its subtrees, indented D levels.  */
    private void print(BSTNode node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    public Object getItem() {
        return root.item;
    }


    /**
     * Node for BST.
     */
    static class BSTNode {

        /** Item. */
        protected Object item;

        /** Left child. */
        protected BSTNode left;

        /** Right child. */
        protected BSTNode right;
    }

    public static void main(String[] args) {
        LinkedList<Integer> nums = new LinkedList<Integer>();
        nums.add(3);
        nums.add(6);
        nums.add(10);
        nums.add(11);


        Iterator<Integer> iter = nums.iterator();

        BST b = new BST(nums);

        b.print();
    }
}
