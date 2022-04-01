import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        
        this.trials = trials;
        this.thresholds = performMonteCarloSimulation(n);
    }

    private double[] performMonteCarloSimulation(int n) {
        double[] thresholdsArr = new double[this.trials];

        for (int i = 0; i < thresholdsArr.length; i++) {
            thresholdsArr[i] = performTrial(n);
        }

        return thresholdsArr;
    }

    private double performTrial(int n) {
        Percolation trialP = new Percolation(n);

        while (!trialP.percolates()) {
            int randomP = StdRandom.uniform(n);
            int randomQ = StdRandom.uniform(n);
            trialP.open(randomP + 1, randomQ + 1);
        }

        double threshold = (double) trialP.numberOfOpenSites() / (n * n);

        return threshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);
        String summary = "mean                    = " + p.mean();
        summary += "\nstddev                  = " + p.stddev();
        summary += String.format("\n95%% confidence interval = [%f, %f]", p.confidenceLo(), p.confidenceHi());

        System.out.println(summary);
    }

}
