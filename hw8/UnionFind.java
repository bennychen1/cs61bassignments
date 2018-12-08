import java.util.Arrays;

/** A partition of a set of contiguous integers that allows (a) finding whether
 *  two integers are in the same partition set and (b) replacing two partitions
 *  with their union.  At any given time, for a structure partitioning
 *  the integers 1-N, each partition is represented by a unique member of that
 *  partition, called its representative.
 *  @author
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        _sets = new int[N];
        // FIXME
    }

    /** Return the representative of the partition currently containing V.
     *  Assumes V is contained in one of the partitions.  */
    public int find(int v) {
        return _sets[v - 1];  // FIXME
    }

    /** Return true iff U and V are in the same partition. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single partition, returning its representative. */
    public int union(int u, int v) {
        int uSet = find(u);
        int vSet = find(v);

        int[] inUSet = new int[_sets.length];
        int[] inVSet = new int[_sets.length];

        int countV = 0;
        int countU = 0;

        for (int i = 0; i < _sets.length; i += 1) {
            if (_sets[i] == vSet) {
                countV += 1;
                inVSet[i] = 1;
            } else if (_sets[i]  == uSet) {
                countU += 1;
                inUSet[i] = 1;
            }
        }

        if (countV < countU || (countV == countU && vSet < uSet)) {
            for (int i = 0; i < _sets.length; i += 1) {
                if (inUSet[i] == 1) {
                    _sets[i] = vSet;
                }
            }

            return vSet;
        } else if (countU < countV || (countV == countU && uSet < vSet)) {
            for (int i = 0; i < _sets.length; i += 1) {
                if (inVSet[i] == 1) {
                    _sets[i] = uSet;
                }
            }

            return uSet;
        }

        return 0;  // FIXME
    }

    // FIXME
    int[] _sets;
}
