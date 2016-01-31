import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private static final int CLOSED = 0;
	private static final int OPENED = 1;
	private static final int FULL = 2;
	
	// where (1, 1) is the upper-left site
	private WeightedQuickUnionUF mapUnion;
	private int[] map;
	private int[] firstRow;
	private int[] lastRow;
	private int[] openedCell;
	private int size;
	private int sizeFirstRow;
	private int sizeLastRow;
	private int sizeOpenedCells;
	private boolean perculatesStatus = false;
	
	public Percolation(int N) {
		if (N < 1) {
			throw new IllegalArgumentException();
		}
		size = N;
		map = new int[N*N];
		firstRow = new int[N];
		lastRow = new int[N];
		openedCell = new int[N*N];
		mapUnion = new WeightedQuickUnionUF(N*N);
	}
	
	private int getPos(int i, int j) {
		return (--i*size+--j);
	}

	public boolean isOpen(int i, int j) {
		checkOutOfBounds(i, j);
		return (map[getPos(i, j)] == OPENED);
		
	}
	
	private void checkOutOfBounds(int i, int j) {
		if (i < 1 || j < 1 || i > size || j > size) {
			throw new IndexOutOfBoundsException();
		}		
	}

	public void open(int row, int col) {
		checkOutOfBounds(row, col);
		int pos = getPos(row, col);
		if (map[pos] == CLOSED) {
			map[pos] = OPENED;
			
			openedCell[sizeOpenedCells] = pos;
			sizeOpenedCells++;
			
			if (row == 1) {
				firstRow[sizeFirstRow] = col-1;
				sizeFirstRow++;
			}
			if (row == size) {
				lastRow[sizeLastRow] = getPos(row, col);
				sizeLastRow++;
			}

			// Left
			if (col > 1 && map[pos-1] != CLOSED) {
				mapUnion.union(pos, pos-1);
			}
			// Right
			if (col < size && map[pos+1] != CLOSED) {
				mapUnion.union(pos, pos+1);
			}
			// Up
			if (row > 1 && map[pos-size] != CLOSED) {
				mapUnion.union(pos, pos-size);
			}
			// Down
			if (row < size && map[pos+size] != CLOSED) {
				mapUnion.union(pos, pos+size);
			}
			if (sizeFirstRow>0) {
				for (int i = 0; i < sizeFirstRow; i++) {
					int resizeOpenedCells = 0;
					for (int j = 0; j < sizeOpenedCells; j++) {
						if (resizeOpenedCells > 0) {
							openedCell[j-resizeOpenedCells] = openedCell[j];
						}
						if (map[openedCell[j]] != FULL && mapUnion.connected(openedCell[j], firstRow[i])) {
							resizeOpenedCells++;
							map[openedCell[j]] = FULL;
						}
					}
					sizeOpenedCells -= resizeOpenedCells;
				}
//				System.out.println("sizeOpenedCells: " +sizeOpenedCells );
			}
		}
	}

	public boolean isFull(int i, int j) {
		checkOutOfBounds(i, j);
		return (map[getPos(i, j)] == FULL);
	}

	public boolean percolates() {
		if (!this.perculatesStatus) {
			for (int j = 0; j < sizeLastRow; j++) {
				if (map[lastRow[j]] != CLOSED) {
					for (int i = 0; i < sizeFirstRow; i++) {
						if (mapUnion.connected(lastRow[j], firstRow[i]))
							this.perculatesStatus=true;
					}
				}
			}
		}
		return this.perculatesStatus;
	}

}
