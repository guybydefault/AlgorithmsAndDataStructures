package week7;

import mooc.EdxIO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Week7_1 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        AVLTree tree = new AVLTree(n);
        for (int i = 0; i < n; i++) {
            int key = edxIO.nextInt();
            int left = edxIO.nextInt() - 1;
            int right = edxIO.nextInt() - 1;

            Node<Long> currentNode = tree.getNodes()[i];
            // it can happen that it was created before if its' parent has already been parsed
            if (currentNode == null) {
                currentNode = new Node(key);
                tree.getNodes()[i] = currentNode;
            }

            if (left != -1) {
                if (tree.getNodes()[left] == null) {
                    Node node = new Node<Long>();
                    tree.getNodes()[left] = node;
                    node.setParent(tree.getNodes()[i]);
                }
                tree.getNodes()[i].setLeft(tree.getNodes()[left]);
            }

            if (right != -1) {
                if (tree.getNodes()[right] == null) {
                    Node node = new Node<Long>();
                    tree.getNodes()[right] = node;
                    node.setParent(tree.getNodes()[i]);
                }
                tree.getNodes()[i].setRight(tree.getNodes()[right]);
            }

            if (left == -1 && right == -1) {
                LinkedList<Node<Long>> curr = new LinkedList<>();
                while (currentNode != null) {
                    curr.push(currentNode);
                    currentNode = currentNode.getParent();
                }
                // setting height for all of the nodes on the path from
                // the first leaf
                while (curr.size() != 0) {
                    currentNode = curr.pop();
                    if (currentNode.getHeight() < curr.size())
                        currentNode.setHeight(curr.size());
                }
            }
        }

        for (int i = 0; i < n; i++) {
            edxIO.println(tree.getNodes()[i].getBalance());
        }

        edxIO.close();

    }

    static class AVLTree<T> {
        private Node<T>[] nodes;
        List<Node<T>> leaves = new ArrayList<>();
        private long size;

        public AVLTree(int n) {
            size = n;
            nodes = new Node[n];
        }

        public Node<T>[] getNodes() {
            return nodes;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

    static class Node<T> {
        private T key;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        private long height;

        Node() {

        }

        Node(T key) {
            this.key = key;
        }

        public long getHeight() {
            return height;
        }

        public void setHeight(long height) {
            this.height = height;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public long getBalance() {
            if (left != null && right != null) {
                return right.height - left.height;
            } else if (left == null && right != null) {
                return right.height + 1;
            } else if (left != null) {
                // right == null
                return -1 - left.height;
            }

            return 0;
        }
    }
}
