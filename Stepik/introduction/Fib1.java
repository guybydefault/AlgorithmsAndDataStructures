package stepik.introduction;

import java.io.*;

public class Fib1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        int[] f = new int[41];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        OutputStream os = new BufferedOutputStream(System.out);
        os.write((String.valueOf(f[n]).getBytes()));
        os.flush();
    }
}
