package week1;

import java.io.*;

public class Week1_5 {


    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    private static void swap(int[] array, int i, int j) throws IOException {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        bufferedWriter.write("Swap elements at indices " + (i+1) + " and " + (j+1) + ".\n");
    }

    private static void quickSort(int[] array, int startIndex, int endIndex) throws IOException {
        if (endIndex < startIndex) {
            return;
        }

        int pivot = startIndex + (endIndex - startIndex) / 2; // startIndex <= pivot < endIndex
        int l = startIndex;
        int r = endIndex;
        while (l < r) {
            while (l < pivot) {
                if (array[l] > array[pivot]) {
                    swap(array, l, pivot);
                    pivot = l;
                } else {
                    l++;
                }
            }

            while (r > pivot) {
                if (array[r] < array[pivot]) {
                    swap(array, pivot, r);
                    pivot = r;
                } else {
                    r--;
                }
            }
        }

        quickSort(array, startIndex, pivot - 1);
        quickSort(array, pivot + 1, endIndex);
    }

    public static void main(String[] args) throws IOException {
        bufferedReader = new BufferedReader(new FileReader("input.txt"));
        bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arraySize = Integer.valueOf(bufferedReader.readLine());
        int[] array = new int[arraySize];
        String[] arrayStr = bufferedReader.readLine().split(" ");

        for (int i = 0; i < arraySize; i++) {
            array[i] = Integer.valueOf(arrayStr[i]);
        }

        quickSort(array, 0, array.length - 1);

        bufferedWriter.write("No more swaps needed.\n");

        for (int i = 0; i < arraySize; i++) {
            bufferedWriter.write(array[i] + " ");
        }

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}

