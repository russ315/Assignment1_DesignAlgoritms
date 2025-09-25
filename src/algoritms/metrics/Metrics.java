package algoritms.metrics;

public class Metrics {
    private long comparisons;
    private long swaps;
    private long allocations;

    private long currentDepth;
    private long maxDepth;

    public void reset() {
        comparisons = swaps = allocations = 0;
        currentDepth = maxDepth = 0;
    }

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }
    public void incAllocations() { allocations++; }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        if (currentDepth > 0) currentDepth--;
    }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAllocations() { return allocations; }
    public long getMaxDepth() { return maxDepth; }
}