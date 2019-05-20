package stepik.introduction;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class Gcd1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        BigInteger a = new BigInteger(line[0]);
        BigInteger b = new BigInteger(line[1]);
        OutputStream os = new BufferedOutputStream(System.out);
        // Uses Euclid's algo and when numbers are approximately same length switches to binaryGCD
        // Math.abs(a.intLen - b.intLen) < 2 - condition of switching to binaryGCD
        os.write(a.gcd(b).toString().getBytes());
        os.flush();
    }
}
