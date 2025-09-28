import algoritms.metrics.Metrics;
import algoritms.sort.ISort;
import algoritms.sort.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void testSmallArray() {
        int[] arr = {5, 3, 1, 4, 2};
        Metrics m = new Metrics();
        ISort sortAlgoritm = new QuickSort();
        sortAlgoritm.sort(arr, m);
        assertArrayEquals(new int[]{1,2,3,4,5}, arr);
        assertTrue(m.getComparisons() > 0);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1,2,3,4,5,6,7};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Metrics m = new Metrics();
        ISort sortAlgoritm = new QuickSort();
        sortAlgoritm.sort(arr, m);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {9,8,7,6,5,4,3,2,1};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        Metrics m = new Metrics();
        ISort sortAlgoritm = new QuickSort();
        sortAlgoritm.sort(arr, m);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRandomLargeArray() {
        int n = 50_000;
        int[] arr = new Random(123).ints(n, -1_000_000, 1_000_000).toArray();
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        Metrics m = new Metrics();
        ISort sortAlgoritm = new QuickSort();
        sortAlgoritm.sort(arr, m);
        assertArrayEquals(expected, arr);
        assertTrue(m.getMaxDepth() <= 2 * (int)(Math.log(n) / Math.log(2)) + 10,
                "Depth should be ~O(log n)");
    }

    @Test
    void testCutoffInsertionSort() {
        int[] arr = {3, 1, 2};
        Metrics m = new Metrics();
        ISort sortAlgoritm = new QuickSort();
        sortAlgoritm.sort(arr, m);
        assertArrayEquals(new int[]{1,2,3}, arr);
    }
}
