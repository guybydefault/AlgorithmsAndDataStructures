package stepik.greedy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecoding {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 8192);
        OutputStream os = new BufferedOutputStream(System.out);

        String[] metaData = br.readLine().split(" ");
        int k = Integer.valueOf(metaData[0]);
        int l = Integer.valueOf(metaData[1]);

        Map<String, String> letterMap = new HashMap<>();

        String[] line;
        for (int i = 0; i < k; i++) {
            line = br.readLine().split(": ");
            letterMap.put(line[1], line[0]);
        }

        String encodedMessage = br.readLine();
        StringBuilder decodedMessageBuilder = new StringBuilder(1024);
        int i = 0;
        while (i < l) {
            int j = i + 1;
            while (!letterMap.containsKey(encodedMessage.substring(i, j))) {
                j++;
            }
            decodedMessageBuilder.append(letterMap.get(encodedMessage.substring(i, j)));
            i = j;
        }
        os.write(decodedMessageBuilder.toString().getBytes());
        os.flush();
        os.close();
    }
}
