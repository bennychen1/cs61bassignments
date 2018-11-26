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
        assertEquals(5, d.edgeSize());
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

    @Test
    public void testDirectedVertices() {
        DirectedGraph d = createDGraph(7);
        d.remove(7);
        d.add();

        d.remove(5);

        assertEquals(7, d.maxVertex());

        int[] verticesArray = new int[6];
        int index = 0;
        for (int v : d.vertices()) {
            verticesArray[index] = v;
            index += 1;
        }

        int[] expected = new int[]{1, 2, 3, 4, 6, 7};

        assertArrayEquals(expected, verticesArray);
    }

    @Test
    public void testInDegree() {
        DirectedGraph d = createDGraph(7);

        d.add(7, 5);
        d.add(6, 2);
        d.add(5, 2);
        d.add(1, 2);
        d.add(7, 4);
        d.add(3, 6);
        d.add(4, 2);
        d.add(7, 6);
        d.add(2, 3);

        assertEquals(4, d.inDegree(2));

        d.remove(4, 2);

        assertEquals(3, d.inDegree(2));

        d.remove(5);

        assertEquals(2, d.inDegree(2));

        d.add(2, 2);
        assertEquals(3, d.inDegree(2));

        d.remove(2);

        assertEquals(0, d.inDegree(2));
    }

    @Test
    public void testOutDegree() {
        DirectedGraph d = createDGraph(5);

        d.add(2, 3);
        d.add(2, 4);
        d.add(2, 5);
        d.add(1, 2);

        assertEquals(3, d.outDegree(2));

        d.remove(1, 2);
        d.remove(2, 4);

        assertEquals(2, d.outDegree(2));

        d.remove(5);

        assertEquals(1, d.outDegree(2));

        d.remove(3, 2);
        d.add(2, 2);
        assertEquals(2, d.outDegree(2));

        d.remove(2);
        assertEquals(0, d.outDegree(2));
    }

    @Test
    public void testSuccessors() {
        DirectedGraph d = createDGraph(7);

        d.add(2, 3);
        d.add(5, 7);
        d.add(5, 6);
        d.add(5, 2);
        d.add(2, 4);
        d.add(2, 1);

        int[] successors5 = new int[3];
        int index = 0;

        for (int s : d.successors(5)) {
            successors5[index] = s;
            index += 1;
        }

        int[] successors2 = new int[3];
        int index2 = 0;

        for (int s : d.successors(2)) {
            successors2[index2] = s;
            index2 += 1;
        }

        int[] expected5 = new int[]{7, 6, 2};
        int[] expected2 = new int[]{3, 4, 1};

        assertArrayEquals(expected5, successors5);
        assertArrayEquals(expected2, successors2);
    }



    private DirectedGraph createDGraph(int n) {
        DirectedGraph d = new DirectedGraph();
        for (int i = 0; i < n; i += 1) {
            d.add();
        }

        return d;
    }
}
