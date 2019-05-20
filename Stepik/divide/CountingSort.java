package stepik.divide;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class CountingSort {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] count = new int[11];
        for (int i = 0; i < n; i++) {
            count[sc.nextInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            for (int k = 0; k < count[i]; k++) {
                os.write((i + " ").getBytes());
            }
        }

        br.close();
        os.flush();
        os.close();
    }


}
