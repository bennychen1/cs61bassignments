import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        q.add(s);
        while (!q.isEmpty()) {
            int cur = q.poll();
            marked[cur] = true;
            announce();
            if (cur == t) {
                targetFound = true;
            }

            if (targetFound) {
                return ;
            }
            for (int w : maze.adj(cur)) {
                if (!marked[w]) {
                    marked[w] = true;
                    q.add(w);
                    edgeTo[w] = cur;
                    distTo[w] = distTo[cur] + 1;
                }
            }
        }
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
    }


    @Override
    public void solve() {
        bfs();
    }
}

