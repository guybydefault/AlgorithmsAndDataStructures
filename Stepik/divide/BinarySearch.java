package stepik.divide;

import java.io.*;
import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
        int[] k = Arrays.stream(br.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();

        for (int i = 1; i < k.length; i++) {
            os.write((binarySearch(k[i], a) + " ").getBytes());
        }

        br.close();
        os.flush();
        os.close();
    }

    public static int binarySearch(int x, int[] arr) {
        int ind = binarySearchLeftIterative(x, arr);
        if (ind == arr.length || arr[ind] != x) {
            return -1;
        } else {
            return ind;
        }
    }

    public static int binarySearchLeftIterative(int x, int[] arr) {
        int l = 1; // cuz first number in array - is n of elements!!!(((
        int r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] < x) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }
}
