import algoritms.metrics.CsvWriter;
import algoritms.metrics.Metrics;


public class Main {
    public static void main(String[] args) {
        Metrics m = new Metrics();
        long start = System.nanoTime();


        long end = System.nanoTime();
        try (CsvWriter csv = new CsvWriter("results.csv")) {
            csv.writeHeader();
            csv.writeRow("mergesort", 1, end - start, m);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}