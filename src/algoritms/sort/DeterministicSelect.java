package algoritms.sort;


import algoritms.metrics.DepthTracker;
import algoritms.metrics.Metrics;
import algoritms.util.ArrayUtils;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] a, int k, Metrics m) {
        ArrayUtils.requireNotNull(a);
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k out of bounds: " + k);
        }
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            try (DepthTracker d = new DepthTracker(m)) {
                if (lo == hi) return a[lo];

                int pivotIndex = medianOfMedians(a, lo, hi, m);
                int p = partitionAroundPivot(a, lo, hi, pivotIndex, m);

                if (k == p) {
                    return a[k];
                } else if (k < p) {
                    hi = p - 1;
                } else {
                    lo = p + 1;
                }
            }
        }
    }

    private static int partitionAroundPivot(int[] a, int lo, int hi, int pivotIndex, Metrics m) {
        ArrayUtils.swap(a, pivotIndex, hi, m);
        int pivot = a[hi];
        int store = lo;

        for (int i = lo; i < hi; i++) {
            if (m != null) m.incComparisons();
            if (a[i] < pivot) {
                ArrayUtils.swap(a, i, store, m);
                store++;
            }
        }
        ArrayUtils.swap(a, store, hi, m);
        return store;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            Arrays.sort(a, lo, hi + 1);
            return lo + n / 2;
        }

        int numMedians = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            Arrays.sort(a, i, subHi + 1);
            int median = i + (subHi - i) / 2;
            ArrayUtils.swap(a, lo + numMedians, median, m);
            numMedians++;
        }

        return medianOfMedians(a, lo, lo + numMedians - 1, m);
    }
}
