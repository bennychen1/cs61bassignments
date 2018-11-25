package graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author
 */
public class UndirectedGraph extends GraphObj {

    UndirectedGraph() {
        super();
    }

    @Override
    public boolean isDirected() {
        return false;
    }


    @Override
    public int inDegree(int v) {
        // FIXME
        if (!contains(v)) {
            return 0;
        }

        return _adjList.get(v - 1).size();
    }

    @Override
    public int outDegree(int v) {
        return inDegree(v);
    }


    @Override
    public boolean contains(int u, int v) {
        ArrayList<Integer> edgesU = _adjList.get(u - 1);
        ArrayList<Integer>edgesV = _adjList.get(v - 1);

        if (edgesU == null || edgesV == null) {
            return false;
        }

        if (edgesU.contains(v - 1) || edgesV.contains(u - 1)) {
            return true;
        }

        return false;

    }

    // FIXME

    @Override
    public int add(int u, int v) {

        if (!contains(u) || !contains(v)) {
            checkMyVertex(u);
            checkMyVertex(v);
        }

        ArrayList<Integer> verticesU = _adjList.get(u - 1);
        ArrayList<Integer> verticesV = _adjList.get(v - 1);

        if (verticesU.contains(v - 1)) {
            return edgeId(u, v);
        }

        verticesU.add(v - 1);
        if (u != v) {
            verticesV.add(u - 1);
        }

        Edge e = new Edge(u, v);
        Edge eOther = new Edge(v, u);
        _edges.add(e);
        _edges.add(eOther);

        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if (!contains(v)) {
            return;
        }

        ArrayList<Integer> edgesV = _adjList.get(v - 1);

        ArrayList<Integer> edgeNumbers = new ArrayList<Integer>();

        edgeNumbers.addAll(edgesV);

        for (int i : edgeNumbers) {
            _adjList.get(i).remove(Integer.valueOf(v - 1));
            remove(v, i + 1);
        }

        _adjList.set(v - 1, null);

        _V -= 1;
    }

    @Override
    public void remove(int v, int u) {

        if (!contains(v) || !contains(u)) {
            return ;
        }

        _edges.remove(edgeId(v, u));
        _edges.remove(edgeId(u, v));

        ArrayList<Integer> edgesV = _adjList.get(v - 1);
        ArrayList<Integer> edgesU = _adjList.get(u - 1);

        edgesV.remove(Integer.valueOf(u - 1));
        edgesU.remove(Integer.valueOf(v - 1));
    }

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> edgePairs = new ArrayList<int[]>();
        for (Edge e : _edges) {
            edgePairs.add(new int[]{e.getFrom() + 1, e.getTo() + 1});
        }

        ArrayList<int[]> duplicates = new ArrayList<int[]>();

        for (int i = 1; i < edgePairs.size(); i += 2) {
            duplicates.add(edgePairs.get(i));
        }

        edgePairs.removeAll(duplicates);

        return Iteration.iteration(edgePairs.iterator());
    }

    @Override
    public Iteration<Integer> successors(int v) {
        if (v == 0 || v > _adjList.size()) {
            return Iteration.iteration(new ArrayList<Integer>().iterator());
        }

        ArrayList<Integer> outEdges = _adjList.get(v - 1);

        for (int i = 0; i < outEdges.size(); i += 1) {
            outEdges.set(i, outEdges.get(i) + 1);
        }

        return Iteration.iteration(outEdges.iterator());
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return successors(v);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }



    @Override
    public int edgeId(int u, int v) {
        int i = 0;
        for (Edge e : _edges) {
            if (e.getFrom() == u - 1 && e.getTo() == v - 1){
                return i;
            }

            i += 1;
        }
        return -1;
    }
}
