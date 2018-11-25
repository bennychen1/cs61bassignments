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
        UndirectedGraph u = new UndirectedGraph();
        u.add();
        u.add();
        assertEquals(3, u.add());
        assertEquals(3, u.vertexSize());
        assertEquals(3, u.maxVertex());
        assertEquals(0, u.edgeSize());

        u.add(1, 2);
        assertEquals(2, u.edgeSize());
    }

    @Test
    public void testUDAdd() {
        UndirectedGraph u = new UndirectedGraph();
        u.add();
        u.add();
        u.add();
        u.add(1, 2);
        u.add(1, 3);
        assertEquals(4, u.edgeSize());
        assertEquals(2, u.inDegree(1));
        assertEquals(1, u.inDegree(2));

        assertTrue(u.contains(3));
        assertTrue(u.contains(1));

        assertFalse(u.contains(4));
        assertFalse(u.contains(0));

        assertTrue(u.contains(1, 2));
        assertTrue(u.contains(3, 1));
    }


    @Test
    public void testRemvoeEdge() {
        UndirectedGraph u = new UndirectedGraph();
        u.add();
        u.add();
        u.add();

        u.add(1, 2);
        u.add(2, 3);

        u.remove(1, 2);

        assertEquals(2, u.edgeSize());
        assertFalse(u.contains(1, 2));
        assertFalse(u.contains(2, 1));
    }

    @Test
    public void testRemove() {
        UndirectedGraph u = new UndirectedGraph();
        u.add();
        u.add();

        u.add(2, 1);

        u.add();
        u.add();
        u.add();

        u.add(2, 3);
        u.add(2, 4);
        u.add(1, 3);

        u.remove(2);

        assertEquals(4, u.vertexSize());
        assertEquals(5, u.maxVertex());
        assertEquals(2, u.edgeSize());
        assertFalse(u.contains(2));
        assertFalse(u.contains(2, 3));
        assertFalse(u.contains(3, 2));
        assertFalse(u.contains(2, 4));
    }

    @Test
    public void testRemoveVertices() {
        UndirectedGraph u = new UndirectedGraph();

        for (int i = 0; i < 7; i += 1) {
            u.add();
        }

        u.add(1, 2);
        u.add(2, 4);
        u.add(1, 3);
        u.add(3, 6);
        u.add(3, 5);

        u.remove(5);
        assertFalse(u.contains(5, 3));
        assertEquals(7, u.maxVertex());

        u.remove(7);
        assertEquals(8, u.edgeSize());

        u.remove(3, 1);
        assertEquals(6, u.edgeSize());
        assertTrue(u.contains(3, 6));
        assertTrue(u.contains(1));
    }

    @Test
    public void testVertices() {
        UndirectedGraph u = new UndirectedGraph();

        for (int i = 0; i < 7; i += 1) {
            u.add();
        }

        u.remove(5);

        int[] verticesArray = new int[6];

        int index = 0;
        for (int i : u.vertices()) {
            verticesArray[index] = i;
            index += 1;
        }

        assertArrayEquals(new int[]{1, 2, 3, 4, 6, 7}, verticesArray);
    }

    @Test
    public void testEdgesIteration() {
        UndirectedGraph u = new UndirectedGraph();
        for (int i = 0; i < 5; i += 1) {
            u.add();
        }

        u.add(1, 3);
        u.add(2, 5);
        u.add(4, 5);
        u.add(3, 5);

        u.remove(2, 5);


        int[][] uEdges = new int[3][2];
        int index = 0;
        for (int[]e : u.edges()) {
            uEdges[0] = e;
        }

        int[][] expectedEdges = new int[][]{new int[]{1, 3}, new int[]{4, 5},
                new int[]{3, 5}};

        assertArrayEquals(expectedEdges, uEdges);
    }

    @Test
    public void testEdgesChange() {
        UndirectedGraph u = new UndirectedGraph();

        for (int i = 0; i < 3; i += 1) {
            u.add();
        }

        u.add(1, 2);
        u.add(1, 3);
        u.add(2,3);

        int[][] uEdges = new int[2][2];
        int index = 0;
        for (int[]e : u.edges()) {
            if (index == 1) {
                u.remove(1, 3);
                index += 1;
                continue;
            }

            if (index == 2) {
                uEdges[1] = e;
            }
            uEdges[index] = e;
            index += 1;
        }

        int[][] expected = new int[][]{new int[]{1, 2}, new int[]{2, 3}};

        assertArrayEquals(expected, uEdges);


    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

}
