import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP_NODE_INDEX = 0;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF treePercolation;
    private final WeightedQuickUnionUF treeFull;
    private final int bottomNodeIndex;
    private final int sideLength;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.sideLength = n;
        this.grid = new boolean[this.sideLength][this.sideLength];

        // tree percolation is used to assert whether there's a path from the
        // bottom to the top
        this.treePercolation = new WeightedQuickUnionUF(2 + this.sideLength * this.sideLength);

        // while tree full only asserts whether the element is connected to the top
        this.treeFull = new WeightedQuickUnionUF(1 + this.sideLength * this.sideLength);

        this.bottomNodeIndex = 1 + this.sideLength * this.sideLength;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkValidInput(row, col);

        if (isOpen(row, col)) return;

        int pos = toQUnionIndex(row, col);
        if (row == 1) {
            this.treePercolation.union(TOP_NODE_INDEX, pos);
            this.treeFull.union(TOP_NODE_INDEX, pos);
        }

        if (row == this.sideLength) {
            // important: tree full never connects to the bottom!
            this.treePercolation.union(this.bottomNodeIndex, pos);
        }

        this.openCount++;
        this.grid[row - 1][col - 1] = true;
        openNodeInTree(row, col, pos);
    }

    private void checkValidInput(int row, int col) {
        if (row < 1 || row > this.grid.length || col < 1 || col > this.grid[0].length) 
            throw new IllegalArgumentException();
    }

    private void openNodeInTree(int row, int col, int pos) {
        // is there top, right, bottom, right
        boolean top = row > 1 && isOpen(row - 1, col);
        if (top) {
            this.treeFull.union(pos, pos - this.sideLength);
            this.treePercolation.union(pos, pos - this.sideLength);
        }

        boolean right = col < this.sideLength && isOpen(row, col + 1);
        if (right) {
            this.treeFull.union(pos, pos + 1);
            this.treePercolation.union(pos, pos + 1);
        }

        boolean bottom = row < this.sideLength && isOpen(row + 1, col);
        if (bottom) {
            this.treeFull.union(pos, pos + this.sideLength);
            this.treePercolation.union(pos, pos + this.sideLength);
        }

        boolean left = col > 1 && isOpen(row, col - 1);
        if (left) {
            this.treeFull.union(pos, pos - 1);
            this.treePercolation.union(pos, pos - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkValidInput(row, col);
        return this.grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkValidInput(row, col);
        if (!isOpen(row, col)) return false;
        
        // is connected to top
        return this.treeFull.find(toQUnionIndex(row, col)) == this.treeFull.find(TOP_NODE_INDEX);
    }

    private int toQUnionIndex(int row, int col) {
        int index = this.sideLength * (row - 1) + col;
        return index;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (this.grid.length == 1) return isOpen(1, 1);
        
        return this.treePercolation.find(TOP_NODE_INDEX) == this.treePercolation.find(this.bottomNodeIndex);
    }

    private static Percolation performTrial(int n) {
        Percolation trialP = new Percolation(n);

        while (!trialP.percolates()) {
            int randomP = StdRandom.uniform(n);
            int randomQ = StdRandom.uniform(n);
            trialP.open(randomP + 1, randomQ + 1);
        }

        return trialP;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 10;
        Percolation p = performTrial(n);
//        System.out.println(Arrays.deepToString(p.grid));
        printGrid(p);
        printGridTeste(p.grid);
        
//        System.out.println(String.format("%s/%s", p.numberOfOpenSites(), n * n));
//        System.out.println("Percolates? " + p.percolates());
    }
    
    private static void printGridTeste(boolean[][] grid) {
        StringBuilder convertedGrid = new StringBuilder();
        for (int row = 1; row < grid.length; row++) {
            
            for (int col = 1; col < grid[0].length; col++) { 
                convertedGrid.append(grid[row][col] + " ");
            }
            
            convertedGrid.append("\n");
        }
        System.out.println(convertedGrid);
    }

    private static void printGrid(Percolation p) {
        StringBuilder convertedGrid = new StringBuilder();

        for (int row = 1; row < p.grid.length + 1; row++) {
            
            for (int col = 1; col < p.grid[0].length + 1; col++) { 
                
                if (p.isFull(row, col)) convertedGrid.append("||");
                else if (p.isOpen(row, col)) convertedGrid.append("  ");
                else convertedGrid.append("XX");
            }
            
            convertedGrid.append("\n");
        }
        System.out.println(convertedGrid);
    }
}