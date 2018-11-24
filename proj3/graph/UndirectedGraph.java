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

        if (edgesU == null || edgesV == null) {
            return false;
        }

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

        if (u == 0 || u > _adjList.size()
                ||v == 0 || v > _adjList.size()) {
            return -1;
        }

        ArrayList<Integer> verticesU = _adjList.get(u - 1);
        ArrayList<Integer> verticesV = _adjList.get(v - 1);

        if (verticesU.contains(v - 1)) {
            return edgeId(u, v);
        }

        verticesU.add(v - 1);
        verticesV.add(u - 1);

        Edge e = new Edge(u, v);
        Edge eOther = new Edge(v, u);
        _edges.add(e);
        _edges.add(eOther);

        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if (v == 0 || v > _adjList.size()) {
            return ;
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
        _edges.remove(edgeId(v, u));
        _edges.remove(edgeId(u, v));

        ArrayList<Integer> edgesV = _adjList.get(v - 1);
        ArrayList<Integer> edgesU = _adjList.get(u - 1);

        edgesV.remove(Integer.valueOf(u - 1));
        edgesU.remove(Integer.valueOf(v - 1));
    }

    @Override
    public Iteration<Integer> vertices() {
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
