package week4;

import mooc.EdxIO;

import java.util.ArrayList;
import java.util.HashMap;

public class Week4_5 {
    private static EdxIO edxIO;

    /**
     * BEGIN Queue
     */
    private static int[] queueArray = new int[(int) Math.pow(10, 5)];
    private static int queueTailPointer = 0;
    private static int queueHeadPointer = 0;

    private static int pop() {
        int result = queueArray[queueHeadPointer];
        queueHeadPointer = (queueHeadPointer + 1) % queueArray.length;
        return result;
    }

    private static void push(int el) {
        queueArray[queueTailPointer] = Integer.remainderUnsigned(el, 65536);
        queueTailPointer = (queueTailPointer + 1) % queueArray.length;
    }

    /**
     * END Queue
     */

    /**
     * BEGIN VM Structure
     */
    private static int[] registers = new int['z' - 'a' + 1];
    private static ArrayList<byte[]> code = new ArrayList();
    private static HashMap<String, Integer> labels = new HashMap<String, Integer>();

    /**
     * END VM Structure
     */

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        byte[] ins = edxIO.nextBytes();
        int i = 0;
        do {
            code.add(ins);
            if (ins[0] == ':') {
                labels.put(new String(ins).substring(1), i);
            }
            i++;
            ins = edxIO.nextBytes();

        } while (ins != null);

        int ip = 0;
        while (ip < code.size()) {
            ins = code.get(ip);
            switch (ins[0]) {
                case '+':
                    push(pop() + pop());
                    break;
                case '-':
                    push(pop() - pop());
                    break;
                case '*':
                    push(pop() * pop());
                    break;
                case '/':
                    int a = pop();
                    int b = pop();
                    push(b == 0 ? 0 : a / b);
                    break;
                case '%':
                    a = pop();
                    b = pop();
                    push(b == 0 ? 0 : a % b);
                    break;
                case '>':
                    registers[ins[1] - 'a'] = pop();
                    break;
                case '<':
                    push(registers[ins[1] - 'a']);
                    break;
                case 'P':
                    edxIO.println(ins.length == 1 ? pop() : registers[ins[1] - 'a']);
                    break;
                case 'C':
                    edxIO.print((char) (ins.length == 1 ? pop() % 256 : registers[ins[1] - 'a'] % 256));
                    break;
                case ':':
                    break;
                case 'J':
                    ip = labels.get(new String(ins).substring(1));
                    break;
                case 'Z':
                    if (registers[ins[1] - 'a'] == 0) {
                        ip = labels.get(new String(ins).substring(2));
                    }
                    break;
                case 'E':
                    if (registers[ins[1] - 'a'] == registers[ins[2] - 'a']) {
                        ip = labels.get(new String(ins).substring(3));
                    }
                    break;
                case 'G':
                    if (registers[ins[1] - 'a'] > registers[ins[2] - 'a']) {
                        ip = labels.get(new String(ins).substring(3));
                    }
                    break;
                case 'Q':
                    edxIO.close();
                    return;
                default:
                    int val = 0;
                    for (int k = 0; k < ins.length; k++) {
                        val += (ins[k] - '0') * Math.pow(10, ins.length - k - 1);
                    }
                    push(val);
            }
            ip++;
        }


        edxIO.close();
        return;
    }

}
