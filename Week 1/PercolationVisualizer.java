import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationVisualizer {
    public static void main(String[] args) {
        final int N = 50;
        Percolation grid = new Percolation(N);
        // info bar is always a fifteenth of the screen
        double infoBarSize = (double)N*N/-15;
        StdDraw.setXscale(0, N*N);
        StdDraw.setYscale(infoBarSize, N*N);
        StdDraw.enableDoubleBuffering();
        while(!grid.percolates()) {
            StdDraw.clear();
            grid.open(StdRandom.uniform(N) + 1, StdRandom.uniform(N) + 1);
            for(int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    double bias = (double)N/2;
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledSquare((j * N) + bias,(N*(N-1) - i * N) + bias, bias);
                    int row = i + 1;
                    int col = j + 1;
                    if(grid.isFull(row, col)) {
                        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    }
                    else if (grid.isOpen(row, col)) {
                        StdDraw.setPenColor(StdDraw.WHITE);
                    }
                    else {
                        StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    }
                    StdDraw.filledSquare((j * N) + bias,(N*(N-1) - i * N) + bias, bias - 0.1*N);
                }
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.textLeft(0, infoBarSize/2, String.format("Open Sites: %d", grid.numberOfOpenSites()));
            StdDraw.textRight(N*N, infoBarSize/2, String.format("Percolates: %b", grid.percolates()));
            StdDraw.show();
        }
        double factor = (double) grid.numberOfOpenSites() / (N*N);
        StdOut.println(factor);
    }
}
