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
    public int vertexSize() {
        return _V;
    }

    @Override
    public int maxVertex() {
        if (_V == 0) {
            return 0;
        }
        return _adjList.size();
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public int inDegree(int v) {
        // FIXME
        if (v == 0) {
            return 0;
        }
        return _adjList.get(v - 1).size();
    }

    @Override
    public int outDegree(int v) {
        if (v == 0) {
            return 0;
        }
        return _adjList.get(v - 1).size();
    }

    @Override
    public boolean contains(int v) {
        int vertexIndex = v - 1;
        if (vertexIndex >= 0 && vertexIndex < _adjList.size()) {
            if (_adjList.get(vertexIndex) != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        ArrayList<Integer> edgesU = _adjList.get(u - 1);
        ArrayList<Integer>edgesV = _adjList.get(v - 1);
        if (edgesU.contains(v - 1) || edgesV.contains(u - 1)) {
            return true;
        }

        return false;

    }


    @Override
    public Iteration<Integer> predecessors(int v) {
        // FIXME
        return null;
    }

    // FIXME

    @Override
    public int add() {
        _adjList.add(new ArrayList<Integer>());
        _V += 1;
        return maxVertex();
    }

    @Override
    public int add(int u, int v) {
        ArrayList<Integer> verticesU = _adjList.get(u - 1);
        ArrayList<Integer> verticesV = _adjList.get(v - 1);

        if (verticesU.contains(v - 1)) {
            return edgeId(u, v);
        }

        verticesU.add(v - 1);
        verticesV.add(u - 1);

        Edge e = new Edge(u - 1, v - 1);
        Edge eOther = new Edge(v -1, u - 1);
        _edges.add(e);
        _edges.add(eOther);

        return edgeId(u, v);
    }



    @Override
    public int edgeId(int u, int v) {
        return 0;
    }

}
