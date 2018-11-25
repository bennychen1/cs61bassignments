package graph;

import org.junit.Test;
import static org.junit.Assert.*;

public class DirectedGraphTest {

    @Test
    public void testAddEdge() {
        DirectedGraph d = createDGraph(5);
        d.add();

        d.add(1, 6);
        assertEquals(1, d.add(3, 2));
        d.add(3, 6);
        d.add(4, 5);
        d.add(5, 1);

        assertEquals(2, d.add(3, 6));
    }

    @Test
    public void testContainsEdge() {
        DirectedGraph d = createDGraph(5);

        d.add(2, 5);
        d.add(1, 5);
        d.add(2, 1);
        d.add(4, 5);
        d.add(3, 5);
        d.add(1, 1);

        assertTrue(d.contains(1, 5));
        assertFalse(d.contains(5, 1));
        assertTrue(d.contains(1, 1));
        assertTrue(d.contains(5));
    }

    @Test
    public void testRemoveVertex() {
        DirectedGraph d = createDGraph(6);

        d.add(5, 2);
        d.add(2, 3);
        d.add(4, 2);
        d.add(1, 2);
        d.add(6, 2);
        d.add(3, 6);

        d.remove(2);

        assertEquals(6, d.maxVertex());
        assertEquals(1, d.edgeSize());
        assertFalse(d.contains(5, 2));

        d.add();

        assertEquals(7, d.maxVertex());
        assertFalse(d.contains(2));

        assertEquals(1, d.add(5, 7));
    }

    private DirectedGraph createDGraph(int n) {
        DirectedGraph d = new DirectedGraph();
        for (int i = 0; i < n; i += 1) {
            d.add();
        }

        return d;
    }
}
