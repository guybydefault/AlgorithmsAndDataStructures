package week2;

import mooc.EdxIO;

import java.io.IOException;

/**
 * Asc/desc enhancements are commented cause they are not required to pass :3
 * though some tests run faster with them (but most of them are actually slower =D)
 */
public class Week2_4 {

    private static EdxIO edxIO;

    private static void swap(int[] array, int i, int j) throws IOException {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static void searchKth(int[] array, int kIndex, int kEnd, int startIndex, int endIndex, int absStart, int absEnd) throws IOException {
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

        if (pivot == kIndex) {
            edxIO.print(array[pivot]);
            edxIO.print(" ");
            if (kIndex == kEnd) {
                return;
            } else {
                searchKth(array, kIndex + 1, kEnd, pivot + 1, absEnd, absStart, absEnd);
            }
        } else if (pivot < kIndex) {
            absStart = pivot + 1;
            searchKth(array, kIndex, kEnd, pivot + 1, endIndex, absStart, absEnd);
        } else {
            if (pivot > kEnd) {
                absEnd = pivot - 1;
            }
            searchKth(array, kIndex, kEnd, absStart, pivot - 1, absStart, absEnd);
        }
    }

    public static void main(String[] args) throws IOException {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        int kStart = edxIO.nextInt() - 1;
        int kEnd = edxIO.nextInt() - 1;
        int[] a = new int[n];
        int A = edxIO.nextInt();
        int B = edxIO.nextInt();
        int C = edxIO.nextInt();
        a[0] = edxIO.nextInt();
        a[1] = edxIO.nextInt();

//        boolean isAsc = a[1] >= a[0];
//        int tmpAscStart = 0;
//        int ascStart = 0;
//        int ascEnd = 0;
//        boolean isDesc = a[1] <= a[0];
//        int tmpDescStart = 0;
//        int descStart = 0;
//        int descEnd = 0;
        for (int i = 2; i < n; i++) {
            a[i] = A * a[i - 2] + B * a[i - 1] + C;
//            if (isAsc && a[i] < a[i - 1]) {
//                isAsc = false;
//                if (i - tmpAscStart > ascEnd - ascStart + 1) { // if asc sequence is longer than previous
//                    ascStart = tmpAscStart;
//                    ascEnd = i - 1;
//                }
//            } else if (!isAsc && a[i] >= a[i - 1]) {
//                isAsc = true;
//                tmpAscStart = i - 1;
//            }
//
//            if (isDesc && a[i] > a[i - 1]) {
//                isDesc = false;
//                if (i - tmpDescStart > descEnd - descStart + 1) { // if desc sequence is longer than previous
//                    descStart = tmpDescStart;
//                    descEnd = i - 1;
//                }
//            } else if (!isDesc && a[i] <= a[i - 1]) {
//                isDesc = true;
//                tmpDescStart = i - 1;
//            }
        }
//        if (isAsc && n - tmpAscStart > ascEnd - ascStart + 1) { // if last asc sequence is longer than previous
//            ascStart = tmpAscStart;
//            ascEnd = n - 1;
//        }
//        if (isDesc && n - tmpDescStart > descEnd - descStart + 1) { // if last asc sequence is longer than previous
//            descStart = tmpDescStart;
//            descEnd = n - 1;
//        }


//        int numLessThanAsc = 0;
//
//        for (int i = 0; i < ascStart; i++) {
//            if (a[i] <= a[ascStart]) {
//                numLessThanAsc++;
//            }
//        }
//        for (int i = ascEnd + 1; i < n; i++) {
//            if (a[i] <= a[ascStart]) {
//                numLessThanAsc++;
//            }
//        }
//
//        int numLessThanDesc = 0;
//
//        for (int i = 0; i < descStart; i++) {
//            if (a[i] <= a[descEnd]) {
//                numLessThanDesc++;
//            }
//        }
//        for (int i = descEnd + 1; i < n; i++) {
//            if (a[i] <= a[descEnd]) {
//                numLessThanDesc++;
//            }
//        }

//        if (kStart >= numLessThanAsc && kEnd < numLessThanAsc + ascEnd - ascStart + 1) {
//            for (int k = kStart; k <= kEnd; k++) {
//                edxIO.print(a[ascStart + k - numLessThanAsc] + " ");
//            }
//        } else if (kStart >= numLessThanDesc && kEnd < numLessThanDesc + descEnd - descStart + 1) {
//            for (int k = kStart; k <= kEnd; k++) {
//                edxIO.print(a[descEnd - (descStart + k - numLessThanDesc)] + " ");
//            }
//        } else {
            searchKth(a, kStart, kEnd, 0, a.length - 1, 0, a.length - 1);
//        }

        edxIO.close();
    }
}
