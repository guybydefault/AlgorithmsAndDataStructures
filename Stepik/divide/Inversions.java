package stepik.divide;

import java.io.*;
import java.util.Scanner;

public class Inversions {

    private static long inversionsNumber = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        mergeSort(a, 0, a.length);

        os.write((inversionsNumber + "\n").getBytes());
        br.close();
        os.flush();
        os.close();
    }

    // l1 < l2; r1 < r2
    public static void merge(int[] array, int l1, int r1, int l2, int r2) throws IOException {
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
                inversionsNumber += r1 - l1;
                l2++;
            }
        }

        for (int k = 0; k < size; k++) {
            array[destStart + k] = tmp[k];
        }
    }

    public static void mergeSort(int[] array, int l, int r) throws IOException {
        if (l == r - 1) {
            return;
        }
        mergeSort(array, l, l + (r - l) / 2);
        mergeSort(array, l + (r - l) / 2, r);
        merge(array, l, l + (r - l) / 2, l + (r - l) / 2, r);
    }
}
