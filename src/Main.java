import algoritms.metrics.CsvWriter;
import algoritms.metrics.Metrics;
import algoritms.sort.ISort;
import algoritms.sort.InsertionSort;
import algoritms.sort.MergeSort;
import algoritms.sort.QuickSort;

import java.util.ArrayList;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Metrics m = new Metrics();

        int listSize = 1000;

        int upperBound = 1000;

        int[] randomNumbers = getRandomArray(listSize, upperBound);

        long start = System.nanoTime();
        ISort sortAlgoritm = new InsertionSort();
        sortAlgoritm.sort(randomNumbers, m);

        long end = System.nanoTime();

        writeToCsv(sortAlgoritm.getClass().getName().replace("algoritms.sort.",""),listSize,end-start,m);

        for(int i = 0; i < listSize; i++){
            System.out.println(randomNumbers[i]);
        }
    }
    private static void writeToCsv(String algo,int n,long time,Metrics m){
        try (CsvWriter csv = new CsvWriter("results.csv")) {
            csv.writeHeader();
            csv.writeRow(algo, n, time, m);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
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