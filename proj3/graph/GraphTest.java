package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author
 */
public class GraphTest {

    // Add tests.  Here's a sample.
    @Test
    public void testUDSizes() {
        UndirectedGraph u = new UndirectedGraph(2);
        assertEquals(2, u.add());
        assertEquals(3, u.vertexSize());
        assertEquals(2, u.maxVertex());
        assertEquals(0, u.edgeSize());

        u.add(1, 2);
        assertEquals(1, u.edgeSize());
    }

    @Test
    public void testUDAdd() {
        UndirectedGraph u = new UndirectedGraph(3);
        u.add(1, 2);
        u.add(1, 3);
        assertEquals(2, u.edgeSize());
        assertEquals(2, u.inDegree(1));
        assertEquals(1, u.inDegree(2));
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

}
