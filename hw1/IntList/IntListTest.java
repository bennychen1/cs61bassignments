import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6);
        IntList C = IntList.list();
        IntList D = IntList.list(1);
        IntList E = IntList.list(10);
        IntList A2 = IntList.list(1, 2, 3);
        IntList empty = IntList.list();
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6), IntList.dcatenate(A, B));
        assertEquals(IntList.list(4, 5, 6), IntList.dcatenate(C, B));
        assertEquals(IntList.list(), IntList.dcatenate(empty, empty));
        assertEquals(IntList.list(10, 4, 5, 6), IntList.dcatenate(E, B));
        assertEquals(IntList.list(1, 2, 3, 1), IntList.dcatenate(A2, D));

    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {
        IntList A = IntList.list(1, 2, 3, 4, 5, 6);
        IntList B = IntList.list(10, 11, 12);
        IntList empty = IntList.list();
        IntList C = IntList.list(10, 11, 12);
        IntList.subTail(C, 2);
        assertEquals(IntList.list(2, 3, 4, 5, 6), IntList.subTail(A, 1));
        assertEquals(IntList.list(12), IntList.subTail(B, 2));
        assertEquals(empty, IntList.subTail(empty, 10));
        assertEquals(IntList.list(10, 11, 12), C);

    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList A = IntList.list(1, 2, 3, 4, 5, 6, 7);
        IntList B = IntList.list(10, 20, 30, 40, 50);
        IntList C = IntList.list(1, 2, 3, 4, 5, 6, 7);
        IntList.sublist(B, 1, 2);
        assertEquals(IntList.list(2, 3, 4, 5, 6), IntList.sublist(A, 1, 5));
        assertEquals(IntList.list(10, 20, 30, 40, 50), B);
        assertEquals(IntList.list(2, 3, 4, 5, 6, 7), IntList.sublist(C, 1, 10));

    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {
        IntList A = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9);
        IntList B = IntList.list(20, 30, 40, 50, 60);
        assertEquals(IntList.list(2, 3, 4, 5, 6), IntList.dsublist(A, 1, 5));
        assertEquals(false, IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9) == A);
        assertEquals(IntList.list(50, 60), IntList.dsublist(B, 3, 10));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
