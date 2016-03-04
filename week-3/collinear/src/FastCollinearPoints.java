import java.util.Arrays;
import java.util.TreeSet;

/**
 * 
 * @author freedev
 *
 */
public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    private class PairOfPoints implements Comparable<PairOfPoints> {
        private Point start;
        private Point end;
        public PairOfPoints(Point s, Point e) {
            this.start = s;
            this.end = e;
        }
        public int compare(PairOfPoints o1, PairOfPoints o2) {
            // TODO Auto-generated method stub
            if (o1.start.compareTo(o2.start) == 0)
                return o1.end.compareTo(o2.end);
            else
                return o1.start.compareTo(o2.start);
        }
        @Override
        public int compareTo(PairOfPoints o) {
            // TODO Auto-generated method stub
            if (this.start.compareTo(o.start) == 0)
                return this.end.compareTo(o.end);
            else
                return this.start.compareTo(o.start);
        }
    }
    
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
                if (this.points[i - 1].compareTo(this.points[i]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        if (segments == null) {
            buildSegments(points);
        }
    }

    private void buildSegments(Point[] givenPoints) {
        if (segments == null) {
            TreeSet<PairOfPoints> segmentSet = new TreeSet<>();
            for (int i = 0; i < givenPoints.length; i++) {
                Arrays.sort(this.points, givenPoints[i].slopeOrder());
                // StdOut.println("-");
                // for (int j = 0; j < this.points.length; j++) {
                // StdOut.print(this.points[j]);
                // StdOut.print("-");
                // }
                // StdOut.println("-");
                int j = 1;
                while (j < (this.points.length - 2)) {
                    double s = this.points[0].slopeTo(this.points[j]);
                    int k = 0;
                    while ((j + k) < this.points.length - 1) {
                        if (s != this.points[0]
                                .slopeTo(this.points[j + k + 1])) {
                            break;
                        }
                        k++;
                    }
                    if (k > 1) {
                        Point[] result = new Point[k + 2];
                        result[0] = this.points[0];
                        for (int jj = 0; jj < k + 1; jj++) {
                            result[jj + 1] = this.points[j + jj];
                        }
                        j = j + k + 1;
                        Arrays.sort(result);
                        PairOfPoints l = new PairOfPoints(result[0], result[result.length-1]);
                        if (!segmentSet.contains(l)) {
                            segmentSet.add(l);
                        }
                    }
                    j++;
                 }
            }
            segments = new LineSegment[segmentSet.size()];
            int i = 0;
            for (PairOfPoints pp : segmentSet) {
                segments[i] = new LineSegment(pp.start, pp.end);
                i++;
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
