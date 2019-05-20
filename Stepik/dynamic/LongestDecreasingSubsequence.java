package stepik.dynamic;

import java.io.*;
import java.util.Arrays;

public class LongestDecreasingSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        int n = Integer.valueOf(br.readLine());
        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
        int[] tail = new int[n];
        int[] previous = new int[n];
        int sequenceLength = 0;
        for (int i = 0; i < n; i++) {
            int pos = binarySearchRight(a, tail, sequenceLength, a[i]);
            if (pos == sequenceLength) {
                sequenceLength++;
            }
//            System.out.println("pos: " +  pos + "");
            previous[i] = pos > 0 ? tail[pos - 1] : -1;
            tail[pos] = i;
//            System.out.println("seqlen: "  +sequenceLength + " pos: " + pos  + " previous[" + i + "]: " + previous[i] + " tail[pos]=i:" + i);
        }

        os.write((String.valueOf(sequenceLength) + "\n").getBytes());

        int[] result = new int[sequenceLength];
        for (int i = tail[sequenceLength - 1]; i >= 0; i = previous[i]) {
            result[--sequenceLength] = i + 1;
        }

        for (int i = 0; i < result.length; i++) {
            os.write(String.valueOf(result[i] + " ").getBytes());
        }

        br.close();
        os.flush();
        os.close();
    }

    static int binarySearchRight(int[] a, int[] tail, int sequenceLength, int key) {
        int l = -1;
        int r = sequenceLength;
        while (l < r - 1) {
            int mid = (l + r) >>> 1;
            if (a[tail[mid]] >= key) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }

}
