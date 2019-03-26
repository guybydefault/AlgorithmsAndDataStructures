package week4;

import mooc.EdxIO;

public class Week4_4 {

    public static void main(String[] args) {
        EdxIO edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        QueueWithMinimal<Integer> queueWithMinimal = new QueueWithMinimal<>((int) Math.pow(10, 6));

        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case '+':
                    queueWithMinimal.push(edxIO.nextInt());
                    break;
                case '-':
                    queueWithMinimal.pop();
                    break;
                case '?':
                    edxIO.println(queueWithMinimal.getMin());
                    break;
            }
        }

        edxIO.close();
    }

    private static class StackWithMinimal<T extends Comparable> {

        private Object[] array;

        private Object[] minArray;

        private int stackPointer = 0;

        public StackWithMinimal(int initialCapacity) {
            array = new Object[initialCapacity];
            minArray = new Object[initialCapacity];
        }

        public int getSize() {
            return stackPointer;
        }

        public void push(T el) {
            if (el == null) {
                throw new IllegalArgumentException("El can not be null");
            }

            array[stackPointer] = el;

            Object min = el;
            if (getSize() > 0 && el.compareTo(minArray[stackPointer - 1]) >= 0) {
                min = minArray[stackPointer - 1];
            }
            minArray[stackPointer] = min;

            stackPointer++;
        }

        public T pop() {
            T result = peek();
            stackPointer--;
            array[stackPointer] = null;
            minArray[stackPointer] = null;
            return result;
        }

        public T peek() {
            if (getSize() == 0) {
                throw new IllegalStateException("No elements in stack");
            }
            return (T) array[stackPointer - 1];
        }

        public T getMin() {
            if (getSize() == 0) {
                throw new IllegalStateException("No elements in stack");
            }
            return (T) minArray[stackPointer - 1];
        }

        public boolean isEmpty() {
            return getSize() == 0;
        }
    }

    public static class QueueWithMinimal<T extends Comparable> {

        private StackWithMinimal<T> s1;
        private StackWithMinimal<T> s2;

        public QueueWithMinimal(int initialCapacity) {
            s1 = new StackWithMinimal<>(initialCapacity);
            s2 = new StackWithMinimal<>(initialCapacity);
        }

        public int getSize() {
            return s1.getSize() + s2.getSize();
        }

        public void push(T el) {
            s1.push(el);
        }

        public T pop() {
            if (getSize() == 0) {
                throw new IllegalStateException("Queue is empty");
            }

            if (!s2.isEmpty()) {
                return s2.pop();
            }

            while (s1.getSize() != 0) {
                s2.push(s1.pop());
            }

            return s2.pop();
        }

        public T getMin() {
            if (getSize() == 0) {
                throw new IllegalStateException("Queue is empty");
            }

            if (s1.isEmpty()) {
                return s2.getMin();
            } else if (s2.isEmpty()) {
                return s1.getMin();
            } else {
                if ((s1.getMin().compareTo(s2.getMin()) > 0)) {
                    return s2.getMin();
                } else {
                    return s1.getMin();
                }
            }
        }
    }
}
