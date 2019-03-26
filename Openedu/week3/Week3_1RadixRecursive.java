package week3;

import mooc.EdxIO;

public class Week3_1RadixRecursive {

    private static final int DIG_S = 8;
    private static final int COUNT_SIZE = (int) Math.pow(2, DIG_S);
    private static final int INITIAL_MASK;
    private static final int MAX_EL = 40000 + 1;

    static {
        int mask = 0;
        for (int i = 0; i < DIG_S; i++) {
            mask |= 0x80000000 >> i;
        }
        INITIAL_MASK = mask;
    }

    private static EdxIO edxIO;

    private static int[] tmpResult;

    private static void solveBucket(final int[] C, final int digit, final int l, final int r) {
        if (digit == Integer.SIZE / DIG_S) {
            return;
        } else if (r - l < 10 && l % 10 != 0 && r % 10 > l % 10) {
            return;
        } else if (r - l < 47) {
            insertionSort(C, l, r);
        } else {
            countingSort(C, digit, l, r);
        }
    }

    private static void countingSort(final int[] C, final int digit, final int l, final int r) {
        int[] count = new int[COUNT_SIZE];

        int bitMask = INITIAL_MASK >>> (digit * DIG_S);
        int shift = (32 - (digit + 1) * DIG_S);

        int length = r - l;
        for (int j = l; j < r; j++) {
            count[(C[j] & bitMask) >> shift]++;
        }

        for (int k = 1; k < count.length; k++) {
            count[k] = count[k] + count[k - 1];
        }

        if (digit == Integer.SIZE / DIG_S - 1 && count[0] == length) {
            return;
        }

        int[] countClone = count.clone();

        for (int j = length - 1; j >= 0; j--) {
            int d = (C[l + j] & bitMask) >> shift;
            tmpResult[count[d] - 1] = C[l + j];
            count[d]--;
        }

        for (int i = 0; i < length; i++) {
            C[l + i] = tmpResult[i];
        }

        count = countClone;

        int left = l;
        if (count[0] > 0) {
            solveBucket(C, digit + 1, left, left + count[0]);
            left = l + count[0];
        }
        for (int k = 1; k < count.length; k++) {
            if (count[k] > count[k - 1]) {
                solveBucket(C, digit + 1, left, l + count[k]);
                left = l + count[k];
            }
            if (count[k] == length) {
                return;
            }
        }
    }

    private static void insertionSort(final int[] array, final int l, final int r) {
        for (int i = l; i < r; i++) {
            int j = i;
            while (j > l && array[j] < array[j - 1]) {
                int tmp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = tmp;
                j--;
            }
        }
    }

    public static void main(String[] args) {
        solve();
    }

    private static void fillArrayFromCount(int[] arrayToFill, int[] count) {
        for (int i = 0, toIndex = 0; i < count.length; i++) {
            for (int k = 0; k < count[i]; k++) {
                arrayToFill[toIndex] = i;
                toIndex++;
            }
        }
    }

    private static void solve() {
        edxIO = EdxIO.create();

        int sizeA = edxIO.nextInt();
        int sizeB = edxIO.nextInt();

        int[] A = new int[sizeA];
        int[] B = new int[sizeB];
        int[] C = new int[sizeA * sizeB];

        int[] ACount = new int[MAX_EL];
        int[] BCount = new int[MAX_EL];

        for (int i = 0; i < sizeA; i++) {
            A[i] = edxIO.nextInt();
            ACount[A[i]]++;

        }

        for (int i = 0; i < sizeB; i++) {
            B[i] = edxIO.nextInt();
            BCount[B[i]]++;
        }

        fillArrayFromCount(A, ACount);
        fillArrayFromCount(B, BCount);

        if (B.length < A.length) {
            int[] tmp = A;
            A = B;
            B = tmp;
        }

        for (int i = 0, totalIndex = 0; i < A.length; i++) {
            for (int k = 0; k < B.length; k++, totalIndex++) {
                C[totalIndex] = A[i] * B[k];
            }
        }

        // init
        tmpResult = new int[C.length];

        solveBucket(C, 0, 0, C.length);

        long sum = 0;
        for (int i = 0; i < C.length; i += 10) {
            sum += C[i];
        }

        edxIO.println(sum);
        edxIO.close();
    }
}
