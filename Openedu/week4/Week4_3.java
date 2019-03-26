package week4;

import mooc.EdxIO;

public class Week4_3 {
    private static EdxIO edxIO;

    private static char[] stackArray = new char[5000];

    private static int stackPointer = 0;

    public static char getStartingBracket(char closingBracket) {
        switch (closingBracket) {
            case ']':
                return '[';
            case ')':
                return '(';
            default:
                throw new IllegalStateException();
        }
    }


    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        char ch;
        byte[] sequence;
        boolean isValid = true;
        for (int i = 0; i < n; i++) {
            sequence = edxIO.nextBytes();
            for (int k = 0; k < sequence.length && isValid; k++) {
                ch = (char) sequence[k];
                switch (ch) {
                    case '(':
                    case '[':
                        if (stackPointer > stackArray.length - 1) {
                            // it's impossible that later we meet more than stack.Array.length closing brackets
                            isValid = false;
                            break;
                        }
                        stackArray[stackPointer] = ch;
                        stackPointer++;
                        break;
                    case ')':
                    case ']':
                        if (stackPointer < 1 || getStartingBracket(ch) != stackArray[stackPointer - 1]) {
                            isValid = false;
                            break;
                        }
                        stackPointer--;
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
            edxIO.println(isValid && stackPointer == 0 ? "YES" : "NO");
            stackPointer = 0;
            isValid = true;
        }

        edxIO.close();
    }
}
