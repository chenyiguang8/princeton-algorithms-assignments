
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] grid;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufTop;
	private int numOpen;
	private int n;

	/**
	 * create a n by n boolean grid
	 * represent each site in the gird from 0 to n^2-1.
	 * represent top visual site with n^2.
	 * represent bottom visual site with n^2+1.
	 */
	public Percolation(int n) {
		if (n <= 0) throw new IllegalArgumentException();
		this.n = n;
		grid = new boolean[n][n];
		uf = new WeightedQuickUnionUF(n * n + 2);
		ufTop = new WeightedQuickUnionUF(n * n + 1);
	}

	/**
	 * open a site. if there is any site around it , union them.
	 * @param row 
	 * @param col
	 */
	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			grid[row - 1][col - 1] = true;
			numOpen++;
			//if the site is in the first row, union with the top
			if (row == 1) {
				uf.union(siteLabel(row, col), n * n);
				ufTop.union(siteLabel(row, col), n * n);
			}
			//if the site is in the bottom row, union with the bottom
			if (row == n) {
				uf.union(siteLabel(row, col), n * n + 1);
			}
			//check the above site.
			if (row > 1 && isOpen(row - 1, col)) {
				uf.union(siteLabel(row, col), siteLabel(row - 1, col));
				ufTop.union(siteLabel(row, col), siteLabel(row - 1, col));
			}
			//check the right site.
			if (col < n && isOpen(row, col + 1)) {
				uf.union(siteLabel(row, col), siteLabel(row, col + 1));
				ufTop.union(siteLabel(row, col), siteLabel(row, col + 1));
			}
			//check the below site.
			if (row < n && isOpen(row + 1, col)) {
				uf.union(siteLabel(row, col), siteLabel(row + 1, col));
				ufTop.union(siteLabel(row, col), siteLabel(row + 1, col));
			}
			//check the left site.
			if (col > 1 && isOpen(row, col - 1)) {
				uf.union(siteLabel(row, col), siteLabel(row, col - 1));
				ufTop.union(siteLabel(row, col), siteLabel(row, col - 1));
			}
		}
	}

	/**
	 * return the siteLabel ranging from 0 to n^2 - 1.
	 * @param row ranging from 1 to n.
	 * @param col ranging from 1 to n.
	 * @return 
	 */
	private int siteLabel(int row, int col) {
		return (row - 1) * n + (col - 1);
	}

	/** 
	 * return true if the site is open.
	 */
	public boolean isOpen(int row, int col) {
		if (row <= 0 || col <= 0 ||
			row > n || col > n) {
			throw new IndexOutOfBoundsException();
		}
		return grid[row - 1][col - 1];
	}

	/**
	 * check whether the site is a full site
	 * a full site is ....
	 */
	public boolean isFull(int row, int col) {
		if (row <= 0 || col <= 0 ||
			row > n || col > n) {
			throw new IndexOutOfBoundsException();
		}
		return ufTop.connected(siteLabel(row, col), n * n);
	}

	/**
	 * return the number of the open site.
	 */
	public int numberOfOpenSites() {
		return numOpen;
	}

	/**
	 * check whether the system percolate.
	 */
	public boolean percolates() {
		/*for (int col = 1; col <= n; col++) {
			if (uf.connected(siteLabel(n, col), n * n)) {
				return true;
			}
		}
		return false;*/
		return (uf.connected(n * n, n * n + 1));
	}
}