
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] fraction;
	private int trials;

	public static void main(String[] args) {
		if (args.length > 1) {
			int n = Integer.parseInt(args[0]);
			int t = Integer.parseInt(args[1]);
			PercolationStats p = new PercolationStats(n, t);
			System.out.println("mean = " + p.mean());
			System.out.println("stddev: = " + p.stddev());
			System.out.println("95% confidence interval = " + "[" +
				 			   p.confidenceLo() + "," + p.confidenceHi() +
				 			   "]");
		}
	}
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
		fraction = new double[trials];
		this.trials = trials;
		for (int i = 0; i < trials; i++) {
			Percolation sites = new Percolation(n);
			int openSites = 0;
			while (true) {
				int randomRow = StdRandom.uniform(n) + 1;
				int randomCol = StdRandom.uniform(n) + 1;
				if (!sites.isOpen(randomRow, randomCol)) {
					sites.open(randomRow, randomCol);
					openSites++;
				}
				if (sites.percolates()) {
					fraction[i] = (double) openSites / (n * n);
					break;
				}
			}
		}
	}

	/**
	 * calcute the mean of the fraction array of ints.
	 */
	public double mean() {
		return StdStats.mean(fraction);
	}

	/**
	 * calcute the sample standard deviation.
	 */
	public double stddev() {
		return StdStats.stddev(fraction);
	}

	/**
	 * low endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		double low;
		low = mean() - (1.96 * stddev()) / Math.sqrt(trials);
		return low;
	}

	/**
	 * high endpoint of 95% confidence inteval
	 */
	public double confidenceHi() {
		double high;
		high = mean() + (1.96 * stddev()) / Math.sqrt(trials);
		return high;
	}

}