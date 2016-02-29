import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author freedev
 *
 */
public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    /**
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
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
    }

    private void findSegments(Point[] arr, int len, int startPosition,
            Point[] result, List<LineSegment> list) {
        if (len == 0) {
            double s = result[0].slopeTo(result[1]);
            if (s == result[0].slopeTo(result[2]) && s == result[0].slopeTo(result[3]) && 
                s == result[1].slopeTo(result[2]) && s == result[1].slopeTo(result[3]) && 
                s == result[2].slopeTo(result[3])) {
//                Arrays.sort(result);
                list.add(new LineSegment(result[0], result[3]));
            }
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
            Point[] checkSegment = new Point[4];
            List<LineSegment> lsegments = new LinkedList<>();
            findSegments(points, checkSegment.length, 0, checkSegment, lsegments);
            segments = new LineSegment[lsegments.size()];
            for (int i = 0; i < segments.length; i++) {
                segments[i] = lsegments.remove(0);
            }
        }
        return segments;
    }

}
