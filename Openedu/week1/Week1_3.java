package week1;

import java.io.*;

public class Week1_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arraySize = Integer.valueOf(bufferedReader.readLine());
        int[] array = new int[arraySize];
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
                j--;
            }
            bufferedWriter.write(j + 1 + " ");
        }
        bufferedWriter.write("\n");
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
