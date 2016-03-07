import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        @Override
        public int compareTo(PairOfPoints o) {
            // TODO Auto-generated method stub
            int startCompare = this.start.compareTo(o.start);
            if (startCompare == 0)
                return this.end.compareTo(o.end);
            return startCompare;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PairOfPoints other = (PairOfPoints) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (end == null) {
                if (other.end != null)
                    return false;
            } else if (end.compareTo(other.end) != 0)
                return false;
            if (start == null) {
                if (other.start != null)
                    return false;
            } else if (start.compareTo(other.start) != 0)
                return false;
            return true;
        }

        private FastCollinearPoints getOuterType() {
            return FastCollinearPoints.this;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
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
            List<PairOfPoints> segmentArray = new ArrayList<>();
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
                        PairOfPoints l = new PairOfPoints(result[0],
                                result[result.length - 1]);
                        segmentArray.add(l);
                    }
                    j++;
                }
            }
            Collections.sort(segmentArray);
            LineSegment[] segmentsTmp = new LineSegment[segmentArray.size()];
            int counter = 0;
            int pos = 0;
            if (segmentArray.size() > 0) {
                segmentsTmp[counter] = new LineSegment(
                        segmentArray.get(0).start, segmentArray.get(0).end);
                counter++;
            }
            for (int i = 1; i < segmentArray.size(); i++) {
                if (!segmentArray.get(pos).equals(segmentArray.get(i))) {
                    pos = i;
                    segmentsTmp[counter] = new LineSegment(
                            segmentArray.get(i).start, segmentArray.get(i).end);
                    counter++;
                }
            }
            segments = new LineSegment[counter];
            for (int i = 0; i < counter; i++) {
                segments[i] = segmentsTmp[i];
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
