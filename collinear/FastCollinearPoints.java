
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class FastCollinearPoints {
    private static final int MINI_SUBSET = 4;
    private int num;
    private ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();

    /**
     * constructor. finds all the line segments containing 4 or more points.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null || containsNull(points)) 
            throw new NullPointerException();
        if (containsRepeated(points)) throw new IllegalArgumentException();
        int numOfPoints = points.length;
        // avoid mutating the argument. so copy the points array.
        Point[] temp = new Point[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            temp[i] = points[i];
        }
        // sort the array according to the natural order.
        Arrays.sort(temp);
        
        for (int i = 0; i < numOfPoints - MINI_SUBSET + 1; i++) {
            Point currentPoint = temp[i];
            Comparator<Point> c = currentPoint.slopeOrder();
            Point[] copy = new Point[numOfPoints - i - 1];
            for (int j = 0; j < copy.length; j++) {
                copy[j] = temp[j + i + 1];
            }

            Arrays.sort(copy, c);
            Point[] endPoints = findEndPoints(copy, currentPoint);
            
            for (int k = 0; k < endPoints.length; k++) {
                boolean isSub = false;
                for (int p = 0; p < i; p++) {
                    if (isSubsegment(temp[p], currentPoint, endPoints[k])) {
                        isSub = true;
                        break;
                    }
                }
                if (!isSub) {
                    addLineSegment(currentPoint, endPoints[k]);
                }
            }
        }
    }

        /**
         * create a line segment and add it to the segmentList.
         * and increment the num.
         * @param point1.
         * @param point2.
         */
        private void addLineSegment(Point point1, Point point2) {
            LineSegment segment = new LineSegment(point1, point2);
            segmentList.add(segment);
            num++;
        }

        /**
         * check to see whether the given 3 points is in the same line, 
         * and use that to determine whether there is a subsegment.
         * @param point1.
         * @param point2.
         * @param endPoint.
         * @return true if the slope between point1 and endpoint is the same
         * as the slope between point2 and endpoint.
         */
       private boolean isSubsegment(Point point1,Point point2,Point endPoint) {
            return (point1.slopeTo(endPoint) == point2.slopeTo(endPoint));
        }

        /**
         * get the endPoint in the array.
         * @param the array.
         * @param the point.
         * @return the array containing all the endpoints.
         */
        private Point[] findEndPoints(Point[] points, Point point) {
            double slope1 = points[0].slopeTo(point);
            ArrayList<Point> pointList = new ArrayList<Point>();
            Point[] endPoints;
            int count = 1;

            for (int i = 1; i < points.length; i++) {
                double slope2 = points[i].slopeTo(point);
                if (slope2 == slope1) {
                    count++;
                } else {
                    if (count >= MINI_SUBSET - 1) {
                        pointList.add(points[i - 1]);
                    }
                    count = 1;
                    slope1 = slope2;
                }
            }
            if (count >= MINI_SUBSET - 1) pointList.add(points[points.length - 1]);
            endPoints = new Point[pointList.size()];
            for (int i = 0; i < pointList.size(); i++) {
                endPoints[i] = pointList.get(i);
            }
            return endPoints;
        }

        /**
         * return the number of segments.
         */
        public int numberOfSegments() {
            return num;
        }

        /**
         * create an array and copy all the points from segmentList
         * to the new array.
         * @return the linesegment array.
         */
        public LineSegment[] segments() {
            LineSegment[] segments = new LineSegment[num];
            for (int i = 0; i < num; i++) {
                segments[i] = segmentList.get(i);
            }
            return segments;
        }

        /**
         * check to see whether the array contains null.
         * @param points array.
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
}