package week6;

import mooc.EdxIO;

public class Week6_4 {

    private static EdxIO edxIO;

    private static Node[] tree;
    private static int[] parents;
    private static int size;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        size = n;
        tree = new Node[n];
        parents = new int[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new Node(edxIO.nextInt(), edxIO.nextInt(), edxIO.nextInt());

            if (tree[i].hasLeft()) {
                parents[tree[i].getLeft() - 1] = i;
            }
            if (tree[i].hasRight()) {
                parents[tree[i].getRight() - 1] = i;
            }
        }

        int m = edxIO.nextInt();
        for (int i = 0; i < m; i++) {
            int x = edxIO.nextInt();
            x = find(x);

            if (x >= 0) {
                    if (tree[parents[x]].left - 1 == x) {
                        tree[parents[x]].left = 0;
                    }
                    else {
                        tree[parents[x]].right = 0;
                    }
                size -= tree[x].getSize();
            }
            edxIO.println(size);
        }

        edxIO.close();
    }

    private static int find(int x) {
        int i = 0;
        while (tree[i].getKey() != x) {
            if (x < tree[i].getKey()) {
                if (tree[i].hasLeft()) {
                    i = tree[i].left - 1;
                }
                else {
                    return -1;
                }
            }
            else {
                if (tree[i].hasRight()) {
                    i = tree[i].right - 1;
                }
                else {
                    return -1;
                }
            }
        }

        return i;
    }

    private static class Node {
        private int key;
        private int left;
        private int right;

        public Node(int key, int left, int right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public boolean hasLeft() {
            return left != 0;
        }

        public boolean hasRight() {
            return right != 0;
        }

        private int getHeight() {
            int h = 1;

            if (hasLeft()) {
                h = Integer.max(h, tree[left - 1].getHeight() + 1);
            }
            if (hasRight()) {
                h = Integer.max(h, tree[right - 1].getHeight() + 1);
            }

            return h;
        }

        private int getSize() {
            int size = 1;

            if (hasLeft()) {
                size += tree[left - 1].getSize();
            }
            if (hasRight()) {
                size += tree[right - 1].getSize();
            }

            return size;
        }
    }


}
