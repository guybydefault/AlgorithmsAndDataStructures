package week1;

import java.io.*;

public class Week1_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
        String[] str = bufferedReader.readLine().split(" ");

        long a = Integer.valueOf(str[0]);
        long b = Integer.valueOf(str[1]);
        long result = a + b * b;

        bufferedWriter.write(result + "\n");
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
