import algoritms.sort.MergeSort;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;
import algoritms.metrics.Metrics;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

class MergeSortTest {

    private MergeSort mergeSort;
    private Metrics metrics;

    @BeforeEach
    void setup() {
        mergeSort = new MergeSort();
        metrics.reset();
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testRandomArray() {
        int[] arr = {7, 3, 9, 1, 5};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, arr);
    }

    @Test
    void testDuplicates() {
        int[] arr = {4, 2, 2, 4, 1};
        mergeSort.sort(arr,metrics);
        assertArrayEquals(new int[]{1, 2, 2, 4, 4}, arr);
    }

    @Test
    void testLargeRandomArray() {
        int n = 10000;
        int[] arr = new Random(42).ints(n, -100000, 100000).toArray();
        int[] expected = Arrays.copyOf(arr, arr.length);

        mergeSort.sort(arr,metrics);
        Arrays.sort(expected);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testRecursionDepthBounded() {
        int n = 1 << 12; // 4096
        int[] arr = new Random(1).ints(n, 0, 10000).toArray();

        mergeSort.sort(arr,metrics);

        long depth = metrics.getMaxDepth();
        assertTrue(depth <= 20, "Depth too large: " + depth);
    }

    @Test
    void testMetricsCounts() {
        int[] arr = {3, 1, 2};
        mergeSort.sort(arr,metrics);
        assertTrue(metrics.getComparisons() > 0, "Comparisons must be counted");
        assertTrue(metrics.getAllocations() >= 0, "Allocations metric must be valid");
    }
}
