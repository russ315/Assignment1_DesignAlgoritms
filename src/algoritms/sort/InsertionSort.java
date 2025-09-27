package algoritms.sort;

import algoritms.metrics.Metrics;

public class InsertionSort {
    public static void sort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.incComparisons();
                if (a[j] <= key) break;
                a[j + 1] = a[j];
                m.incSwaps();
                j--;
            }
            a[j + 1] = key;
        }
    }
}
