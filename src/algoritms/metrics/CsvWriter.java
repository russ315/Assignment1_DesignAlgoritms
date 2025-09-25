package algoritms.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter implements AutoCloseable {
    private final PrintWriter writer;
    private boolean headerWritten = false;

    public CsvWriter(String path) throws IOException {
        this.writer = new PrintWriter(new FileWriter(path, true)); // append mode
    }

    public void writeHeader() {
        if (!headerWritten) {
            writer.println("algo,n,time_ns,comparisons,swaps,allocations,maxDepth");
            headerWritten = true;
            writer.flush();
        }
    }

    public void writeRow(String algo, int n, long timeNs, Metrics m) {
        writer.printf("%s,%d,%d,%d,%d,%d,%d%n",
                algo,
                n,
                timeNs,
                m.getComparisons(),
                m.getSwaps(),
                m.getAllocations(),
                m.getMaxDepth()
        );
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}
