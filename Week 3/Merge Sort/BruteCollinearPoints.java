import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> collinearSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Can't be created with a null array");
        checkNull(points);

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicates(sortedPoints);
        bruteForce(sortedPoints);
    }

    private void bruteForce(Point[] points) {

        for (int first = 0; first < points.length - 3; first++) {
            for (int second = first + 1; second < points.length - 2; second++) {
                double firstSecondSlope = points[first].slopeTo(points[second]);
                for (int third = second + 1; third < points.length - 1; third++) {
                    double secondThirdSlope = points[second].slopeTo(points[third]);
                    if (firstSecondSlope == secondThirdSlope) {
                        for (int fourth = third + 1; fourth < points.length; fourth++) {
                            double thirdFourthSlope = points[third].slopeTo(points[fourth]);
                            if (secondThirdSlope == thirdFourthSlope) {
                                collinearSegments.add(new LineSegment(points[first], points[fourth]));
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Array has null values");
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Array has duplicate points");
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.collinearSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return this.collinearSegments.toArray(new LineSegment[] {});
    }
}
