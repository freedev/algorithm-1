import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author freedev
 *
 */

public class Percolation {

    private static final int CLOSED = 0;
    private static final int OPENED = 1;
    private static final int FULL = 2;

    // where (1, 1) is the upper-left site
    private WeightedQuickUnionUF mapUnion;
    // percolation map
    private int[] map;
    // size of the map
    private int size;
    // number of opened sites within first row
    private int firstRowPos;
    // number of opened sites within last row
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
        map = new int[N * N];
        // Weighted union-find data type
        mapUnion = new WeightedQuickUnionUF(N * N + 2);
        // defaults to -1
        firstRowPos = N * N;
        lastRowPos = -1;
    }

    /**
     * helper method - translate row and column into position
     * 
     * @param i
     *            row {@code int}
     * @param j
     *            column {@code int}
     * @return
     */
    private int getPos(int i, int j) {
        return (--i * size + --j);
    }

    /**
     * check if a site at row and column is OPENED
     * 
     * @param i
     *            row {@code int}
     * @param j
     *            column {@code int}
     * @return {@code boolean} returns true if site at row and column is OPENED
     * 
     * @throws IndexOutOfBoundsException
     */
    public boolean isOpen(int i, int j) {
        validateBounds(i, j);
        return (map[getPos(i, j)] != CLOSED);
    }

    /**
     * helper method - validate row and column, throws if row or column are not
     * valid.
     * 
     * @param i
     *            row {@code int}
     * @param j
     *            column {@code int}
     * @throws IndexOutOfBoundsException
     */
    private void validateBounds(int i, int j) {
        if (i < 1 || j < 1 || i > size || j > size) {
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
     * @throws IndexOutOfBoundsException
     */
    public void open(int row, int col) {
        validateBounds(row, col);
        int pos = getPos(row, col);
        if (map[pos] == CLOSED) {
            map[pos] = OPENED;

            if (row == 1) {
                mapUnion.union(pos, firstRowPos);
                map[pos] = FULL;
            }

            // Left
            if (col > 1 && map[pos - 1] != CLOSED) {
                mapUnion.union(pos, pos - 1);
                if (map[pos - 1] == FULL)
                    map[pos] = FULL;
            }
            // Right
            if (col < size && map[pos + 1] != CLOSED) {
                mapUnion.union(pos, pos + 1);
                if (map[pos + 1] == FULL)
                    map[pos] = FULL;
            }
            // Up
            if (row > 1 && map[pos - size] != CLOSED) {
                mapUnion.union(pos, pos - size);
                if (map[pos - size] == FULL)
                    map[pos] = FULL;
            }
            // Down
            if (row < size && map[pos + size] != CLOSED) {
                mapUnion.union(pos, pos + size);
                if (map[pos + size] == FULL)
                    map[pos] = FULL;
            }
            if (row == size) {
                if (map[pos] == FULL) {
                    percolatesStatus = true;
                } else {
                    lastRowPos = pos;
                }
            }

        }
    }

    /**
     * Check if a site at row and column is FULL
     * 
     * @param i
     *            row {@code int}
     * @param j
     *            column {@code int}
     * @return boolean - returns true if site state is FULL
     */
    public boolean isFull(int i, int j) {
        validateBounds(i, j);
        int pos = getPos(i, j);
        if (map[pos] == OPENED && mapUnion.connected(pos, firstRowPos)) {
            map[pos] = FULL;
            return true;
        }
        if (map[pos] == FULL)
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
            if (lastRowPos != -1 && mapUnion.connected(lastRowPos, firstRowPos))
                this.percolatesStatus = true;
        }
        return this.percolatesStatus;
    }

}
