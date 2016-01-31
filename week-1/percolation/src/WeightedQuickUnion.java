public class WeightedQuickUnion {
		
		private int[] elements;
		private int[] sizeThree;
		
		public WeightedQuickUnion(int size) {
			elements = new int[size];
			sizeThree = new int[size];
			for (int i = 0; i < size; i++) {
				elements[i] = i;
				sizeThree[i] = 1;
			}
		}
		
		private int root(int n) {
			while (elements[n] != n) {
				n = elements[n];
			}
			return n;
		}
		
		public void union(int p, int q) {
//			System.out.println("union " + p + " " + q);
			int rootP = root(p);
			int rootQ = root(q);
			
			if (rootP == rootQ)
				return;
			
			if (sizeThree[rootP] < sizeThree[rootQ]) {
				elements[rootP] = rootQ;
				sizeThree[rootQ] += sizeThree[rootP];
			} else {
				elements[rootQ] = rootP;
				sizeThree[rootP] += sizeThree[rootQ];
			} 
		}
		
		public boolean connected(int p, int q) {
			if (root(elements[p]) == root(elements[q])) {
				return true;
			}
			return false;
		}
		
	private void print() {
		System.out.println(this);
	}
	
	public static void mainEx2(String argc[]) {
		// 1-4 5-9 7-3 4-0 3-5 6-8 6-1 5-4 3-2 
		// 7-4 6-3 7-6 8-2 8-0 8-9 5-9 8-1 4-9 

		WeightedQuickUnion q = new WeightedQuickUnion(10);
		q.union(7, 4);
		q.print();
		q.union(6, 3);
		q.print();
		q.union(7, 6);
		q.print();
		q.union(8, 2);
		q.print();
		q.union(8, 0);
		q.print();
		q.union(8, 9);
		q.print();
		q.union(5, 9);
		q.print();
		q.union(8, 1);
		q.print();
		q.union(4, 9);
		q.print();
		
	}
	public static void main2(String argc[]) {
		// 7-9 4-3 1-9 4-0 1-2 5-2
		WeightedQuickUnion q = new WeightedQuickUnion(10);
		q.union(4, 3);
		q.print();
		q.union(3, 8);
		q.print();
		q.union(6, 5);
		q.print();
		q.union(9, 4);
		q.print();
		q.union(2, 1);
		q.print();
		q.connected(8, 9);
		q.union(5, 0);
		q.print();
		q.union(7, 2);
		q.print();
		q.union(6, 1);
		q.print();
		q.union(7, 3);
		q.print();
	}

	
	public static void main(String argc[]) {

//		0  1  2  3  4  5  6  7  8  9  
//		7  7  3  5  7  7  5  2  7  7

		WeightedQuickUnion q = new WeightedQuickUnion(10);
		q.union(7, 8);
		q.union(7, 9);
		q.union(4, 1);
		q.print();
		
	}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < elements.length; i++) {
				if (i>0)
					sb.append(" ");
				sb.append("" +elements[i]);
			}
			sb.append(" -- ");
			for (int i = 0; i < sizeThree.length; i++) {
				if (i>0)
					sb.append(" ");
				sb.append("" +sizeThree[i]);
			}
			return sb.toString();
		}
		
	}
