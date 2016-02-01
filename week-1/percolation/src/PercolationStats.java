import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    double _mean;
    double _stddev;
    double _confidenceLo;
    double _confidenceHi;
    /**
     * Performs T independent computational experiments on an N-by-N grid.
     * @param N
     * @param T
     * @throws IllegalArgumentException
     */
    public PercolationStats(int N, int T) throws IllegalArgumentException {
        int testCount;
        double[] fractions;

        Percolation perculation;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        testCount = T;
        fractions = new double[testCount];
        for (int expNum = 0; expNum < testCount; expNum++) {
            perculation = new Percolation(N);
            int sitesOpen = 0;
            while (!perculation.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                if (!perculation.isOpen(row, col)) {
                    perculation.open(row, col);
                    sitesOpen++;
                }
            }
            double fraction = (double) sitesOpen / (N * N);
            fractions[expNum] = fraction;
        }
        _mean = StdStats.mean(fractions);
        _stddev = StdStats.stddev(fractions);
        _confidenceLo = mean() - ((1.96 * stddev()) / Math.sqrt(testCount));
        _confidenceHi = mean() + ((1.96 * stddev()) / Math.sqrt(testCount));
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return _mean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return _stddev;
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return _confidenceLo;
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return _confidenceHi;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("mean = " + ps.mean());
        StdOut.println("95% confidence interval = " + 
                ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}