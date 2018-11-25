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
        return 0;
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
        return null;
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
    }

    @Override
    protected int edgeId(int u, int v) {
        // FIXME
        return 0;
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
