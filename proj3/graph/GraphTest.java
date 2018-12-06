package graph;

import org.junit.Test;

import java.awt.*;
import java.util.Collection;
import java.util.ArrayList;
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
        assertEquals(1, u.edgeSize());
    }

    @Test
    public void testUDAdd() {
        UndirectedGraph u = createUDGraph(3);
        u.add(1, 2);
        u.add(1, 3);
        assertEquals(2, u.edgeSize());
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
    public void testRemoveEdge() {
        UndirectedGraph u = createUDGraph(3);

        u.add(1, 2);
        u.add(2, 3);

        u.remove(1, 2);

        assertEquals(1, u.edgeSize());
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
        assertEquals(1, u.edgeSize());
        assertFalse(u.contains(2));
        assertFalse(u.contains(2, 3));
        assertFalse(u.contains(3, 2));
        assertFalse(u.contains(2, 4));
    }

    @Test
    public void testRemoveAndMax() {
        UndirectedGraph u = createUDGraph(10);
        DirectedGraph d = createDGraph(10);

        u.remove(10);
        d.remove(10);

        u.add();
        d.add();

        assertEquals(10, u.maxVertex());
        assertEquals(10, d.maxVertex());

        u.remove(2);
        d.remove(2);

        assertEquals(2, u.add());
        assertEquals(2, d.add());

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
        assertEquals(4, u.edgeSize());

        u.remove(3, 1);
        assertEquals(3, u.edgeSize());
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

        assertEquals(1, u.edgeId(5, 6));

        u.remove(6,5);

        assertEquals(1, u.edgeId(1, 2));
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

    @Test
    public void testAddEdge() {
        DirectedGraph d = createDGraph(5);
        d.add();

        d.add(1, 6);
        assertEquals(2, d.add(3, 2));
        d.add(3, 6);
        d.add(4, 5);
        d.add(5, 1);

        assertEquals(3, d.add(3, 6));
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

        assertEquals(6, d.maxVertex());
        assertTrue(d.contains(2));

        assertEquals(2, d.add(5, 6));
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

    @Test
    public void testPredecessors() {
        DirectedGraph d = createDGraph(6);

        d.add(6, 5);
        d.add(5, 4);
        d.add(2, 3);
        d.add(1, 3);
        d.add(3, 6);
        d.add(5, 3);

        int[] predecessors3 = new int[3];
        int index = 0;

        for (int i : d.predecessors(3)) {
            predecessors3[index] = i;
            index += 1;
        }

        int[] expected = new int[]{2, 1, 5};

        assertArrayEquals(expected, predecessors3);

        d.remove(1, 3);

        int[] predcessors3Remove = new int[2];
        int index3 = 0;

        for (int i : d.predecessors(3)) {
            predcessors3Remove[index3] = i;
            index3 += 1;
        }

        int[] expected3 = new int[]{2, 5};

        assertArrayEquals(expected3, predcessors3Remove);
    }

    @Test
    public void testPredecessors2() {
        DirectedGraph d = createDGraph(6);
        d.add(3, 3);
        d.add(1, 3);
        d.add(5, 4);
        d.add(3, 6);
        d.add(1, 3);
        d.add(2, 3);

        d.remove(1);

        int[] predecessors3 = new int[2];
        int index = 0;

        for (int p : d.predecessors(3)) {
            predecessors3[index] = p;
            index += 1;
        }

        int[] expected = new int[]{3, 2};

        assertArrayEquals(expected, predecessors3);
    }

    @Test
    public void testEdges() {
        DirectedGraph d = createDGraph(6);

        d.add(6, 5);
        d.add(1, 1);
        d.add(6, 5);
        d.add(1, 2);
        d.add(5, 2);
        d.add(5, 4);
        d.add(2, 3);
        d.add(1, 3);

        int[][] edgeArray = new int[7][2];
        int index = 0;

        for (int[]e : d.edges()) {
            edgeArray[index] = e;
            index += 1;
        }

        int[][] expected = new int[][]{{6, 5}, {1, 1}, {1, 2},
                {5, 2}, {5, 4}, {2, 3}, {1, 3}};

        assertArrayEquals(expected, edgeArray);
    }

    @Test
    public void testEdgesModified() {
        DirectedGraph d = createDGraph(5);

        d.add(3, 2);
        d.add(2, 3);
        d.add(1, 2);
        d.add(4, 5);
        d.add(2, 5);
        d.add(1, 5);
        d.add(5, 5);

        d.remove(4);
        d.remove(5);
        d.add();
        d.add(4, 1);
        d.remove(1, 2);

        int[][] edgesArray = new int[3][2];
        int index = 0;

        for (int[]e : d.edges()) {
            edgesArray[index] = e;
            index += 1;
        }

        int[][] expected = new int[][]{{3, 2}, {2, 3}, {4, 1}};
    }


    @Test
    public void testDirectedBFT() {
        DirectedGraph d = createDGraph(6);

        d.add(1, 6);
        d.add(3, 5);
        d.add(3, 4);
        d.add(3, 2);
        d.add(1, 3);


        BFTTest bft = new BFTTest(d);
        bft.traverse(1);
        int[] expectedOrder = new int[] {1, 6, 3, 5, 4, 2};

        assertArrayEquals(expectedOrder, bft._visitOrder);
    }

    @Test
    public void testCycleDirectedBFT() {
        DirectedGraph d = createDGraph(5);

        d.add(1, 2);
        d.add(3, 1);
        d.add(2, 3);
        d.add(5, 4);
        d.add(1, 5);

        BFTTest bft = new BFTTest(d);
        bft.traverse(1);
        int[] expectedOrder = new int[]{1, 2, 5, 3, 4};

        assertArrayEquals(expectedOrder, bft._visitOrder);
    }

    @Test
    public void testUDBFT() {
        UndirectedGraph u = createUDGraph(6);
        u.add(1, 3);
        u.add(2, 1);
        u.add(3, 2);
        u.add(5, 4);
        u.add(6, 1);

        ArrayList<Integer> startVertices = new ArrayList<Integer>();
        startVertices.add(1);
        startVertices.add(5);

        BFTTest bft = new BFTTest(u);
        bft.traverse(startVertices);

        int[] expectedOrder = new int[]{1, 3, 2, 6, 5, 4};

        assertArrayEquals(expectedOrder, bft._visitOrder);
    }

    @Test
    public void testDirectedDFTPreOrder() {
        DirectedGraph d = createDGraph(5);

        d.add(1, 2);
        d.add(1, 3);
        d.add(3, 4);
        d.add(4, 5);

        DFTPreOrder preOrder = new DFTPreOrder(d);

        preOrder.traverse(1);

        int[] expectedOrder = new int[]{1, 3, 4, 5, 2};

        assertArrayEquals(expectedOrder, preOrder._visitOrder);
    }

    @Test
    public void testDirectedDFTPostOrder() {
        DirectedGraph d = createDGraph(7);

        d.add(2, 6);
        d.add(7, 6);
        d.add(1, 2);
        d.add(5, 7);
        d.add(4, 5);
        d.add(3, 4);
        d.add(1, 3);

        d.remove(7);

        ArrayList<Integer> startVertices = new ArrayList<Integer>();

        startVertices.add(1);
        startVertices.add(7);

        DFTPostOrder postOrder = new DFTPostOrder(d);
        postOrder.traverse(startVertices);

        int[] expectedOrder = new int[]{5, 4, 3, 6, 2, 1};

        assertArrayEquals(expectedOrder, postOrder._visitOrder);
    }

    @Test
    public void testDFTUD() {
        UndirectedGraph u = createUDGraph(7);

        u.add(2, 6);
        u.add(4, 5);
        u.add(1, 3);
        u.add(2, 1);
        u.add(6, 7);
        u.add(7, 5);
        u.add(3, 4);

        u.remove(7);

        DFTPreOrder preOrder = new DFTPreOrder(u);
        DFTPostOrder postOrder = new DFTPostOrder(u);

        preOrder.traverse(1);
        postOrder.traverse(1);

        int[] expectedPreOrder = new int[]{1, 2, 6, 3, 4, 5};
        int[] expectedPostOrder = new int[]{6, 2, 5, 4, 3, 1};

        assertArrayEquals(expectedPreOrder, preOrder._visitOrder);
        assertArrayEquals(expectedPostOrder, postOrder._visitOrder);
    }


    @Test
    public void test3Traversals() {
        DirectedGraph d = createDGraph(15);

        d.add(5, 6);
        d.add(2, 1);
        d.add(1, 7);
        d.add(5, 4);
        d.add(3, 5);
        d.add(2, 3);

        d.add(8, 9);
        d.add(9, 2);
        d.add(9, 10);

        d.add(10, 15);
        d.add(13, 2);
        d.add(15, 14);
        d.add(15, 13);

        d.remove(9, 2);
        d.remove(15);

        BFTTest bft = new BFTTest(d);
        DFTPreOrder preOrder = new DFTPreOrder(d);
        DFTPostOrder postOrder = new DFTPostOrder(d);

        ArrayList<Integer> startingVertices = new ArrayList<Integer>();
        startingVertices.add(2);
        startingVertices.add(8);

        bft.traverse(startingVertices);
        preOrder.traverse(startingVertices);
        postOrder.traverse(startingVertices);

        int[]expectedPreOrder = new int[]{2, 3, 5, 4, 6, 1, 7, 8, 9, 10, 0, 0, 0, 0};
        int[] expectedPostOrder = new int[]{4, 6, 5, 3, 7, 1, 2, 10, 9, 8, 0, 0, 0, 0};
        int[] expectedBFT = new int[]{2, 1, 3, 7, 5, 6, 4, 8, 9, 10, 0, 0, 0, 0};

        assertArrayEquals(expectedPreOrder, preOrder._visitOrder);
        assertArrayEquals(expectedPostOrder, postOrder._visitOrder);
        assertArrayEquals(expectedBFT, bft._visitOrder);

    }

    @Test
    public void testShortestPaths() {
        DirectedGraph d = createDGraph(5);
        d.add(1, 3);
        d.add(3, 2);
        d.add(2, 4);
        d.add(4, 5);
        d.add(2, 5);
        d.add(1, 5);

        LabeledGraph<Integer, Double> g = new LabeledGraph<Integer, Double>(d);

        g.setLabel(1, 3, 2.0);
        g.setLabel(3, 2, 3.0);
        g.setLabel(1, 5, 5.0);
        g.setLabel(2, 4, 5.0);
        g.setLabel(2, 5, 6.0);
        g.setLabel(4,5 ,7.0);

        TestShortestPaths s = new TestShortestPaths(g, 1, 5);
        s.setPaths();

        ArrayList<Integer> shortestPath = new ArrayList<Integer>();
        shortestPath.add(1);
        shortestPath.add(5);

        assertEquals(shortestPath, s.pathTo(5));
    }

    @Test
    public void testMulitpleSP() {
        UndirectedGraph u = createUDGraph(6);

        u.add(3, 5);
        u.add(5, 4);
        u.add(3, 2);
        u.add(2, 1);
        u.add(6, 1);
        u.add(5, 6);
        u.add(1, 3);

        LabeledGraph<Double, Double> g = new LabeledGraph<Double, Double>(u);

        g.setLabel(2, 1, 1.0);
        g.setLabel(1, 6, 2.0);
        g.setLabel(5, 4, 1.0);
        g.setLabel(2, 3, 1.0);
        g.setLabel(3, 1, 6.0);
        g.setLabel(3, 5, 2.0);
        g.setLabel(5, 6, 3.0);

        TestShortestPaths s = new TestShortestPaths(g, 1, 0);
        s.setPaths();

        ArrayList<Integer> expected5 = new ArrayList<Integer>();
        expected5.add(1);
        expected5.add(2);
        expected5.add(3);
        expected5.add(5);

        ArrayList<Integer> expected6 = new ArrayList<Integer>();
        expected6.add(1);
        expected6.add(6);

        assertEquals(expected5, s.pathTo(5));
        assertEquals(expected6, s.pathTo(6));

        TestShortestPaths s2 = new TestShortestPaths(g, 2, 0);
        s2.setPaths();

        ArrayList<Integer> expectedTwoToSix = new ArrayList<Integer>();
        expectedTwoToSix.add(2);
        expectedTwoToSix.add(1);
        expectedTwoToSix.add(6);

        assertEquals(expectedTwoToSix, s2.pathTo(6));
    }

    @Test
    public void testSP() {
        DirectedGraph d = createDGraph(6);

        d.add(2, 5);
        d.add(5, 4);
        d.add(2, 3);
        d.add(3, 1);
        d.add(1, 6);
        d.add(5, 6);

        LabeledGraph<Double, Double> g = new LabeledGraph<Double, Double>(d);

        g.setLabel(2, 3, 1.0);
        g.setLabel(5, 4, 1.0);
        g.setLabel(2, 5, 9.0);
        g.setLabel(3, 1, 1.0);
        g.setLabel(1, 6, 20.0);
        g.setLabel(5, 6, 3.0);

        TestShortestPaths s = new TestShortestPaths(g, 2, 6);
        s.setPaths();

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(2);
        expected.add(5);
        expected.add(6);

        assertEquals(expected, s.pathTo());
    }
    
    private DirectedGraph createDGraph(int n) {
        DirectedGraph d = new DirectedGraph();
        for (int i = 0; i < n; i += 1) {
            d.add();
        }

        return d;
    }


    private UndirectedGraph createUDGraph(int n) {
        UndirectedGraph u = new UndirectedGraph();
        for (int i = 0; i < n; i += 1) {
            u.add();
        }

        return u;
    }

    private class BFTTest extends BreadthFirstTraversal {
        BFTTest(Graph G) {
            super(G);
            _gr = G;
            _visitOrder = new int[_gr.vertexSize()];
            _index = 0;
        }

        private int[] _visitOrder;
        private Graph _gr;
        private int _index;

        @Override
        protected boolean visit(int v) {
            mark(v);
            _visitOrder[_index] = v;
            _index += 1;
            return true;
        }
    }

    private class DFTPreOrder extends DepthFirstTraversal {
        DFTPreOrder(Graph G) {
            super(G);
            gr = G;
            _visitOrder = new int[gr.vertexSize()];
            _index = 0;

        }

        @Override
        public boolean visit(int v) {
            mark(v);
            _visitOrder[_index] = v;
            _index += 1;
            return super.visit(v);
        }

        private Graph gr;
        private int[] _visitOrder;
        private int _index;
    }

    private class DFTPostOrder extends DepthFirstTraversal {
        DFTPostOrder(Graph G) {
            super(G);
            _gr = G;
            _visitOrder = new int[_gr.vertexSize()];
            _index = 0;
        }

        @Override
        public boolean shouldPostVisit(int v) {
            return true;
        }

        @Override
        public boolean visit(int v) {
            mark(v);
            return super.visit(v);
        }

        @Override
        public boolean postVisit(int v) {
            mark(v);
            _visitOrder[_index] = v;
            _index += 1;
            return true;
        }

        private Graph _gr;
        private int _index;
        private int[] _visitOrder;
    }

    private class TestShortestPaths extends SimpleShortestPaths {
        TestShortestPaths(LabeledGraph G, int source, int dest) {
            super(G, source, dest);
            _labeled = G;
        }

        @Override
        public double getWeight(int v, int u) {
            return _labeled.getLabel(v, u);
        }

        LabeledGraph<Double, Double> _labeled;
    }

}
