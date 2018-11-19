package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author
 */
public class GraphTest {

    // Add tests.  Here's a sample.
    @Test
    public void testUDGraph() {
        UndirectedGraph u = new UndirectedGraph(3);
        u.add(1, 2);
        assertEquals(1, u.inDegree(1));
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

}
