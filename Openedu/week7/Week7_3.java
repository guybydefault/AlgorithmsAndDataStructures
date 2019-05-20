package week7;

import mooc.EdxIO;

import java.util.LinkedList;
import java.util.Queue;

public class Week7_3 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        Node<Long>[] nodes = new Node[n];

        AVLTree tree = new AVLTree(n);
        for (int i = 0; i < n; i++) {
            long key = edxIO.nextLong();
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
                    Node<Long> node = new Node<Long>();
                    nodes[left] = node;
                    node.setParent(nodes[i]);
                }
                nodes[i].setLeft(nodes[left]);
            }

            if (right != -1) {
                if (nodes[right] == null) {
                    Node<Long> node = new Node<Long>();
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

        long key = edxIO.nextLong();
        tree.insert(key);

        edxIO.println(tree.getSize());
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

        public Node<T> lookup(T key) {
            Node<T> currNode = root;
            while (currNode != null &&
                    currNode.getKey().compareTo(key) != 0
                    && (currNode.getKey().compareTo(key) < 0 && currNode.getRight() != null
                    || currNode.getKey().compareTo(key) > 0 && currNode.getLeft() != null)
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

        public boolean contains(T key) {
            Node<T> node = lookup(key);
            return node != null && key.compareTo(node.getKey()) == 0;
        }

        public void insert(T key) {
            if (isEmpty()) {
                root = new Node<>(key);
            } else {
                Node<T> lookupNode = lookup(key);
                Node<T> newNode = new Node<>(key, lookupNode);
                if (lookupNode.getKey().compareTo(key) < 0) {
                    lookupNode.setRight(newNode);
                } else if (lookupNode.getKey().compareTo(key) > 0) {
                    lookupNode.setLeft(newNode);
                } else {
                    return;
                }

                recalculateHeightsStartingFrom(newNode);

                Node currNode = lookupNode;
                while (currNode != null) {
                    currNode.setHeight(max(currNode));
                    int balance = getBalance(currNode);

                    if (balance < -1 && key.compareTo(currNode.getLeft().getKey()) < 0) {
                        rightRotation(currNode);
                    } else if (balance > 1 && key.compareTo(currNode.getRight().getKey()) > 0) {
                        leftRotation(currNode);
                    } else if (balance < -1 && key.compareTo(currNode.getLeft().getKey()) > 0) {
                        leftRotation(currNode.getLeft());
                        rightRotation(currNode);
                    } else if (balance > 1 && key.compareTo(currNode.getRight().getKey()) < 0) {
                        rightRotation(currNode.getRight());
                        leftRotation(currNode);
                    }
                    currNode = currNode.getParent();
                }
            }
            size++;
        }

        private void balanceStartingFrom(Node<T> node) {
            recalculateHeightsStartingFrom(node);

            Node currNode = node;
            while (currNode != null) {
                currNode.setHeight(max(currNode));
                int balance = getBalance(currNode);

                if (balance < -1 && getBalance(currNode.getLeft()) <= 0) {
                    rightRotation(currNode);
                } else if (balance > 1 && getBalance(currNode.getRight()) >= 0) {
                    leftRotation(currNode);
                } else if (balance < -1 && getBalance(currNode.getLeft()) > 0) {
                    leftRotation(currNode.getLeft());
                    rightRotation(currNode);
                } else if (balance > 1 && getBalance(currNode.getRight()) < 0) {
                    rightRotation(currNode.getRight());
                    leftRotation(currNode);
                }
                currNode = currNode.getParent();
            }
        }

        private void replaceChild(Node deleted, Node replace) {
            if (deleted.hasParent()) {
                if (deleted.equals(deleted.getParent().getLeft())) {
                    deleted.getParent().setLeft(replace);
                } else {
                    deleted.getParent().setRight(replace);
                }
            } else {
                root = replace;
            }
            replace.setParent(deleted.getParent());
        }

        private void deleteLeaf(Node leaf) {
            if (leaf.hasParent()) {
                if (leaf.equals(leaf.getParent().getLeft())) {
                    leaf.getParent().setLeft(null);
                } else {
                    leaf.getParent().setRight(null);
                }
            } else {
                root = null;
            }
        }

        private Node<T> findRightmost(Node<T> node) {
            while (node != null && node.hasRight()) {
                node = node.getRight();
            }
            return node;
        }

        public int getBalance(Node node) {
            if (node != null) {
                if (node.hasLeft() && node.hasRight()) {
                    return node.getRight().getHeight() - node.getLeft().getHeight();
                } else if (!node.hasLeft() && node.hasRight()) {
                    return node.getRight().getHeight() + 1;
                } else if (node.hasLeft()) {
                    // right == null
                    return -1 - node.getLeft().getHeight();
                }
            }
            return 0;
        }


        private void recalculateHeightsStartingFrom(Node<T> currentNode) {
            while (currentNode != null) {
                currentNode.setHeight(1 + Integer.max(
                        currentNode.getLeft() == null ? -1 : currentNode.getLeft().getHeight(),
                        currentNode.getRight() == null ? -1 : currentNode.getRight().getHeight()));
                currentNode = currentNode.getParent();
            }
        }

        public void print(EdxIO edxIO) {
            Node<T> node = root;
            Queue<Node<T>> queue = new LinkedList<>();
            if (node != null) {
                queue.add(node);
            }
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

        private int max(Node node) {
            return 1 + Integer.max(
                    node.getLeft() == null ? -1 : node.getLeft().getHeight(),
                    node.getRight() == null ? -1 : node.getRight().getHeight()
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

            y.setHeight(max(y));
            x.setHeight(max(x));
        }

        public Node<T> getRoot() {
            return root;
        }

        public void setRoot(Node<T> root) {
            this.root = root;
        }
    }

    static class Node<T> {
        private T key;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        private int height;

        Node() {

        }

        Node(T key) {
            this.key = key;
        }

        Node(T key, Node<T> parent) {
            this.key = key;
            this.parent = parent;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
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

    }
}