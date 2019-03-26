package week1;

import java.io.*;

public class Week1_5_Ins {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arraySize = Integer.valueOf(bufferedReader.readLine());
        Integer[] array = new Integer[arraySize];
        String[] arrayStr = bufferedReader.readLine().split(" ");

        for (int i = 0; i < arraySize; i++) {
            array[i] = Integer.valueOf(arrayStr[i]);
        }


        for (int i = 0; i < arraySize; i++) {
            int j = i;
            while (j > 0 && array[j] < array[j - 1]) {
                int tmp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = tmp;
                bufferedWriter.write("Swap elements at indices " + (j) + " and " + (j + 1) + ".\n");
                j--;
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

