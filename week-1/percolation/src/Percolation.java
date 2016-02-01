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
    // stack last row opened sites
    private int[] lastRow;
    // stack list of all opened sites
    private int[] openSites;
    // size of the map
    private int size;
    // number of opened sites within first row
    private int firstRowPos;
    // number of opened sites within last row
    private int sizeLastRow;
    // number of all opened sites
    private int sizeOpenSites;
    // percolate status
    private boolean percolatesStatus = false;

    /**
     * Create N-by-N grid, with all sites blocked
     * Initializes all data structures empty, save the size of the map
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
        // stack - optimization for last row
        lastRow = new int[N];
        // stack - list of open sites
        openSites = new int[N * N];
        // Weighted union-find data type
        mapUnion = new WeightedQuickUnionUF(N * N);
        // defaults to -1
        firstRowPos = -1;
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
    public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
        validateBounds(i, j);
        return (map[getPos(i, j)] == OPENED);
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
    private void validateBounds(int i, int j) throws IndexOutOfBoundsException {
        if (i < 1 || j < 1 || i > size || j > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * open site (row i, column j) if it is not open already
     * open site at row and column
     * 
     * @param row
     *            {@code int}
     * @param column
     *            {@code int}
     * @throws IndexOutOfBoundsException
     */
    public void open(int row, int col) throws IndexOutOfBoundsException {
        validateBounds(row, col);
        int pos = getPos(row, col);
        if (map[pos] == CLOSED) {
            map[pos] = OPENED;

            openSites[sizeOpenSites] = pos;
            sizeOpenSites++;

            if (row == 1) {
                if (firstRowPos == -1) {
                    firstRowPos = col - 1;
                } else {
                    mapUnion.union(firstRowPos, pos);
                }
                
            }
            if (row == size) {
                lastRow[sizeLastRow] = getPos(row, col);
                sizeLastRow++;
            }

            // Left
            if (col > 1 && map[pos - 1] != CLOSED) {
                mapUnion.union(pos, pos - 1);
            }
            // Right
            if (col < size && map[pos + 1] != CLOSED) {
                mapUnion.union(pos, pos + 1);
            }
            // Up
            if (row > 1 && map[pos - size] != CLOSED) {
                mapUnion.union(pos, pos - size);
            }
            // Down
            if (row < size && map[pos + size] != CLOSED) {
                mapUnion.union(pos, pos + size);
            }

            // If a first row site is opened we can try to see if other open
            // sites are connected
            if (firstRowPos != -1) {
                    int resizeOpenSites = 0;
                    for (int j = 0; j < sizeOpenSites; j++) {
                        if (resizeOpenSites > 0) {
                            openSites[j - resizeOpenSites] = openSites[j];
                        }
                        if (map[openSites[j]] != FULL && mapUnion
                                .connected(openSites[j], firstRowPos)) {
                            resizeOpenSites++;
                            map[openSites[j]] = FULL;
                        }
                    }
                    sizeOpenSites -= resizeOpenSites;
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
        return (map[getPos(i, j)] == FULL);
    }

    /**
     * check if map percolates
     * 
     * @return returns true if map percolates
     */
    public boolean percolates() {
        if (!this.percolatesStatus && firstRowPos != -1) {
            for (int j = 0; j < sizeLastRow; j++) {
                if (map[lastRow[j]] != CLOSED) {
                    if (mapUnion.connected(lastRow[j], firstRowPos))
                        this.percolatesStatus = true;
                }
            }
        }
        return this.percolatesStatus;
    }

}
