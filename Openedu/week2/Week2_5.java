package week2;

import mooc.EdxIO;

import java.io.IOException;


public class Week2_5 {

    private static EdxIO edxIO;

    private static void swap(int[] array, int i, int j) throws IOException {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static boolean quickSort(int[][] a, int k, int startIndex, int endIndex, int span) throws IOException {
        if (endIndex < startIndex) {
            return true;
        }

        int pivot = startIndex + (endIndex - startIndex) / 2; // startIndex <= pivot < endIndex
        int l = startIndex;
        int r = endIndex;
        while (l < r) {
            while (l < pivot) {
                if (a[k][l] > a[k][pivot]) {
                    swap(a[k], l, pivot);
                    pivot = l;
                } else {
                    l++;
                }
            }

            while (r > pivot) {
                if (a[k][r] < a[k][pivot]) {
                    swap(a[k], pivot, r);
                    pivot = r;
                } else {
                    r--;
                }
            }
        }

        // checking: prev subsequence is less or equal
        if (k != 0 && a[k - 1][pivot] > a[k][pivot]) {
            return false;
        }
        // checking: last subsequence > first
        if (span > 1 && k == span - 1 && a[0].length > pivot + 1 && a[0][pivot + 1] < a[k][pivot]) {
            return false;
        }

        boolean result = true;
        result = quickSort(a, k, startIndex, pivot - 1, span);
        if (!result) {
            return result;
        }
        return quickSort(a, k, pivot + 1, endIndex, span);

    }

    public static void printArray(int[][] array, int n, int span) {
        edxIO.println();
        int i = 0;
        int k = 0;
        while (i < n) {
            edxIO.print(array[k][i / span]+  " ");
            i++;
            k++;
            if (k == span) {
                k = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        int span = edxIO.nextInt();

        int[][] array = new int[span][n / span];
        for (int i = 0; i < n % span; i++) {
            array[i] = new int[n / span + 1];
        }

        int i = 0;
        int k = 0;
        while (i < n) {
            array[k][i / span] = edxIO.nextInt();
            i++;
            k++;
            if (k == span) {
                k = 0;
            }
        }

        for (k = 0; k < span; k++) {
            if (!quickSort(array, k, 0, array[k].length - 1, span)) {
                edxIO.print("NO");
//                printArray(array, n, span);
                edxIO.close();
                return;
            }
        }
        edxIO.print("YES");
//        printArray(array, n, span);
        edxIO.close();
    }
}
