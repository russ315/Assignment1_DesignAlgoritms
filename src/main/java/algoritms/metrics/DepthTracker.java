package algoritms.metrics;


public class DepthTracker implements AutoCloseable {
    private final Metrics metrics;

    public DepthTracker(Metrics metrics) {
        this.metrics = metrics;
        this.metrics.enterRecursion();
    }

    @Override
    public void close() {
        metrics.exitRecursion();
    }
}
