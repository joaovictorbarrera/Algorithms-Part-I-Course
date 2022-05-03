import java.util.Arrays;

public class IntersectionOfTwoSets {
    static class Point2D implements Comparable<Point2D> {
        private final double x;
        private final double y;
        
        Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double getX() {
            return this.x;
        }
        
        public double getY() {
            return this.y;
        }

        @Override
        public int compareTo(Point2D other) {
            if (this.getX() > other.getX()) return 1;
            if (other.getX() > this.getX()) return -1;
            if (this.getY() > other.getY()) return 1;
            if (other.getY() > this.getY()) return -1;
            
            return 0;
        }
        
        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }
     }

    public static int countCommonPoints(Point2D[] arr1, Point2D[] arr2) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int count = 0;
        int j = 0;
        int i = 0;
        while(i < arr1.length && j < arr2.length) {
            if(arr1[i].compareTo(arr2[j]) == 1) j++;
            else if(arr2[j].compareTo(arr1[i]) == 1) i++;
            else {
                count++;
                i++;
                j++;
            }
        }
        
        return count;
    }
   
    public static void main(String[] args) {
        Point2D[] arr1 = {new Point2D(1.1, 2.0), new Point2D(2, 5), new Point2D(6, 9), new Point2D(0,0), 
                new Point2D(0,0), new Point2D(2,3), new Point2D(0,0)};
        Point2D[] arr2 = {new Point2D(1.5, 2.0), new Point2D(6, 5), new Point2D(6, 9), 
                new Point2D(0,0), new Point2D(0,0), new Point2D(0,0), new Point2D(2,3)};
        System.out.println(countCommonPoints(arr1, arr2));
    }
}
