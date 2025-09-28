package algoritms.cli;

import algoritms.geometry.ClosestPair;
import algoritms.geometry.Point;
import algoritms.geometry.Result;
import algoritms.metrics.CsvWriter;
import algoritms.metrics.Metrics;
import algoritms.sort.*;

import java.util.Random;


public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar app.jar <algo> <n> [outfile.csv]");
            System.err.println("Algos: mergesort, quicksort, select, closest");
            System.exit(1);
        }

        String algo = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);
        String outFile = args.length >= 3 ? args[2] : algo + ".csv";
        ISort sortAlgo;
        Metrics m = new Metrics();
        long start = System.nanoTime();

        switch (algo) {
            case "mergesort" -> {
                int[] arr = getRandomArray(n,1000);
                sortAlgo = new MergeSort();
                sortAlgo.sort(arr, m);
            }
            case "quicksort" -> {
                int[] arr = getRandomArray(n,1000);
                sortAlgo = new QuickSort();
                sortAlgo.sort(arr, m);
            }
            case "select" -> {
                int[] arr = getRandomArray(n,1000);
                int k = n / 2; // median
                DeterministicSelect.select(arr, k, m);
            }
            case "closest" -> {
                Point[] pts = getRandomPoints(n);
                Result r = ClosestPair.findClosest(pts, m);
                System.out.printf("Closest distance = %.5f%n", r.dist());
            }
            default -> {
                System.err.println("Unknown algo: " + algo);
                System.exit(2);
            }
        }

        long end = System.nanoTime();
        long timeMs = (end - start) / 1_000_000;

        writeToCsv(outFile,algo, n, timeMs, m);
        System.out.printf("Algo=%s n=%d time=%dms comps=%d swaps=%d depth=%d -> %s%n",
                algo, n, timeMs, m.getComparisons(), m.getSwaps(), m.getMaxDepth(), outFile);
    }
    private static void writeToCsv(String file,String algo,int n,long time,Metrics m){
        try (CsvWriter csv = new CsvWriter(file)) {
            csv.writeHeader();
            csv.writeRow(algo, n, time, m);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static Point[] getRandomPoints(int n) {
        Random rnd = new Random();
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        }
        return pts;
    }
    public static int[] getRandomArray(int listSize,int upperBound){
        int[] randomNumbers = new int[listSize];
        Random random = new Random();
        for (int i = 0; i < listSize; i++) {
            int randomNumber = random.nextInt(upperBound);
            randomNumbers[i] = randomNumber;
        }
        return randomNumbers;
    }
}