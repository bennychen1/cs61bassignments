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
        UndirectedGraph u = createUDGraph(2);
        assertEquals(3, u.add());
        assertEquals(3, u.vertexSize());
        assertEquals(3, u.maxVertex());
        assertEquals(0, u.edgeSize());

        u.add(1, 2);
        assertEquals(2, u.edgeSize());
    }

    @Test
    public void testUDAdd() {
        UndirectedGraph u = createUDGraph(3);
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
        UndirectedGraph u = createUDGraph(3);

        u.add(1, 2);
        u.add(2, 3);

        u.remove(1, 2);

        assertEquals(2, u.edgeSize());
        assertFalse(u.contains(1, 2));
        assertFalse(u.contains(2, 1));
    }

    @Test
    public void testRemove() {
        UndirectedGraph u = createUDGraph(5);

        u.add(2, 1);
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
        UndirectedGraph u = createUDGraph(7);

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
        UndirectedGraph u = createUDGraph(7);

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
        UndirectedGraph u = createUDGraph(5);

        u.add(1, 3);
        u.add(2, 5);
        u.add(4, 5);
        u.add(3, 5);

        u.remove(2, 5);


        int[][] uEdges = new int[3][2];
        int index = 0;
        for (int[]e : u.edges()) {
            uEdges[index] = e;
            index += 1;
        }

        int[][] expectedEdges = new int[][]{new int[]{1, 3}, new int[]{4, 5},
                new int[]{3, 5}};

        assertArrayEquals(expectedEdges, uEdges);
    }

    @Test
    public void testEdgesChange() {
        UndirectedGraph u = createUDGraph(3);

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
                break;
            }
            uEdges[index] = e;
            index += 1;
        }

        int[][] expected = new int[][]{new int[]{1, 2}, new int[]{2, 3}};

        assertArrayEquals(expected, uEdges);
    }

    @Test
    public void testSuccessorsIteration() {
        UndirectedGraph u = createUDGraph(3);
        u.add(1, 2);
        u.add(3, 2);
        u.add(2, 2);

        int[] successorsArray = new int[3];
        int index = 0;

        for (int s : u.successors(2)) {
            successorsArray[index] = s;
            index += 1;
        }

        int[] expected = new int[]{1, 3, 2};

        assertArrayEquals(expected, successorsArray);
    }

    @Test
    public void testEdgeID() {
        UndirectedGraph u = createUDGraph(6);
        u.add(3, 2);
        u.add(5, 6);
        u.add(3, 5);
        u.add(1, 2);

        u.remove(3);

        assertEquals(0, u.edgeId(5, 6));

        u.remove(6,5);

        assertEquals(0, u.edgeId(1, 2));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNoVertex() {
        UndirectedGraph u = createUDGraph(5);
        u.add(1, 6);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRemovedVertex() {
        UndirectedGraph u = createUDGraph(5);
        u.remove(1);
        u.add(1, 3);
    }

    @Test
    public void testRemovedLargestMaxVertex() {
        UndirectedGraph u = createUDGraph(7);
        u.remove(7);
        assertEquals(6, u.maxVertex());
    }

    @Test
    public void testRemovedLargestMaxVertex2() {
        UndirectedGraph u = createUDGraph(5);
        u.remove(2);
        u.remove(4);
        u.remove(5);
        assertEquals(3, u.maxVertex());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCheckMyVertex() {
        UndirectedGraph u = createUDGraph(5);
        u.checkMyVertex(6);
    }


    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    private UndirectedGraph createUDGraph(int n) {
        UndirectedGraph u = new UndirectedGraph();
        for (int i = 0; i < n; i += 1) {
            u.add();
        }

        return u;
    }

}
