package week4;

import mooc.EdxIO;

public class Week4_1 {
    private static EdxIO edxIO;

    private static int[] stackArray = new int[(int) Math.pow(10, 6)];
    private static int stackPointer = 0;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case '+':
                    stackArray[stackPointer] = edxIO.nextInt();
                    stackPointer++;
                    break;
                case '-':
                    edxIO.println(stackArray[stackPointer - 1]);
                    stackPointer--;
                    break;
            }
        }

        edxIO.close();
    }
}
