package graph;

import java.util.ArrayList;


/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Benny Chen
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }


    @Override
    public int inDegree(int v) {

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
        ArrayList<Integer> edgesV = _adjList.get(v - 1);

        if (edgesU == null || edgesV == null) {
            return false;
        }

        if (edgesU.contains(v - 1) || edgesV.contains(u - 1)) {
            return true;
        }

        return false;

    }


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

        _edges.add(e);

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
            return;
        }

        int e = edgeId(v, u);
        int e2 = edgeId(u, v);

        if (e != 0) {
            _edges.remove(e - 1);
        } else if (e2 != 0) {
            _edges.remove(e2 - 1);
        }

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

        return Iteration.iteration(edgePairs.iterator());
    }


    @Override
    public Iteration<Integer> predecessors(int v) {
        return successors(v);
    }
}
