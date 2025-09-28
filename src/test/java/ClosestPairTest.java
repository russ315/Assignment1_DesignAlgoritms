
import algoritms.geometry.Result;
import algoritms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import algoritms.geometry.Point;
import algoritms.geometry.ClosestPair;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    @Test
    void testSimple() {
        Point[] pts = {
                new Point(0,0),
                new Point(3,4),
                new Point(1,1)
        };
        Metrics m = new Metrics();
        Result res = ClosestPair.findClosest(pts, m);
        assertEquals(Math.sqrt(2), res.dist(), 1e-9);
    }

    @Test
    void testRandomSmallVsBrute() {
        Random rnd = new Random();
        for (int t = 0; t < 20; t++) {
            int n = 50;
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(rnd.nextDouble()*1000, rnd.nextDouble()*1000);
            }

            Result fast = ClosestPair.findClosest(pts, new Metrics());
            Result slow = bruteForceCheck(pts);

            assertEquals(slow.dist(), fast.dist(), 1e-9);
        }
    }

    @Test
    void testTwoPoints() {
        Point[] pts = {
                new Point(1,2),
                new Point(4,6)
        };
        Result res = ClosestPair.findClosest(pts, new Metrics());
        assertEquals(5.0, res.dist(), 1e-9);
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosest(new Point[]{}, null));
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosest(null, null));
    }

    private Result bruteForceCheck(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        Point a=null, b=null;
        for (int i=0;i<pts.length;i++) {
            for (int j=i+1;j<pts.length;j++) {
                double dx = pts[i].x()-pts[j].x();
                double dy = pts[i].y()-pts[j].y();
                double d = Math.sqrt(dx*dx+dy*dy);
                if (d < best) {
                    best = d; a=pts[i]; b=pts[j];
                }
            }
        }
        return new Result(a,b,best);
    }
}
