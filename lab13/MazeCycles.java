import java.util.Observable;
/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    boolean targetFound = false;
    public int[] keepEdges;

    public MazeCycles(Maze m) {
        super(m);
        keepEdges = new int[maze.V()];

    }

    @Override
    public void solve() {
        int s = maze.xyTo1D(1, 1);
        int t = maze.xyTo1D(maze.N(), maze.N());
        distTo[s] = s;
        keepEdges[s] = s;
        dfsCycles(s, t);
        // TODO: Your code here!
    }

    // Helper methods go here
    private void dfsCycles(int v, int t) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (marked[w] && keepEdges[v] != w) {
                edgeTo[v] = w;
                edgeTo[w] = keepEdges[w];
                edgeTo[v] = keepEdges[w];
                announce();
                return;
            }

            if (!marked[w]) {
                keepEdges[w] = v;
                distTo[w] = distTo[v] + 1;
                dfsCycles(w, t);
                if (targetFound) {
                    return;
                }
            }
        }
    }
}


