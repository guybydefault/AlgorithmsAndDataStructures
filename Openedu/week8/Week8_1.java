package week8;

import mooc.EdxIO;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Week8_1 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        Set<Long> set = new HashSet<Long>(Integer.MAX_VALUE/100, 0.75f);
        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case 'A':
                    Long val = edxIO.nextLong();
                    set.add(val);
                    break;
                case 'D':
                    set.remove(edxIO.nextLong());
                    break;
                case '?':
                    edxIO.println(set.contains(edxIO.nextLong()) ? 'Y' : 'N');
                    break;
            }
        }

        edxIO.close();

    }
}
