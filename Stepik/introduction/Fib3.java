package stepik.introduction;

import java.io.*;
import java.util.ArrayList;

public class Fib3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        long time = System.currentTimeMillis();
        long n = Integer.valueOf(line[0]);
        int m = Integer.valueOf(line[1]);
        OutputStream os = new BufferedOutputStream(System.out);
        os.write(getFibonacciRest(n, m).toString().getBytes());
        os.flush();
    }

    private static Long getFibonacciRest(long n, long m) {
        ArrayList<Long> s = getSequencePeriod(m);
        long period = s.size() - 2;
        int val = (int) (n % period);
        return s.get(val);
    }

    private static ArrayList<Long> getSequencePeriod(long m) {
        ArrayList<Long> s = new ArrayList();
        s.add(0l);
        s.add(1l);
        for (int i = 2; i < m * 6; i++) {
            s.add((s.get(i - 1) + s.get(i - 2)) % m);
            if (s.get(i) == 1 && s.get(i - 1) == 0) {
                break;
            }
        }
        return s;
    }
}
