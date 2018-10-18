import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
  * store Comparable objects. Instead, it can store any type of object
  * (represented by type T) and an associated priority value.
  * @author CS 61BL Staff*/
public class ArrayHeap<T> {

    /* DO NOT CHANGE THESE METHODS. */

    /* An ArrayList that stores the nodes in this binary heap. */
    private ArrayList<Node> contents;

    /* A constructor that initializes an empty ArrayHeap. */
    public ArrayHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the number of elments in the priority queue. */
    public int size() {
        return contents.size() - 1;
    }

    /* Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /* Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /* Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /* Prints out the heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* A Node class that stores items and their associated priorities. */
    public class Node {
        private T item;
        private double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T item() {
            return this.item;
        }

        public double priority() {
            return this.priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return this.item.toString() + ", " + this.priority;
        }
    }



    /* FILL IN THE METHODS BELOW. */

    /* Returns the index of the node to the left of the node at i. */
    private int getLeftOf(int i) {
        //YOUR CODE HERE
        return 2 * i;
    }

    /* Returns the index of the node to the right of the node at i. */
    private int getRightOf(int i) {
        //YOUR CODE HERE
        return (2 * i) + 1;
    }

    /* Returns the index of the node that is the parent of the node at i. */
    private int getParentOf(int i) {
        //YOUR CODE HERE
        return i / 2;
    }

    /* Adds the given node as a left child of the node at the given index. */
    private void setLeft(int index, Node n) {
        setNode(getLeftOf(index), n);
        //YOUR CODE HERE
    }

    /* Adds the given node as the right child of the node at the given index. */
    private void setRight(int index, Node n) {
        setNode(getRightOf(index), n);
        //YOUR CODE HERE
    }

    /** Returns the index of the node with smaller priority. Precondition: not
      * both nodes are null. */
    private int min(int index1, int index2) {
        //YOUR CODE HERE
        if (contents.indexOf(index1) < contents.indexOf(index2)) {
            return index1;
        } else {
            return index2;
        }
    }

    /* Returns the Node with the smallest priority value, but does not remove it
     * from the heap. */
    public Node peek() {
        //YOUR CODE HERE
        for (int i = 1; i < size(); i += 1) {
            bubbleDown(i);
        }
        return contents.get(1);
    }

    /* Bubbles up the node currently at the given index. */
    private void bubbleUp(int index) {
        Node curNode = getNode(index);
        int parIndex = getParentOf(index);
        Node par = getNode(parIndex);

        if (curNode == null || par == null) {
            return ;
        }

        if (curNode.priority() < par.priority()) {
            swap(index, getParentOf(index));
            bubbleUp(parIndex);
        }
        //YOUR CODE HERE
    }

    /* Bubbles down the node currently at the given index. */
    private void bubbleDown(int index) {
        Node curNode = getNode(index);
        Node leftNode = getNode(getLeftOf(index));
        Node rightNode = getNode(getRightOf(index));

        if (leftNode != null && rightNode != null
                && leftNode.priority < curNode.priority && rightNode.priority < curNode.priority) {
            if (leftNode.priority > rightNode.priority) {
                swap(getLeftOf(index), index);
            } else {
                swap(getRightOf(index), index);
            }
        } else if (leftNode != null && leftNode.priority() < curNode.priority()) {
            swap(index, getLeftOf(index));
            bubbleDown(getLeftOf(index));
        } else if (rightNode != null && rightNode.priority < curNode.priority) {
            swap(index, getRightOf(index));
            bubbleDown(getRightOf(index));
        }
    }

    /* Inserts an item with the given priority value. Same as enqueue, or offer. */
    public void insert(T item, double priority) {
        setNode(size() + 1, new Node(item, priority));
        bubbleUp(size());
        //YOUR CODE HERE
    }

    /* Returns the element with the smallest priority value, and removes it from
     * the heap. Same as dequeue, or poll. */
    public T removeMin() {
        int index = 1;
        for (; index < contents.size(); index += 1) {
            bubbleDown(index);
        }
        T toReturn = getNode(1).item();
        swap(index - 1, 1);
        removeNode(index - 1);

        for (int i = 1; i < contents.size(); i += 1) {
            bubbleDown(i);
        }

        return toReturn;
    }

    /* Changes the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check for item equality with .equals(), not == */
    public void changePriority(T item, double priority) {
        int foundIndex = 0;
        for (int i = 1; i < size(); i += 1) {
            Node curNode = getNode(i);
            if (curNode.item().equals(item)) {
                foundIndex = i;
                break;
            }
        }
        setNode(foundIndex, new Node(item, priority));

        for (int i = 1; i < size(); i += 1) {
            bubbleDown(i);
        }
        //YOUR CODE HERE
    }

}
