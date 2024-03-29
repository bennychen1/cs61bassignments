/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        return L.map(new Adder(n)); // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /** Return the sum of the elements in L. */
    static int sum(WeirdList L) {
        addSum added = new addSum(0);
        WeirdList res = L.map(added);
        return added._result;

       // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /* As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     */
    private static class Adder implements IntUnaryFunction {
        private int _addTo;
        public Adder(int addTo) {
            _addTo = addTo;
        }
        public int apply(int x){
            return this._addTo + x;
        }
    }

    private static class addSum implements IntUnaryFunction {
        private int _result;
        public addSum(int result) {
            _result = result;
        }
        public int apply(int x) {
            _result += x;
            return _result;
        }
    }

}
