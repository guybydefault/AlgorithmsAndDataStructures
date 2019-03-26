package week1;

import java.io.*;

public class Week1_5_Bubble {

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static boolean isSortedDesc(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] < array[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arraySize = Integer.valueOf(bufferedReader.readLine());
        Integer[] array = new Integer[arraySize];
        String[] arrayStr = bufferedReader.readLine().split(" ");
        for (int i = 0; i < arraySize; i++) {
            array[i] = Integer.valueOf(arrayStr[i]);
        }

        if (isSortedDesc(array)) {
            for (int i = 0; i < array.length / 2; i++) {
                swap(array, i, array.length - 1 - i);
                bufferedWriter.write("Swap elements at indices " + (i + 1) + " and " + (array.length - i) + ".\n");
            }
        } else {
            for (int i = 0, swapsNumber = 1000; (i < arraySize - 1) && (swapsNumber != 0); i++) {
                swapsNumber = 0;
                for (int k = 0; k < arraySize - 1 - i; k++) {
                    if (array[k] > array[k + 1]) {
                        swap(array, k, k + 1);
                        swapsNumber++;
                        bufferedWriter.write("Swap elements at indices " + (k + 1) + " and " + (k + 2) + ".\n");
                    }
                }
            }
        }
        bufferedWriter.write("No more swaps needed.\n");

        for (int i = 0; i < arraySize; i++) {
            bufferedWriter.write(array[i] + " ");
        }

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
