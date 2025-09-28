package algoritms.sort;


import algoritms.metrics.DepthTracker;
import algoritms.metrics.Metrics;

public class MergeSort implements ISort {
    private static final int CUTOFF = 32;

    public void sort(int[] a, Metrics m) {
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buf, int lo, int hi, Metrics m) {
        if (hi - lo + 1 <= CUTOFF) {
            InsertionSort.sort(a, lo, hi, m);
            return;
        }
        try (DepthTracker d = new DepthTracker(m)) {
            int mid = lo + (hi - lo) / 2;
            sort(a, buf, lo, mid, m);
            sort(a, buf, mid + 1, hi, m);
            merge(a, buf, lo, mid, hi, m);
        }
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi, Metrics m) {
        m.incAllocations();

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            m.incComparisons();
            if (a[i] <= a[j]) {
                buf[k++] = a[i++];
            } else {
                buf[k++] = a[j++];
            }
            m.incSwaps();
        }
        while (i <= mid) {
            buf[k++] = a[i++];
            m.incSwaps();
        }
        while (j <= hi) {
            buf[k++] = a[j++];
            m.incSwaps();
        }
        for (int t = lo; t <= hi; t++) {
            a[t] = buf[t];
        }
    }
}
