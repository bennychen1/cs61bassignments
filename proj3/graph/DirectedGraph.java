package graph;

import java.util.ArrayList;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        //FIXME
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        // FIXME
        return null;
    }

    // FIXME

    @Override
    public int add(int v, int u) {
        if (!contains(v) || !contains(u)) {
            checkMyVertex(v);
            checkMyVertex(u);
        }

        ArrayList<Integer> edgesV = _adjList.get(v - 1);

        if (edgesV.contains(u - 1)) {
            return edgeId(v, u);
        }

        _adjList.get(v - 1).add(u - 1);

        _edges.add(new Edge(v, u));

        return edgeId(v, u);
    }

    @Override
    public boolean contains(int v, int u) {
        if (!contains(v)) {
            return false;
        }

        if (_adjList.get(v - 1).contains(u - 1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void remove(int v) {
        if (!contains(v)) {
            return ;
        }

        ArrayList<Integer> outEdges = _adjList.get(v - 1);

        ArrayList<Integer> edgeNumbers = new ArrayList<Integer>();
        edgeNumbers.addAll(outEdges);

        for (int outVertex : edgeNumbers) {
            remove(v, outVertex + 1);
        }

        Iterator<Edge> edgeIterator = _edges.iterator();

        while (edgeIterator.hasNext()) {
            Edge curEdge = edgeIterator.next();
            if (curEdge.getTo() == v - 1) {
                _adjList.get(curEdge.getFrom()).remove(Integer.valueOf(v - 1));
                edgeIterator.remove();
            }
        }

        _V -= 1;

        _adjList.set(v - 1, null);
    }

    @Override
    public void remove(int v, int u) {
        if (!contains(v) || !contains(u)) {
            return;
        }

        _adjList.get(v - 1).remove(Integer.valueOf(u - 1));

        int index = 0;
        for (Edge e : _edges) {
            if (e.getFrom() == v - 1 && e.getTo() == u - 1) {
                _edges.remove(index);
                return;
            } else {
                index += 1;
            }
        }
    }
}
