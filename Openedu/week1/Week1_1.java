package week1;

import java.io.*;

public class Week1_1 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
        String[] str = bufferedReader.readLine().split(" ");

        int a = Integer.valueOf(str[0]);
        int b = Integer.valueOf(str[1]);
        int sum = a + b;

        bufferedWriter.write(sum + "\n");
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
