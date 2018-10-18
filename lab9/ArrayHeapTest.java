import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapTest {

    /** Basic test of adding, checking, and removing two elements from a heap */
    @Test
    public void simpleTest() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("Qir", 2);
        pq.insert("Kat", 1);
        assertEquals(2, pq.size());

        String first = pq.removeMin();
        assertEquals("Kat", first);
        assertEquals(1, pq.size());

        String second = pq.removeMin();
        assertEquals("Qir", second);
        assertEquals(0, pq.size());
    }

    @Test
    public void newTest() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("A", 10);
        pq.insert("B", 2);
        pq.insert("AB", 5);
        assertEquals("B", pq.peek().item());
        assertEquals(3, pq.size());

        pq.insert("BA", 50);
        pq.insert("ABB", 25);

        assertEquals("B", pq.removeMin());
        assertEquals("AB", pq.removeMin());
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArrayHeapTest.class));
    }
}
