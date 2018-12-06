package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Benny Chen
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _edgeTo = new int[G.vertexSize()];
        _dist = new double[G.vertexSize()];

        for (int i = 0; i < _dist.length; i += 1) {
            _dist[i] = Double.MAX_VALUE;
        }

        _dist[source - 1] = 0;
        _edgeTo[source - 1] = source;

        _labeled = new LabeledGraph<Double, Double>(G);

    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (!_G.contains(v)) {
            return Double.MAX_VALUE;
        }
        return _dist[v - 1];
    }

    @Override
    protected void setWeight(int v, double w) {
        if (!_G.contains(v)) {
            return;
        }
        _dist[v - 1] = w;
    }

    @Override
    public int getPredecessor(int v) {
        if (!_G.contains(v) || _edgeTo[v - 1] == 0) {
            return 0;
        }
        return _edgeTo[v - 1];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        if (!_G.contains(v)) {
            return;
        }
        _edgeTo[v - 1] = u;
    }

    /** The labeled graph version of G. */
    private LabeledGraph<Double, Double> _labeled;

    /** The weights of each vertex. */
    private double[] _dist;

    /** The predecessor vertices of each vertex. */
    private int[] _edgeTo;

}
