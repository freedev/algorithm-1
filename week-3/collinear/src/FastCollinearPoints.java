import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            } else {
                this.points[i] = points[i];
            }
        }
        if (this.points.length > 1) {
            Arrays.sort(this.points);
            for (int i = 1; i < this.points.length; i++) {
                if (this.points[i-1].compareTo(this.points[i]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        if (segments == null) {
            buildSegments(points);
        }
    }

    private void buildSegments(Point[] givenPoints) {
        if (segments == null) {
            List<LineSegment> lsegments = new LinkedList<>();
            for (int i = 0; i < givenPoints.length; i++) {
                Arrays.sort(this.points, givenPoints[i].slopeOrder());
                for (int j = 0; j < this.points.length-2; j++) {
                    if (givenPoints[i].compareTo(this.points[j]) != 0 &&
                            givenPoints[i].compareTo(this.points[j+1]) != 0 &&
                            givenPoints[i].compareTo(this.points[j+2]) != 0 
                            ) {
                        double s = givenPoints[i].slopeTo(this.points[j]);
                        if (s == givenPoints[i].slopeTo(this.points[j+1]) 
                                && s == givenPoints[i].slopeTo(this.points[j+2])) {
                                lsegments.add(new LineSegment(givenPoints[i], this.points[j+2]));
                            }
                        
                    }
                }
            }
            segments = new LineSegment[lsegments.size()];
            for (int i = 0; i < segments.length; i++) {
                segments[i] = lsegments.remove(0);
            }
        }
    }


    /**
     * 
     * @return
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * 
     * @return
     */
    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[segments.length];
        for (int i = 0; i < segments.length; i++) {
            s[i] = segments[i];
        }

        return s;
    }

}
