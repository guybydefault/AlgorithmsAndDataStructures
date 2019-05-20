package week7;

import mooc.EdxIO;

import java.util.LinkedList;
import java.util.Queue;

public class Week7_2 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        Node<Long>[] nodes = new Node[n];

        AVLTree tree = new AVLTree(n);
        for (int i = 0; i < n; i++) {
            int key = edxIO.nextInt();
            int left = edxIO.nextInt() - 1;
            int right = edxIO.nextInt() - 1;

            Node<Long> currentNode = nodes[i];
            // it can happen that it was created before if its' parent has already been parsed
            if (currentNode == null) {
                currentNode = new Node(key);
                nodes[i] = currentNode;
            } else {
                nodes[i].setKey((long) key);
            }

            if (left != -1) {
                if (nodes[left] == null) {
                    Node node = new Node<Long>();
                    nodes[left] = node;
                    node.setParent(nodes[i]);
                }
                nodes[i].setLeft(nodes[left]);
            }

            if (right != -1) {
                if (nodes[right] == null) {
                    Node node = new Node<Long>();
                    nodes[right] = node;
                    node.setParent(nodes[i]);
                }
                nodes[i].setRight(nodes[right]);
            }
            if (i == 0) {
                tree.setRoot(nodes[i]);
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

//        for (int i = 0; i < n; i++) {
//            edxIO.println(tree.getNodes()[i].getBalance());
//        }
        System.out.println(tree.getRoot().getBalance());
        if (tree.getRoot().getRight().getBalance() == -1) {
            tree.bigLeftTurn(tree.getRoot());
        } else {
            tree.leftRotation(tree.getRoot());
        }

//        tree.print(edxIO);
        edxIO.println(n);
        tree.print(edxIO);
        edxIO.close();
    }

    static class AVLTree<T extends Comparable> {
        private long size;
        private Node<T> root;

        public AVLTree(int n) {
            size = n;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Node lookup(T key) {
            if (isEmpty()) {
                return null;
            }

            Node<T> currNode = root;
            while (
                    currNode.getKey().compareTo(key) != 0
                            && currNode.getKey().compareTo(key) < 0 && currNode.getRight() != null
                            && currNode.getKey().compareTo(key) > 0 && currNode.getLeft() != null
            ) {
                if (currNode.getKey().compareTo(key) < 0) {
                    currNode = currNode.getRight();
                } else if (currNode.getKey().compareTo(key) > 0) {
                    currNode = currNode.getLeft();
                } else {
                    throw new IllegalStateException("[DEBUG] Just can't happen");
                }
            }
            return currNode;
        }


        public void print(EdxIO edxIO) {
            Node node = root;
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);
            int c = 1;
            while (queue.size() != 0) {
                node = queue.poll();
                edxIO.print(node.getKey() + " ");
                if (node.hasLeft()) {
                    queue.add(node.getLeft());
                    c++;
                    edxIO.print(c + " ");
                } else {
                    edxIO.print("0 ");
                }
                if (node.hasRight()) {
                    queue.add(node.getRight());
                    c++;
                    edxIO.print(c);
                } else {
                    edxIO.print("0");
                }
                edxIO.println();
            }
        }

        private void leftRotation(Node x) {
            Node parent = x.getParent();
            Node T1 = x.getLeft();
            Node y = x.getRight();
            Node T2 = y.getLeft();
            Node T3 = y.getRight();

            y.setParent(parent);
            if (parent != null) {
                if (x.equals(parent.getLeft())) {
                    parent.setLeft(y);
                } else {
                    parent.setRight(y);
                }
            } else {
                root = y;
            }

            y.setLeft(x);
            x.setParent(y);

            x.setLeft(T1);
            if (T1 != null) {
                T1.setParent(x);
            }

            x.setRight(T2);
            if (T2 != null) {
                T2.setParent(x);
            }

            y.setRight(T3);
            if (T3 != null) {
                T3.setParent(y);
            }

            y.setHeight(max(y));
            x.setHeight(max(x));
        }

        private long max(Node node) {
            return Long.max(
                    node.getLeft() == null ? 0 : node.getLeft().getHeight(),
                    node.getRight() == null ? 1 : node.getRight().getHeight() + 1
            );
        }

        private void rightRotation(Node y) {
            Node parent = y.getParent();
            Node x = y.getLeft();
            Node T3 = y.getRight();
            Node T1 = x.getLeft();
            Node T2 = x.getRight();

            x.setParent(parent);
            if (parent != null) {
                if (y.equals(parent.getLeft())) {
                    parent.setLeft(x);
                } else {
                    parent.setRight(x);
                }
            } else {
                root = x;
            }

            x.setRight(y);
            y.setParent(x);

            x.setLeft(T1);
            if (T1 != null) {
                T1.setParent(x);
            }

            y.setLeft(T2);
            if (T2 != null) {
                T2.setParent(y);
            }

            y.setRight(T3);
            if (T3 != null) {
                T3.setParent(y);
            }
        }

        public Node<T> getRoot() {
            return root;
        }

        public void setRoot(Node<T> root) {
            this.root = root;
        }

        private void bigLeftTurn(Node z) {
            Node y = z.getRight();
            rightRotation(y);
            leftRotation(z);
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

        public boolean hasLeft() {
            return left != null;
        }

        public boolean hasRight() {
            return right != null;
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
