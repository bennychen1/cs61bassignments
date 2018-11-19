import java.util.LinkedList;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

public class BSTTest {
    @Test
    public void testBST() {
        LinkedList<Integer> nums = new LinkedList<Integer>();
        nums.add(3);
        nums.add(6);
        nums.add(10);
        nums.add(11);


        Iterator<Integer> iter = nums.iterator();

        BST b = new BST(nums);
        assertEquals(10, b.getItem());

    }
}
