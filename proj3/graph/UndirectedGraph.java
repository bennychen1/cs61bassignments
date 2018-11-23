package graph;

import java.lang.reflect.Array;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author
 */
public class UndirectedGraph extends GraphObj {

    /** Instantiate and undirected graph with V vertices. */
    public UndirectedGraph(int v) {
        _V = v;
        _adjList = new ArrayList<>();

        for (int i = 0; i < v; i += 1) {
            _adjList.add(new ArrayList<Integer>());
        }

    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        // FIXME
        return 0;
    }

    @Override
    public int outDegree(int v) {
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        // FIXME
        return null;
    }

    // FIXME

    @Override
    public int vertexSize() {
        return _adjList.size();
    }

    @Override
    public int add() {
        _adjList.add(new ArrayList<Integer>());
        return _adjList.size() - 1;
    }

    @Override
    public int add(int u, int v) {
        return 0;
    }

    @Override
    public boolean contains(int u) {
        return true;
    }

    @Override
    public boolean contains(int u, int v) {
        return true;
    }


    /** A List of the vertices and the out edges, which are
     * also in edges. */
    private ArrayList<ArrayList<Integer>> _adjList;

    /** The number of vertices in the graph. */
    private int _V;

}
