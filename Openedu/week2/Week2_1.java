package week2;

import java.io.*;

public class Week2_1 {

    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    // l1 < l2; r1 < r2
    public static void merge(int[] array, int l1, int r1, int l2, int r2) throws IOException {
        bufferedWriter.write((l1+1) + " ");
        bufferedWriter.write((r2) + " ");
        int i = 0;
        int size = r2 - l1;
        int destStart = l1;
        int[] tmp = new int[size];
        while (i < size) {
            while (l1 < r1 && (l2 >= r2 || array[l1] <= array[l2])) { // stable
                tmp[i++] = array[l1];
                l1++;
            }
            while (l2 < r2 && (l1 >= r1 || array[l2] < array[l1])) {
                tmp[i++] = array[l2];
                l2++;
            }
        }


        for (int k = 0; k < size; k++) {
            array[destStart + k] = tmp[k];
        }

        bufferedWriter.write(array[destStart] + " ");
        bufferedWriter.write(array[r2 - 1] + "\n");
    }

    public static void mergeSort(int[] array, int l, int r) throws IOException {
        if (l == r - 1) {
            return;
        }
        mergeSort(array, l, l + (r - l) / 2);
        mergeSort(array, l + (r - l) / 2, r);
        merge(array, l, l + (r - l) / 2, l + (r - l) / 2, r);
    }

    public static void main(String[] args) throws IOException {
        bufferedReader = new BufferedReader(new FileReader("input.txt"));
        bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arraySize = Integer.valueOf(bufferedReader.readLine());
        String[] arrayStr = bufferedReader.readLine().split(" ");
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = Integer.valueOf(arrayStr[i]);
        }

        mergeSort(array, 0, array.length);

        for (int i = 0; i < arraySize; i++) {
            bufferedWriter.write(Integer.toString(array[i]));
            if (i + 1 == arraySize) {
                bufferedWriter.write("\n");
            } else {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
