import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author freedev
 *
 */

public class Percolation {

    private static final byte CLOSED = 0;
    private static final byte OPENED = 1;
    private static final byte FULL = 2;

    // where (1, 1) is the upper-left site
    private WeightedQuickUnionUF mapUnion;
    private WeightedQuickUnionUF mapFully;
    // percolation map
    private byte[] map;
    private byte[] mapBit;
    // size of the map
    private int size;
    // a site within first row
    private int firstRowPos;
    // a site within last row
    private int lastRowPos;
    // percolate status
    private boolean percolatesStatus = false;

    /**
     * Create N-by-N grid, with all sites blocked Initializes all data
     * structures empty, save the size of the map
     *
     * @param N
     *            the number of sites
     */
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException();
        }
        size = N;
        map = new byte[N * N];
        mapBit = new byte[(int)Math.floor((N*N)/4)];
        // Weighted union-find data type
        mapUnion = new WeightedQuickUnionUF(N * N + 2);
        mapFully = new WeightedQuickUnionUF(N * N + 1);
        // defaults to -1
        firstRowPos = N * N;
        lastRowPos = N * N + 1;
    }

    /**
     * helper method - translate row and column into position
     * 
     * @param row
     *            row {@code int}
     * @param col
     *            column {@code int}
     * @return
     */
    private int getPos(int row, int col) {
        return (--row * size + --col);
    }

    /**
     * check if a site at row and column is OPENED
     * 
     * @param row
     *            row {@code int}
     * @param col
     *            column {@code int}
     * @return {@code boolean} returns true if site at row and column is OPENED
     */
    public boolean isOpen(int row, int col) {
        validateBounds(row, col);
        return (map[getPos(row, col)] != CLOSED);
    }

    /**
     * helper method - validate row and column, throws if row or column are not
     * valid.
     * 
     * @param row
     *            row {@code int}
     * @param col
     *            column {@code int}
     */
    private void validateBounds(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * open site (row i, column j) if it is not open already open site at row
     * and column
     * 
     * @param row
     *            {@code int}
     * @param column
     *            {@code int}
     */
    public void open(int row, int col) {
        validateBounds(row, col);

        int pos = getPos(row, col);
        if (getMapValue(pos) == CLOSED) {

            setMapValue(pos, OPENED);

            if (row == 1) {
                mapUnion.union(pos, firstRowPos);
                mapFully.union(pos, firstRowPos);
            }
            if (row == size) {
                mapUnion.union(pos, lastRowPos);
            }

            // Left
            if (col > 1) {
                if (map[pos - 1] != CLOSED) {
                    mapUnion.union(pos, pos - 1);
                    mapFully.union(pos, pos - 1);
                }
            }
            // Right
            if (col < size) {
                if (map[pos + 1] != CLOSED) {
                    mapUnion.union(pos, pos + 1);
                    mapFully.union(pos, pos + 1);
                }
            }
            // Up
            if (row > 1) {
                if (map[pos - size] != CLOSED) {
                    mapUnion.union(pos, pos - size);
                    mapFully.union(pos, pos - size);
                }
            }
            // Down
            if (row < size) {
                if (map[pos + size] != CLOSED) {
                    mapUnion.union(pos, pos + size);
                    mapFully.union(pos, pos + size);
                }
            }

        }
    }

    /**
     * Check if a site at row and column is FULL
     * 
     * @param row
     *            row {@code int}
     * @param col
     *            column {@code int}
     * @return boolean - returns true if site state is FULL
     */
    public boolean isFull(int row, int col) {
        validateBounds(row, col);
        int pos = getPos(row, col);
        if (getMapValue(pos) == OPENED && mapFully.connected(pos, firstRowPos)) {
            setMapValue(pos, FULL);
        }
        if (getMapValue(pos) == FULL)
            return true;
        return false;
    }

    /**
     * check if map percolates
     * 
     * @return returns true if map percolates
     */
    public boolean percolates() {
        if (!this.percolatesStatus) {
            if (mapUnion.connected(lastRowPos, firstRowPos)) {
                this.percolatesStatus = true;
            }
        }
        return this.percolatesStatus;
    }
    
    private int getMapValue(int pos) {
        return map[pos];
    }

    private void setMapValue(int pos, byte value) {
        map[pos] = value;
    }
    

    private int getMapBytePos(int pos) {
        return Math.abs(pos/4);
    }

    private int getMapBitPos(int pos) {
        return (pos%4)*2;
    }
    
    private int getMapBitValue(int site_pos) {
        int pos = getMapBytePos(site_pos);
        int bit = getMapBitPos(site_pos);
        return ((mapBit[pos] >> bit ) & 3 );
    }

    private void setMapBitValue(int site_pos, byte value) {
        int pos = getMapBytePos(site_pos);
        int bit = getMapBitPos(site_pos);
        int byte_value = (byte) (value << bit);
        mapBit[pos] = (byte)((mapBit[pos] & (~(3 << bit)) ) | byte_value);
    }
    
//    public static void main(String[] args) {
//        Percolation p = new Percolation(10);
//       
//        for (int row = 1; row < 10; row++) {
//            for (int col = 1; col < 10; col++) {
//                System.out.println(p.getMapBytePos(row, col) + " " + p.getMapBitPos(row, col));
//                p.setMapBitValue(row, col, (byte)(col%3));
//                System.out.println(p.getMapBitValue(row, col));
//            }
//        }
//    }
    

}
