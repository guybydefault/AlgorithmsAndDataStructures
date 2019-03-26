package week1;

import java.io.*;

public class Week1_4 {

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int n = Integer.parseInt(bufferedReader.readLine());
        String[] moneyStrArray = bufferedReader.readLine().split(" ");
        Integer[] aIdentifiers = new Integer[n];
        Double[] aMoney = new Double[n];
        for (int i = 0; i < n; i++) {
            aIdentifiers[i] = i + 1;
            aMoney[i] = Double.parseDouble(moneyStrArray[i]);
        }

        for (int i = 0; i < n; i++) {
            int j = i;
            while (j > 0 && aMoney[j] < aMoney[j - 1]) {
                swap(aMoney, j, j - 1);
                swap(aIdentifiers, j, j - 1);
                j--;
            }
        }

        bufferedWriter.write(aIdentifiers[0] + " ");
        bufferedWriter.write(aIdentifiers[n / 2] + " ");
        bufferedWriter.write(aIdentifiers[n - 1].toString());

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
