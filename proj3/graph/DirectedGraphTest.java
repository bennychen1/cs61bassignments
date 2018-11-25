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
    }

    private DirectedGraph createDGraph(int n) {
        DirectedGraph d = new DirectedGraph();
        for (int i = 0; i < n; i += 1) {
            d.add();
        }

        return d;
    }
}
