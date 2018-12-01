package graph;

/* See restrictions in Graph.java. */

import org.antlr.v4.runtime.tree.Tree;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedList;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        // FIXME
        _edgeTo = new int[G.vertexSize()];

        _dist = new double[G.vertexSize()];

        for (int i = 0; i < _dist.length; i += 1) {
            _dist[i] = Integer.MAX_VALUE;
        }

        _dist[source] = 0;
        _edgeTo[source] = source;

        _paths = new ArrayList<ArrayList<Integer>>();
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        ArrayList<Integer> sourcesPath = new ArrayList<Integer>();
        sourcesPath.add(getSource() - 1);

        if (getDest() == 0) {
            for (int i = 0; i < _paths.size(); i += 1) {
                _paths.add(new ArrayList<Integer>());
            }
            _paths.set(getSource() - 1, sourcesPath);
        } else {
            _paths.set(getSource() - 1, sourcesPath);
        }
        // FIXME
    }

    /** Returns the starting vertex. */
    public int getSource() {
        // FIXME
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        // FIXME
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        // FIXME
        if (_dest != 0 && v != _dest) {
            return null;
        }
        return _paths.get(v - 1);
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    // FIXME

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    // FIXME
    int[] _edgeTo;

    double[] _dist;

    ArrayList<ArrayList<Integer>> _paths;

    private class BFTAll extends Traversal {
        BFTAll(Graph G) {
            super(G, new TreeSetQueue());
        }

        @Override
        public boolean visit(int v) {

            return true;
        }
    }

    private class TreeSetQueue extends LinkedList<Integer> {
        TreeSetQueue() {
            _treeSet = new TreeSet<Integer>(new VertexComp());
        }

        @Override
        public boolean add(Integer v) {
            _treeSet.add(v);
            return true;
        }

        @Override
        public Integer peek() {
            int firstElement = _treeSet.pollFirst();
            _treeSet.add(firstElement);
            return firstElement;
        }

        @Override
        public Integer poll() {
            return _treeSet.pollFirst();
        }


        private TreeSet<Integer> _treeSet;
    }

    private class VertexComp implements Comparator<Integer> {
        @Override
        public int compare(Integer v1, Integer v2) {
            if (_dist[v1 - 1] < _dist[v2 - 1]) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
