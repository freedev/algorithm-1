import java.util.Arrays;

/**
 * 
 * @author freedev
 *
 */
public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;
    private int segmentSize;

    private class BinarySearchST {
        private static final int INIT_CAPACITY = 2;
        private PairOfPoints[] keys;
        private LineSegment[] vals;
        private int N = 0;
        
        /**
         * Initializes an empty symbol table.
         */
        public BinarySearchST() {
            this(INIT_CAPACITY);
        }

        /**
         * Initializes an empty symbol table with the specified initial capacity.
         */
        public BinarySearchST(int capacity) { 
            keys = new PairOfPoints[capacity]; 
            vals = new LineSegment[capacity]; 
        }   

        // resize the underlying arrays
        private void resize(int capacity) {
            assert capacity >= N;
            PairOfPoints[]   tempk = new PairOfPoints[capacity];
            LineSegment[] tempv = new LineSegment[capacity];
            for (int i = 0; i < N; i++) {
                tempk[i] = keys[i];
                tempv[i] = vals[i];
            }
            vals = tempv;
            keys = tempk;
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return N;
        }

        /**
         * Returns true if this symbol table is empty.
         *
         * @return <tt>true</tt> if this symbol table is empty;
         *         <tt>false</tt> otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }


        /**
         * Does this symbol table contain the given key?
         *
         * @param  key the key
         * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
         *         <tt>false</tt> otherwise
         * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
         */
        public boolean contains(PairOfPoints key) {
            if (key == null) throw new NullPointerException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the given key in this symbol table.
         *
         * @param  key the key
         * @return the value associated with the given key if the key is in the symbol table
         *         and <tt>null</tt> if the key is not in the symbol table
         * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
         */
        public LineSegment get(PairOfPoints key) {
            if (key == null) throw new NullPointerException("argument to get() is null"); 
            if (isEmpty()) return null;
            int i = rank(key); 
            if (i < N && keys[i].compareTo(key) == 0) return vals[i];
            return null;
        } 

        /**
         * Returns the number of keys in this symbol table strictly less than <tt>key</tt>.
         *
         * @param  key the key
         * @return the number of keys in the symbol table strictly less than <tt>key</tt>
         * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
         */
        public int rank(PairOfPoints key) {
            if (key == null) throw new NullPointerException("argument to rank() is null"); 

            int lo = 0, hi = N-1; 
            while (lo <= hi) { 
                int mid = lo + (hi - lo) / 2; 
                int cmp = key.compareTo(keys[mid]);
                if      (cmp < 0) hi = mid - 1; 
                else if (cmp > 0) lo = mid + 1; 
                else return mid; 
            } 
            return lo;
        } 


        /**
         * Removes the specified key and its associated value from this symbol table
         * (if the key is in this symbol table).
         *
         * @param  key the key
         * @param  val the value
         * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
         */
        public void put(PairOfPoints key, LineSegment val)  {
            if (key == null) throw new NullPointerException("first argument to put() is null"); 

            if (val == null) {
                delete(key);
                return;
            }

            int i = rank(key);

            // key is already in table
            if (i < N && keys[i].compareTo(key) == 0) {
                vals[i] = val;
                return;
            }

            // insert new key-value pair
            if (N == keys.length) resize(2*keys.length);

            for (int j = N; j > i; j--)  {
                keys[j] = keys[j-1];
                vals[j] = vals[j-1];
            }
            keys[i] = key;
            vals[i] = val;
            N++;

        } 

        /**
         * Removes the specified key and associated value from this symbol table
         * (if the key is in the symbol table).
         *
         * @param  key the key
         * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
         */
        public void delete(PairOfPoints key) {
            if (key == null) throw new NullPointerException("argument to delete() is null"); 
            if (isEmpty()) return;

            // compute rank
            int i = rank(key);

            // key not in table
            if (i == N || keys[i].compareTo(key) != 0) {
                return;
            }

            for (int j = i; j < N-1; j++)  {
                keys[j] = keys[j+1];
                vals[j] = vals[j+1];
            }

            N--;
            keys[N] = null;  // to avoid loitering
            vals[N] = null;

            // resize if 1/4 full
            if (N > 0 && N == keys.length/4) resize(keys.length/2);

        } 


    }
    
    /**
     * 
     * @author freedev
     *
     */
    private class PairOfPoints implements Comparable<PairOfPoints> {
        private Point start;
        private Point end;
        public PairOfPoints(Point s, Point e) {
            this.start = s;
            this.end = e;
        }
        @Override
        public int compareTo(PairOfPoints o) {
            // TODO Auto-generated method stub
            if (this.start.compareTo(o.start) == 0)
                return this.end.compareTo(o.end);
            else
                return this.start.compareTo(o.start);
        }
    }
    
    /**
     * 
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        loadPoints(points);
        checkForDuplicates();
        if (segments == null) {
            buildSegments(points);
        }
    }

    /**
     * @param args
     */
    private void loadPoints(Point[] args) {
        if (args == null) {
            throw new java.lang.NullPointerException();
        }
        this.points = new Point[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                throw new java.lang.NullPointerException();
            } else {
                this.points[i] = args[i];
            }
        }
    }

    /**
     * 
     */
    private void checkForDuplicates() {
        if (this.points.length > 1) {
            Arrays.sort(this.points);
            for (int i = 1; i < this.points.length; i++) {
                if (this.points[i - 1].compareTo(this.points[i]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }
    
    /**
     * 
     * @param givenPoints
     */

    private void buildSegments(Point[] givenPoints) {
        if (segments == null) {
            BinarySearchST segmentSet = new BinarySearchST();
            for (int i = 0; i < givenPoints.length; i++) {
                Arrays.sort(this.points, givenPoints[i].slopeOrder());
                // StdOut.println("-");
                // for (int j = 0; j < this.points.length; j++) {
                // StdOut.print(this.points[j]);
                // StdOut.print("-");
                // }
                // StdOut.println("-");
                int j = 1;
                while (j < (this.points.length - 2)) {
                    double s = this.points[0].slopeTo(this.points[j]);
                    int k = 0;
                    while ((j + k) < this.points.length - 1) {
                        if (s != this.points[0]
                                .slopeTo(this.points[j + k + 1])) {
                            break;
                        }
                        k++;
                    }
                    if (k > 1) {
                        Point[] result = new Point[k + 2];
                        result[0] = this.points[0];
                        for (int jj = 0; jj < k + 1; jj++) {
                            result[jj + 1] = this.points[j + jj];
                        }
                        j = j + k + 1;
                        Arrays.sort(result);
                        PairOfPoints l = new PairOfPoints(result[0], result[result.length-1]);
                        if (!segmentSet.contains(l)) {
                            segmentSet.put(l, new LineSegment(l.start, l.end));;
                        }
                    }
                    j++;
                 }
            }
            segmentSize = segmentSet.size();
            segments = segmentSet.vals;
            segmentSet.vals = null;
        }
    }

    /**
     * 
     * @return
     */
    public int numberOfSegments() {
        return segmentSize;
    }

    /**
     * 
     * @return
     */
    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[segmentSize];
        for (int i = 0; i < segments.length; i++) {
            if (segments[i] != null)
                s[i] = segments[i];
        }

        return s;
    }

}
