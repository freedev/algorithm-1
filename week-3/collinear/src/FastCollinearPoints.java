import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author freedev
 *
 */
public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    /**
     * 
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        Set<String> setPoints = new TreeSet<>();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            } else {
                this.points[i] = points[i];
                if (setPoints.contains(points[i].toString()))
                    throw new java.lang.IllegalArgumentException();
                else
                    setPoints.add(points[i].toString());
            }
        }
        Arrays.sort(this.points);
    }

    private void findSegments(Point[] arr, int len, int startPosition,
            Point[] result, List<LineSegment> list) {
        if (len == 0) {
//            if (Point.areCollinear(result[0], result[1], result[2], result[3])) {
//                Arrays.sort(result);
//                list.add(new LineSegment(result[0], result[3]));
//            }
            return;
        }
        for (int i = startPosition; i <= arr.length - len; i++) {
            result[result.length - len] = arr[i];
            findSegments(arr, len - 1, i + 1, result, list);
        }
    }
    
    /**
     * 
     * @return
     */
    public int numberOfSegments() {
        if (segments != null)
            return segments.length;
        return 0;
    }

    /**
     * 
     * @return
     */
    public LineSegment[] segments() {
        if (segments == null) {
            List<LineSegment> lsegments = new LinkedList<>();
            for (int i = 0; i < (points.length-3); i++) {
//                if (Point.areCollinear(points[i], points[i+1], points[i+2], points[i+3]))
//                    lsegments.add(new LineSegment(points[i], points[i+3]));
            }
            segments = new LineSegment[lsegments.size()];
            for (int i = 0; i < segments.length; i++) {
                segments[i] = lsegments.remove(0);
            }
        }
        return segments;
    }

}
