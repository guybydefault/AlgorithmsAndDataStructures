package week5;

import mooc.EdxIO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Week5_2 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        Heap<HeapNode<Integer>> heap = new Heap(n);
        HeapNode[] heapNodeMapping = new HeapNode[n];

        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case 'A':
                    int el = edxIO.nextInt();
                    HeapNode newEl = new HeapNode<>(el, el);
                    heap.insert(newEl);
                    heapNodeMapping[i] = newEl;
                    break;
                case 'X':
                    if (heap.heapSize == 0) {
                        edxIO.println('*');
                    } else {
                        int min = heap.getMin().getKey();
                        heap.removeMin();
                        edxIO.println(min);
                    }
                    break;
                case 'D':
                    HeapNode heapNode = heapNodeMapping[edxIO.nextInt() - 1];
                    int replaceEl = edxIO.nextInt();
                    heapNode.setKey(replaceEl);
                    heap.siftUp(heapNode.getPos());
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        edxIO.close();
    }

    private interface Keyable {

        public int getKey();

        public void setKey(int key);

        public int getPos();

        public void setPos(int pos);

    }

    public static class HeapNode<T> implements Keyable {
        private T obj;
        private int key;
        private int pos;

        HeapNode(T obj, int key) {
            this.obj = obj;
            this.key = key;
        }

        private T getObject() {
            return obj;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }

    }

    public static class Heap<T extends Keyable> {
        private Object[] h;
        private int heapSize = 0;

        public Heap(int capacity) {
            h = new Object[capacity];
        }

        /**
         * Constructs a HEAP from the array O(N)
         *
         * @param array
         */
        public Heap(T[] array) {
            h = new Object[array.length];
            heapSize = array.length;
            for (int i = 0; i < array.length; i++) {
                h[i] = array[i];
            }
            for (int i = heapSize / 2; i >= 0; i--) {
                siftDown(i);
            }
        }

        public void insert(T t) {
            t.setPos(heapSize);
            h[heapSize] = t;
            heapSize++;
            siftUp(heapSize - 1);
        }

        public T getMin() {
            return get(0);
        }

        public void removeMin() {
            swap(0, --heapSize);
            siftDown(0);
        }

        private void siftUp(int i) {
            while (i > 0 && get(i).getKey() < get((i - 1) / 2).getKey()) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        private void siftDown(int i) {
            while (2 * i + 1 < heapSize) {
                int j = 2 * i + 1;
                if (j + 1 < heapSize && get(j).getKey() > get(2 * i + 2).getKey()) {
                    j += 1;
                }
                if (get(j).getKey() < get(i).getKey()) {
                    swap(i, j);
                    i = j;
                } else {
                    break;
                }
            }
        }

        private T get(int i) {
            return (T) h[i];
        }


        private void swap(int i, int j) {
            get(i).setPos(j);
            get(j).setPos(i);

            T tmp = get(i);
            h[i] = get(j);
            h[j] = tmp;
        }

        public void changeKey(int i, int newKey) {
            get(i).setKey(newKey);
            siftDown(i);
            siftUp(i);
        }

        public void print() {
            BigDecimal dec = new BigDecimal(Math.log(heapSize) / Math.log(2));
            dec = dec.setScale(1, RoundingMode.UP);
            int levels = dec.intValue();
            int lastLevel = (int) Math.pow(2, levels - 1);
            System.out.println("*** HEAP *** SIZE(" + heapSize + ")");
            for (int i = 0, k = 0, lev = 0; i <= (heapSize - 1); i++) {
                if (lev == 4) {
                    System.out.println(" ");
                } else {
                    for (int l = 0; l < (2 * lastLevel - 1) / Math.pow(2, lev); l++) {
                        System.out.print(" ");
                    }
                }
                System.out.print(get(i).getKey());
                if (i == k) {
                    k = 2 * k + 2;
                    lev += 1;
                    System.out.println();
                }
            }
        }
    }

}
