package stepik.introduction;

import java.io.*;

public class Fib2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        int f[] = {0, 1};
        for (int i = 2; i < n + 1; i++) {
            f[i % 2] = (f[0] + f[1]) % 10;
        }
        OutputStream os = new BufferedOutputStream(System.out);
        os.write((String.valueOf(f[n % 2]).getBytes()));
        os.flush();
    }
}
