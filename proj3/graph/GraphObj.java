package graph;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _V = 0;
        _adjList = new ArrayList<ArrayList<Integer>>();
        _edges = new ArrayList<Edge>();
        // FIXME
    }

    @Override
    public int vertexSize() {
        // FIXME
        return _V;
    }

    @Override
    public int maxVertex() {
        if (_V == 0) {
            return 0;
        }

        int curIndex = _adjList.size() - 1;
        while (_adjList.get(curIndex) == null) {
            curIndex -= 1;
        }

        return curIndex + 1;
        // FIXME
    }

    @Override
    public int edgeSize() {
        // FIXME
        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        // FIXME
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        int vertexIndex = u - 1;
        if (vertexIndex >= 0 && vertexIndex < _adjList.size()) {
            if (_adjList.get(vertexIndex) != null) {
                return true;
            }
        }
        // FIXME
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        // FIXME
        return false;
    }

    @Override
    public int add() {
        // FIXME
        if (_adjList.size() == 0) {
            _adjList.add(new ArrayList<Integer>());
        } else if (_adjList.get(_adjList.size() - 1) == null) {
            _adjList.set(_adjList.size() - 1, new ArrayList<Integer>());
        } else {
            _adjList.add(new ArrayList<Integer>());
        }
        _V += 1;
        return maxVertex();
    }

    @Override
    public int add(int u, int v) {
        // FIXME
        return 0;
    }

    @Override
    public void remove(int v) {
        // FIXME
    }

    @Override
    public void remove(int u, int v) {
        // FIXME
    }

    @Override
    public Iteration<Integer> vertices() {
        // FIXME
        int[] verticesArray = new int[_V];

        int index = 0;
        int vertexNumber = 0;

        for (int i = 0; i < _adjList.size(); i += 1) {
            vertexNumber = i + 1;
            if (_adjList.get(i) != null) {
                verticesArray[index] = vertexNumber;
                index += 1;
            }
        }

        ArrayList<Integer> verticesArrayList = new ArrayList<Integer>();

        for (int i : verticesArray) {
            verticesArrayList.add(i);
        }

        return new VertexIteration(verticesArrayList.iterator());
    }

    @Override
    public Iteration<Integer> successors(int v) {
        // FIXME
        return null;
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        // FIXME
        return null;
    }

    @Override
    protected void checkMyVertex(int v) {
        // FIXME
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        // FIXME (Keep duplicate edges in undirected graphs separate)
        int i = 0;
        for (Edge e : _edges) {
            if (e.getFrom() == u - 1 && e.getTo() == v - 1){
                return i;
            }

            i += 1;
        }
        return -1;
    }

    // FIXME

    static class Edge {
        Edge(int u, int v) {
            _from = u - 1;
            _to = v - 1;
        }

        int getFrom() {
            return _from;
        }

        int getTo() {
            return _to;
        }

        private int _from;
        private int _to;
    }

    static class VertexIteration<Type> extends Iteration<Type>{
        VertexIteration(Iterator<Type> iter) {
            _iter = iter;
        }

        public boolean hasNext() {
            return _iter.hasNext();
        }

        public Type next() {
            return _iter.next();
        }

        private Iterator<Type> _iter;


    }

    /** The number of vertices in the graph. */
    protected int _V;

    /** An ArrayList of ArrayList containing the
     * adjacent vertices of all the vertices in the graph.*/
    protected ArrayList<ArrayList<Integer>> _adjList;

    /** An ArrayList containing the edges of the graph */
    protected ArrayList<Edge> _edges;

}
