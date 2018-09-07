
public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     *
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     *
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     *
     * @return The number of elements in this list.
     */
    public int size() {
        if (_front == null && _back == null) {
            return 0;
        } else {
            DNode start = _front;
            int result = 0;
            while (start != null) {
                result += 1;
                start = start._next;
            }
            return result;
        }
    }

    /**
     *
     * @param i index of element to return, where i = 0 returns the first element,
     *          i = 1 returns the second element, i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *          for positive indices and -size <= i < 0 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        if (this.size() == 1) {
            return _front._val;
        }
        if(i < 0) {
            DNode start = _back;
            i = i + 1;
            while (i < 0) {
                start = start._prev;
                i = i + 1;
            }
            return start._val;
        } else {
            DNode start = _front;
            while (i > 0) {
                start = start._next;
                i = i - 1;// Your code here
            }
            return start._val;
        }
    }

    /**
     *
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        DNode curFront = _front;
        if (curFront == null) {
            _front = new DNode(null, d, null);
            _front._prev = _front;
            _back = _front;
        } else {
            _front = new DNode(null, d, curFront);
            curFront._prev = _front;
        }
        // Your code here 
    }

    /**
     *
      * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        _back = new DNode(_back, d, null);
        if (_front == null) {
            _front = _back;
            _back._prev = _front;
        } else {
            _back._prev._next = _back;
        }
        // Your code here 
    }

    /**
     * Removes the last item in the IntDList and returns it
     * @return the item that was deleted
     */
    public int deleteBack() {
        if (this.size() == 1) {
            DNode curBack = _back;
            _front = null;
            _back = null;
            return curBack._val;
        }
        DNode curBack = _back;
        _back = _back._prev;
        _back._next = null;
        return curBack._val;   // Your code here

    }

    /**
     *
     * @return a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc.
     *  Hint:
     *  String a = "a";
     *  a += "b";
     *  System.out.println(a); //prints ab
     */
    public String toString() {
        String result = "[";
        for (int i = 0; i < this.size(); i += 1) {
            if (i == this.size() - 1) {
                result += this.get(i);
            } else {
                result += this.get(i) + ", ";
            }
        }
        result += "]";
        System.out.println(result);
        return result;   // Your code here
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        protected DNode(int val) {
            this(null, val, null);
        }

        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
