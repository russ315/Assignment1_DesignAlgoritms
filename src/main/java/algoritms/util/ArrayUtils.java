package algoritms.util;

import algoritms.metrics.Metrics;

import java.util.Random;

public final class ArrayUtils {
    private static final Random RAND = new Random();

    private ArrayUtils() {}

    public static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        if (m != null) m.incSwaps();
    }

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RAND.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivotIndex = lo + RAND.nextInt(hi - lo + 1);
        swap(a, lo, pivotIndex, m);
        int pivot = a[lo];

        int i = lo + 1;
        int j = hi;

        while (true) {
            while (i <= hi) {
                if (m != null) m.incComparisons();
                if (a[i] >= pivot) break;
                i++;
            }
            while (j > lo) {
                if (m != null) m.incComparisons();
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

    public static void requireNotNull(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
    }

    public static void requireBounds(int[] a, int lo, int hi) {
        requireNotNull(a);
        if (lo < 0 || hi >= a.length || lo > hi) {
            throw new IllegalArgumentException("Invalid bounds: lo=" + lo + " hi=" + hi);
        }
    }
}
