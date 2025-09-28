package algoritms.geometry;


import algoritms.metrics.DepthTracker;
import algoritms.metrics.Metrics;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    
    public static Result findClosest(Point[] points, Metrics m) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        Point[] ptsByX = Arrays.copyOf(points, points.length);
        Arrays.sort(ptsByX, Comparator.comparingDouble(Point::x));

        Point[] ptsByY = Arrays.copyOf(ptsByX, ptsByX.length);

        return closest(ptsByX, ptsByY, 0, ptsByX.length - 1, m);
    }

    private static Result closest(Point[] ptsByX, Point[] ptsByY, int lo, int hi, Metrics m) {
        try (DepthTracker d = new DepthTracker(m)) {
            int n = hi - lo + 1;
            if (n <= 3) {
                return bruteForce(ptsByX, lo, hi, m);
            }

            int mid = (lo + hi) / 2;
            Point midPoint = ptsByX[mid];

            Point[] leftY = new Point[mid - lo + 1];
            Point[] rightY = new Point[hi - mid];
            int l = 0, r = 0;

            for (Point p : ptsByY) {
                if (p.x() < midPoint.x() || (p.x() == midPoint.x() && belongsLeft(p, ptsByX, lo, mid))) {
                    leftY[l++] = p;
                } else {
                    rightY[r++] = p;
                }
            }

            Result leftRes = closest(ptsByX, leftY, lo, mid, m);
            Result rightRes = closest(ptsByX, rightY, mid + 1, hi, m);

            Result best = leftRes.dist() < rightRes.dist() ? leftRes : rightRes;
            double delta = best.dist();

            Point[] strip = new Point[n];
            int s = 0;
            for (Point p : ptsByY) {
                if (Math.abs(p.x() - midPoint.x()) < delta) {
                    strip[s++] = p;
                }
            }
            Arrays.sort(strip, 0, s, Comparator.comparingDouble(Point::y));

            Result stripRes = stripClosest(strip, s, delta, best, m);

            return stripRes.dist() < best.dist() ? stripRes : best;
        }
    }

    private static boolean belongsLeft(Point p, Point[] ptsByX, int lo, int mid) {
        for (int i = lo; i <= mid; i++) {
            if (ptsByX[i] == p) return true;
        }
        return false;
    }


    private static Result bruteForce(Point[] pts, int lo, int hi, Metrics m) {
        double minDist = Double.POSITIVE_INFINITY;
        Point best1 = null, best2 = null;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                if (m != null) m.incComparisons();
                double d = dist(pts[i], pts[j]);
                if (d < minDist) {
                    minDist = d;
                    best1 = pts[i];
                    best2 = pts[j];
                }
            }
        }
        return new Result(best1, best2, minDist);
    }

    private static Result stripClosest(Point[] strip, int n, double delta, Result best, Metrics m) {
        double minDist = best.dist();
        Point best1 = best.p1(), best2 = best.p2();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && (strip[j].y() - strip[i].y()) < minDist; j++) {
                if (m != null) m.incComparisons();
                double d = dist(strip[i], strip[j]);
                if (d < minDist) {
                    minDist = d;
                    best1 = strip[i];
                    best2 = strip[j];
                }
            }
        }
        return new Result(best1, best2, minDist);
    }

    private static double dist(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
