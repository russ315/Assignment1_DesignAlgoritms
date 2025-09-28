package tests;

import algoritms.metrics.Metrics;
import algoritms.util.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilsTest {

    @Test
    void testSwap() {
        int[] arr = {1,2,3};
        Metrics m = new Metrics();
        ArrayUtils.swap(arr, 0, 2, m);
        assertArrayEquals(new int[]{3,2,1}, arr);
        assertEquals(1, m.getSwaps());
    }

    @Test
    void testPartition() {
        int[] arr = {5,4,3,2,1};
        Metrics m = new Metrics();
        int p = ArrayUtils.partition(arr, 0, arr.length-1, m);
        Arrays.sort(arr);
        assertArrayEquals(new int[]{1,2,3,4,5}, arr);
        assertTrue(p >= 0 && p < arr.length);
    }

    @Test
    void testShuffle() {
        int[] arr = {1,2,3,4,5,6,7,8,9};
        int[] copy = Arrays.copyOf(arr, arr.length);
        ArrayUtils.shuffle(arr);
        assertEquals(copy.length, arr.length);
        assertEquals(Arrays.stream(copy).sum(), Arrays.stream(arr).sum());
        assertNotEquals(Arrays.toString(copy), Arrays.toString(arr));
    }

    @Test
    void testGuards() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.requireNotNull(null));
        int[] arr = {1,2,3};
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.requireBounds(arr, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.requireBounds(arr, 0, 3));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.requireBounds(arr, 2, 1));
    }
}
