package stepik.dynamic;

import java.io.*;
import java.util.Arrays;

public class DividerSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        int n = Integer.valueOf(br.readLine());
        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
        int[] d = new int[n];

        for (int i = 0; i < n; i++) {
            d[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[i] % a[j] == 0) {
                    d[i] = Integer.max(d[i], 1 + d[j]);
                }
            }
        }

        os.write((String.valueOf(Arrays.stream(d).max().getAsInt()) + "\n").getBytes());

        br.close();
        os.flush();
        os.close();
    }

}
