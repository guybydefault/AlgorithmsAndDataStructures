package week6;

import mooc.EdxIO;

public class Week6_3 {

    private static EdxIO edxIO;

    private static Node[] tree;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        if (n == 0) {
            edxIO.println(0);
            edxIO.close();
            return;
        }

        tree = new Node[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new Node(edxIO.nextInt(), edxIO.nextInt(), edxIO.nextInt());
        }

        edxIO.println(tree[0].getHeight());

        edxIO.close();
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

        private int getHeight() {
            int h = 1;

            if (left != 0) {
                h = Integer.max(h, tree[left - 1].getHeight() + 1);
            }
            if (right != 0) {
                h = Integer.max(h, tree[right - 1].getHeight() + 1);
            }

            return h;
        }
    }


}
