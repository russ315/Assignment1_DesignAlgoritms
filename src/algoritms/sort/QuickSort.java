package algoritms.sort;

import algoritms.metrics.DepthTracker;
import algoritms.metrics.Metrics;

import java.util.Random;

public class QuickSort {
    private static final int CUTOFF = 32;
    private static final Random RAND = new Random();

    public static void sort(int[] a, Metrics m) {
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                InsertionSort.sort(a, lo, hi, m);
                return;
            }

            try (DepthTracker d = new DepthTracker(m)) {
                int pivotIndex = lo + RAND.nextInt(hi - lo + 1);
                swap(a, lo, pivotIndex, m);

                int p = partition(a, lo, hi, m);

                int leftSize = p - lo;
                int rightSize = hi - p;

                if (leftSize < rightSize) {
                    sort(a, lo, p - 1, m);
                    lo = p + 1; // iterate on right
                } else {
                    sort(a, p + 1, hi, m);
                    hi = p - 1; // iterate on left
                }
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivot = a[lo];
        int i = lo + 1;
        int j = hi;

        while (true) {
            while (i <= hi) {
                m.incComparisons();
                if (a[i] >= pivot) break;
                i++;
            }
            while (j > lo) {
                m.incComparisons();
                if (a[j] <= pivot) break;
                j--;
            }
            if (i >= j) break;
            swap(a, i, j, m);
            i++;
            j--;
        }
        swap(a, lo, j, m);
        return j;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        m.incSwaps();
    }
}
