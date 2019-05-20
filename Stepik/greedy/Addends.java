package stepik.greedy;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Addends {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        int n = Integer.valueOf(br.readLine());
        ArrayList<Integer> addends = new ArrayList<>(1024);
        int i = 1;
        while (true) {
            if (i * 2 < n) {
                addends.add(i);
                n = n - i;
            } else {
                addends.add(n);
                break;
            }
            i++;
        }

        os.write((addends.size() + "\n").getBytes());
        for (int k = 0; k < addends.size(); k++) {
            os.write((addends.get(k) + " ").getBytes());
        }

        os.flush();
        os.close();
    }
}
