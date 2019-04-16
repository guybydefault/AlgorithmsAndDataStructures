package week6;

import mooc.EdxIO;

public class Week6_1 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = edxIO.nextInt();
        }

        int m = edxIO.nextInt();
        for (int i = 0, num; i < m; i++) {
            num = edxIO.nextInt();
            int leftIndex = binarySearch(num, a);
            if (leftIndex >= 0) {
                edxIO.print((leftIndex + 1) + " ");
                edxIO.println(binarySearchRightIterative(num, a) + 1);
            } else {
                edxIO.println("-1 -1");
            }

        }
        edxIO.close();
    }

    public static int binarySearch(int x, int[] arr) {
        int ind = binarySearchLeftIterative(x, arr);
        if (ind == arr.length || arr[ind] != x) {
            return -ind - 1;
        } else {
            return ind;
        }
    }

    public static int binarySearchLeftIterative(int x, int[] arr) {
        int l = 0;
        int r = arr.length;
        while (l < r ) {
            int mid = l + (r - l) / 2;
            if (arr[mid] < x) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }

    public static int binarySearchRightIterative(int x, int[] arr) {
        int l = 0;
        int r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] <= x) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l - 1;
    }
}
