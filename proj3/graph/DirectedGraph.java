package graph;

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

        _adjList.get(v - 1).add(u - 1);

        _edges.add(new Edge(u, v));

        return edgeId(u, v);
    }

}
