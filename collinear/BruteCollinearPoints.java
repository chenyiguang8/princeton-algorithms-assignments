
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
    private int num;

    /** 
     * finds all the line segments containing 4 points.
     * Throw a java.lang.NullPointerException either the argument to the 
     * constructor is null or if any point in the array is null. Throw a 
     * java.lang.IllegalArgumentException if the argument to the constructor 
     * contains a repeated point. 
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null || containsNull(points)) 
            throw new NullPointerException();
        if (containsRepeated(points)) throw new IllegalArgumentException();
        // avoid mutating the argument. so copy the points array.
        Point[] temp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            temp[i] = points[i];
        }

        Arrays.sort(temp);
        for (int i = 0; i < temp.length; i++) {
            Point a = temp[i];
            for (int j = i + 1; j < temp.length; j++) {
                double slope1 = a.slopeTo(temp[j]);
                for (int k = j + 1; k < temp.length; k++) {
                     double slope2 = a.slopeTo(temp[k]);
                    for (int p = k + 1; p < temp.length; p++) {
                        double slope3 = a.slopeTo(temp[p]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            LineSegment segment = new LineSegment(a, temp[p]);
                            segmentList.add(segment);
                            num++;
                        }
                    }
                }
            }
        }
    }

    /**
     * check to see whether the array contains null.
     * @param the points array.
     */
    private boolean containsNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return true;
        }
        return false;
    }

    /**
     * check to see whether the points array contains repeated points
     * @param the points array.
     */
    private boolean containsRepeated(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) 
                    return true;
            }
        }
        return false;
    }
    /** 
     * return the number of the line segments containing 4 points.
     */
    public int numberOfSegments() {
        return num;
    }

    /** 
     * create an lineSegment array and copy all the elements from the
     * segmentlist.
     * return the lineSegment array containing 4 points
     */
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[num];
        for (int i = 0; i < num; i++) {
            segments[i] = segmentList.get(i);
        }
        return segments;
    }
}