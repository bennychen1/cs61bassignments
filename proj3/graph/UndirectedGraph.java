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

        for (int i = 0; i < v; i += 1) {
            adjList.add(new ArrayList<Integer>());
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


    private ArrayList<ArrayList<Integer>> adjList;
    private int _V;

}
