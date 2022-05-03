import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> collinearSegments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Can't be created with a null array");
        checkNull(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicates(sortedPoints);
        findCollinearPoints(sortedPoints);
    }

    private void findCollinearPoints(Point[] points) {

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points);
            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());
            
            int last = 2;
            while (last < points.length) {
                // 0 12 52 3 3 4 1 1 1 1 1 9 8 8
                // find last collinear to p point
                int counter = 1;
                while (last < points.length
                        && Double.compare(p.slopeTo(points[last - counter]), p.slopeTo(points[last])) == 0) {
                    counter++;
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                // (p is not bigger than the first point found, not inverse order)
                if (counter >= 3 && p.compareTo(points[last - counter]) < 0) {
                    collinearSegments.add(new LineSegment(p, points[last - 1]));
                }
                last++;
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