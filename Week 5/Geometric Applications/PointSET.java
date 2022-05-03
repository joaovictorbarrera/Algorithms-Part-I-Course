import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final TreeSet<Point2D> tree;

    // construct an empty set of points
    public PointSET() {
        this.tree = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        this.tree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        return this.tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : this.tree) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Null input");
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : this.tree) {
            if (rect.contains(p))
                q.enqueue(p);
        }
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        Point2D champion = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D x : this.tree) {
            double candidateDis = x.distanceSquaredTo(p);
            if (candidateDis < minDistance) {
                champion = x;
                minDistance = candidateDis;
            }
        }
        return champion;
    }
}
