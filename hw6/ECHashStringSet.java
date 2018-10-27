// REPLACE THIS STUB WITH THE CORRECT SOLUTION.
// The current contents of this file are merely to allow things to compile
// out of the box.

import java.util.List;
import java.util.ArrayList;

/** A set of String values.
 *  @author
 */
class ECHashStringSet implements StringSet {
    public ECHashStringSet() {
        _buckets = new ArrayList<ArrayList<String>>(2);
        numBuckets = 2;

        for (int i = 0; i < numBuckets; i += 1) {
            ArrayList<String> initial = new ArrayList<String>();
            _buckets.add(initial);
        }
    }

    @Override
    public void put(String s) { ;
        if ((_added + 1) / numBuckets > 5) {
            resize();
        }

        int indexToPut = s.hashCode() & 0x7fffffff % numBuckets;

        ArrayList<String> bucketAtIndex = _buckets.get(indexToPut);

        if (!(bucketAtIndex.contains(s))) {
            bucketAtIndex.add(s);
            _added += 1;

        }
    }

    @Override
    public boolean contains(String s) {
        int indexToSearch = s.hashCode() % numBuckets;
        return _buckets.get(indexToSearch).contains(s);
    }

    @Override
    public List<String> asList() {
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < _buckets.size(); i += 1) {
            result.addAll(_buckets.get(i));
        }

        return result;
    }

    /**
    int hashCode(String s) {
        int hC = 0;
        for (int i = s.length() - 1, j = 0; i >= 0; i -= 1, j += 1) {
            char curChar = s.charAt(i);
            hC += curChar * (31 ^ j);
        }
        return hC;
    } */

    void resize() {
        while ((_added + 1) / numBuckets > 0.2) {
            numBuckets += 1;
        }

        ArrayList<ArrayList<String>> temp = _buckets;
        ArrayList<ArrayList<String>> expanded = new ArrayList<ArrayList<String>>(numBuckets);
        _buckets = expanded;

        for (int i = 0; i < numBuckets; i += 1) {
            ArrayList<String> initial = new ArrayList<String>();
            _buckets.add(initial);
        }

        for (int i = 0; i < _buckets.size(); i += 1) {
            for (int j = 0; j < _buckets.get(i).size(); j += 1) {
                put(_buckets.get(i).get(j));
            }
        }
    }

    private ArrayList<ArrayList<String>> _buckets;
    private int numBuckets;
    private int _added;
}
