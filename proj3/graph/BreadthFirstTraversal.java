package graph;

import java.util.concurrent.ArrayBlockingQueue;

/* See restrictions in Graph.java. */

/** Implements a breadth-first traversal of a graph.  Generally, the
 *  client will extend this class, overriding the visit method as desired
 *  (by default, it does nothing).
 *  @author
 */
public class BreadthFirstTraversal extends Traversal {

    /** A breadth-first Traversal of G. */
    protected BreadthFirstTraversal(Graph G) {
        super(G, new ArrayBlockingQueue<Integer>(G.vertexSize()));
        // FIXME
    }


    @Override
    protected boolean visit(int v) {
        System.out.println(v);
        return super.visit(v);
    }

}
