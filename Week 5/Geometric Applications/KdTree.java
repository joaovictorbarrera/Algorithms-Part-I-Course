import java.util.Comparator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int WINDOW_MAX_HEIGHT = 1;
    private static final int WINDOW_MIN_HEIGHT = 0;
    private static final int WINDOW_MAX_WIDTH = 1;
    private static final int WINDOW_MIN_WIDTH = 0;
    private Node root = null;
    private int size = 0;
    private double minDistance;

    private class Node {
        private final Point2D p;
        private Node left = null, right = null;
        Node(Point2D p) {
            this.p = p;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        if (root == null) {
            root = new Node(p);
            size++;
        } else if (!contains(p)) {
            insert(root, p, true);
            size++;
        }
    }

    private void insert(Node curr, Point2D p, boolean useX) {
        Comparator<Point2D> cmp = useX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmp.compare(p, curr.p) <= 0) {
            if (curr.left == null) {
                curr.left = new Node(p);
            } else {
                insert(curr.left, p, !useX);
            }
        } else {
            if (curr.right == null) {
                curr.right = new Node(p);
            } else {
                insert(curr.right, p, !useX);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        return contains(root, p, true);
    }

    private boolean contains(Node curr, Point2D p, boolean useX) {
        if (curr == null)
            return false;
        if (curr.p.equals(p))
            return true;

        Comparator<Point2D> cmp = useX ? Point2D.X_ORDER : Point2D.Y_ORDER;

        if (cmp.compare(p, curr.p) > 0)
            return contains(curr.right, p, !useX);
        else
            return contains(curr.left, p, !useX);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, new RectHV(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT, WINDOW_MAX_WIDTH, WINDOW_MAX_HEIGHT), true);
    }

    private void draw(Node curr, RectHV area, boolean useX) {
        if (curr == null)
            return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        curr.p.draw();
        StdDraw.setPenRadius();
        if (useX) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(curr.p.x(), area.ymin(), curr.p.x(), area.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(area.xmin(), curr.p.y(), area.xmax(), curr.p.y());
        }
        draw(curr.left, useX ? new RectHV(area.xmin(), area.ymin(), curr.p.x(), area.ymax())
                : new RectHV(area.xmin(), area.ymin(), area.xmax(), curr.p.y()), !useX);
        draw(curr.right, useX ? new RectHV(curr.p.x(), area.ymin(), area.xmax(), area.ymax())
                : new RectHV(area.xmin(), curr.p.y(), area.xmax(), area.ymax()), !useX);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Null input");
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q, true);
        return q;
    }

    private void range(Node curr, RectHV rect, Queue<Point2D> q, boolean useX) {
        if (curr == null)
            return;

        if (rect.contains(curr.p))
            q.enqueue(curr.p);

        if (useX) { // curr is vertical
            if (rect.xmin() < curr.p.x() && rect.xmax() < curr.p.x()) { // to the left
                range(curr.left, rect, q, !useX);
            } else if (rect.xmin() > curr.p.x() && rect.xmax() > curr.p.x()) { // to the right
                range(curr.right, rect, q, !useX);
            } else { // both sides
                range(curr.left, rect, q, !useX);
                range(curr.right, rect, q, !useX);
            }
        } else { // curr is horizontal
            if (rect.ymin() < curr.p.y() && rect.ymax() < curr.p.y()) { // to the left
                range(curr.left, rect, q, !useX);
            } else if (rect.ymin() > curr.p.y() && rect.ymax() > curr.p.y()) { // to the right
                range(curr.right, rect, q, !useX);
            } else { // both sides
                range(curr.left, rect, q, !useX);
                range(curr.right, rect, q, !useX);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null input");
        if (isEmpty())
            return null;
        this.minDistance = Double.POSITIVE_INFINITY;
        return nearest(root, root.p, p,
                new RectHV(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT, WINDOW_MAX_WIDTH, WINDOW_MAX_HEIGHT), true);
    }

    private Point2D nearest(Node curr, Point2D currMin, Point2D p, RectHV nodeArea, boolean useX) {
        if (curr == null)
            return currMin;
        double distance = curr.p.distanceSquaredTo(p);
        if (distance < minDistance) {
            minDistance = distance;
            currMin = curr.p;
        }
        Comparator<Point2D> cmp = useX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmp.compare(p, curr.p) > 0) {
            currMin = nearest(curr.right, currMin, p,
                    useX ? new RectHV(curr.p.x(), nodeArea.ymin(), nodeArea.xmax(), nodeArea.ymax())
                            : new RectHV(nodeArea.xmin(), curr.p.y(), nodeArea.xmax(), nodeArea.ymax()), !useX);
            RectHV leftRec = useX ? new RectHV(nodeArea.xmin(), nodeArea.ymin(), curr.p.x(), nodeArea.ymax())
                    : new RectHV(nodeArea.xmin(), nodeArea.ymin(), nodeArea.xmax(), curr.p.y());
            if (leftRec.distanceSquaredTo(p) < minDistance) {
                currMin = nearest(curr.left, currMin, p, leftRec, !useX);
            }
        } else {
            currMin = nearest(curr.left, currMin, p,
                    useX ? new RectHV(nodeArea.xmin(), nodeArea.ymin(), curr.p.x(), nodeArea.ymax())
                            : new RectHV(nodeArea.xmin(), nodeArea.ymin(), nodeArea.xmax(), curr.p.y()), !useX);
            RectHV rightRec = useX ? new RectHV(curr.p.x(), nodeArea.ymin(), nodeArea.xmax(), nodeArea.ymax())
                    : new RectHV(nodeArea.xmin(), curr.p.y(), nodeArea.xmax(), nodeArea.ymax());
            if (rightRec.distanceSquaredTo(p) < minDistance) {
                currMin = nearest(curr.right, currMin, p, rightRec, !useX);
            }
        }

        return currMin;
    }
}
