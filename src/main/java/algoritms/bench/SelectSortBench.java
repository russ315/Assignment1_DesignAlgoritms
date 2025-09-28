package algoritms.bench;

import algoritms.metrics.Metrics;
import algoritms.sort.DeterministicSelect;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgsAppend = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 8, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class SelectSortBench {

    @Param({"1000", "5000", "20000", "100000"})
    public int n;

    private int[] base;
    private Random rnd;

    @Setup(Level.Invocation)
    public void setUpInvocation() {
        rnd = new Random(123);
        base = rnd.ints(n, 0, Integer.MAX_VALUE).toArray();
    }

    @Benchmark
    public int deterministicSelect() {
        int[] a = Arrays.copyOf(base, base.length);
        Metrics m = new Metrics();
        int k = n / 2;
        return DeterministicSelect.select(a, k, m);
    }

    @Benchmark
    public int arraysSortMedian() {
        int[] a = Arrays.copyOf(base, base.length);
        Arrays.sort(a);
        return a[n / 2];
    }
}
