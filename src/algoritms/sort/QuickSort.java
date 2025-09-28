package algoritms.sort;

import algoritms.metrics.DepthTracker;
import algoritms.metrics.Metrics;
import algoritms.util.ArrayUtils;

public class QuickSort {
    private static final int CUTOFF = 32;

    public static void sort(int[] a, Metrics m) {
        ArrayUtils.requireNotNull(a);
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                InsertionSort.sort(a, lo, hi, m);
                return;
            }

            try (DepthTracker d = new DepthTracker(m)) {
                int p = ArrayUtils.partition(a, lo, hi, m);

                int leftSize = p - lo;
                int rightSize = hi - p;

                if (leftSize < rightSize) {
                    sort(a, lo, p - 1, m);
                    lo = p + 1;
                } else {
                    sort(a, p + 1, hi, m);
                    hi = p - 1;
                }
            }
        }
    }
}
