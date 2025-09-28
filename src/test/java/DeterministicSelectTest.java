import algoritms.metrics.Metrics;
import algoritms.sort.DeterministicSelect;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void testSimpleArray() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2};
        Metrics m = new Metrics();

        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);

        for (int k = 0; k < arr.length; k++) {
            int sel = DeterministicSelect.select(Arrays.copyOf(arr, arr.length), k, m);
            assertEquals(sorted[k], sel, "k=" + k);
        }

    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(42);
        for (int t = 0; t < 50; t++) {
            int n = 50 + rnd.nextInt(100);
            int[] arr = rnd.ints(n, 0, 1000).toArray();

            int[] sorted = Arrays.copyOf(arr, arr.length);
            Arrays.sort(sorted);

            for (int k = 0; k < 5; k++) {
                int idx = rnd.nextInt(n);
                int sel = DeterministicSelect.select(Arrays.copyOf(arr, arr.length), idx, new Metrics());
                assertEquals(sorted[idx], sel);
            }
        }
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        Metrics m = new Metrics();
        assertEquals(42, DeterministicSelect.select(arr, 0, m));
    }

    @Test
    void testInvalidK() {
        int[] arr = {1,2,3};
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, -1, null));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 3, null));
    }
}
