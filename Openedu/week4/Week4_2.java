package week4;

import mooc.EdxIO;

public class Week4_2 {
    private static EdxIO edxIO;

    private static int[] queueArray = new int[(int) Math.pow(10, 6)];
    private static int queueTailPointer = 0;
    private static int queueHeadPointer = 0;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case '+':
                    queueArray[queueTailPointer] = edxIO.nextInt();
                    queueTailPointer = (queueTailPointer + 1) % queueArray.length;
                    break;
                case '-':
                    edxIO.println(queueArray[queueHeadPointer]);
                    queueHeadPointer = (queueHeadPointer + 1) % queueArray.length;
                    break;
            }
        }

        edxIO.close();
    }
}
