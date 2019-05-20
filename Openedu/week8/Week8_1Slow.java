package week8;

import mooc.EdxIO;

import java.util.LinkedList;
import java.util.Queue;

public class Week8_1Slow {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        HashMap hashMap = new HashMap<Long, Long>();
        for (int i = 0; i < n; i++) {
            switch (edxIO.nextChar()) {
                case 'A':
                    Long val = edxIO.nextLong();
                    hashMap.add(val, null);
                    break;
                case 'D':
                    hashMap.delete(edxIO.nextLong());
                    break;
                case '?':
                    edxIO.println(hashMap.contains(edxIO.nextLong()) ? 'Y' : 'N');
                    break;
            }
        }

        edxIO.close();

    }

    static class HashMap<T extends Comparable, V> {

        private ListNode head;
        private ListNode tail;
        private int size;

        private AVLTree<Entry<T, V>>[] hashTable = new AVLTree[Integer.MAX_VALUE / 50];

        private int hash(T key) {
            return Math.abs(key.hashCode() % hashTable.length);
        }

        public boolean contains(T key) {
            return hashTable[hash(key)] != null && hashTable[hash(key)].contains(new Entry<T, V>(key, null, null));
        }

        private Entry<T, V> get(T key) {
            AVLTree<Entry<T, V>> tree = hashTable[hash(key)];
            if (tree != null) {
                return tree.get(new Entry<>(key, null, null));
            } else {
                return null;
            }
        }

        public void add(T key, V value) {
            Entry<T, V> entry = get(key);
            if (entry != null) {
                entry.setValue(value);
            } else {
                if (hashTable[hash(key)] == null) {
                    hashTable[hash(key)] = new AVLTree<Entry<T, V>>();
                }
                AVLTree<Entry<T, V>> tree = hashTable[hash(key)];
                ListNode listNode = new ListNode(tail, null, null);
                entry = new Entry<>(key, value, listNode);
                listNode.setEntry(entry);
                if (size == 0) {
                    head = listNode;
                    tail = head;
                } else {
                    tail.setNext(listNode);
                    tail = listNode;
                }
                if (tree.insert(entry)) {

                } else {
                    throw new RuntimeException();
                }
                size++;
            }
        }

        public boolean delete(T key) {
            Entry<T, V> entry = get(key);
            if (entry == null) {
                return false;
            }
            AVLTree<Entry<T, V>> tree = hashTable[hash(key)];
            tree.delete(entry);

            ListNode prev = entry.getListNode().getPrev();
            ListNode next = entry.getListNode().getNext();
            if (prev != null) {
                prev.setNext(next);
            }
            if (next != null) {
                next.setPrev(prev);
            }

            size--;
            if (size == 0) {
                head = null;
                tail = null;
            }
            return true;
        }

    }

    private static class Entry<T extends Comparable, V> implements Comparable<Entry<T, V>> {
        private T key;
        private V value;
        private ListNode listNode;

        public Entry(T key, V value, ListNode listNode) {
            this.key = key;
            this.value = value;
            this.listNode = listNode;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public int compareTo(Entry<T, V> o) {
            return key.compareTo(o.getKey());
        }

        public void setValue(V value) {
            this.value = value;
        }

        public ListNode getListNode() {
            return listNode;
        }

        public void setListNode(ListNode listNode) {
            this.listNode = listNode;
        }
    }

    private static class ListNode {

        private ListNode prev;
        private ListNode next;
        private Entry entry;

        public ListNode(ListNode prev, ListNode next, Entry entry) {
            this.prev = prev;
            this.next = next;
            this.entry = entry;
        }

        public ListNode getPrev() {
            return prev;
        }

        public void setPrev(ListNode prev) {
            this.prev = prev;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public Entry getEntry() {
            return entry;
        }

        public void setEntry(Entry entry) {
            this.entry = entry;
        }
    }

    static class AVLTree<T extends Comparable> {
        private long size;
        private TreeNode<T> root;

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public TreeNode<T> lookup(T key) {
            TreeNode<T> currTreeNode = root;
            while (currTreeNode != null &&
                    currTreeNode.getKey().compareTo(key) != 0
                    && (currTreeNode.getKey().compareTo(key) < 0 && currTreeNode.getRight() != null
                    || currTreeNode.getKey().compareTo(key) > 0 && currTreeNode.getLeft() != null)
            ) {
                if (currTreeNode.getKey().compareTo(key) < 0) {
                    currTreeNode = currTreeNode.getRight();
                } else if (currTreeNode.getKey().compareTo(key) > 0) {
                    currTreeNode = currTreeNode.getLeft();
                } else {
                    throw new IllegalStateException("[DEBUG] Just can't happen");
                }
            }
            return currTreeNode;
        }

        public boolean contains(T key) {
            TreeNode<T> treeNode = lookup(key);
            return treeNode != null && key.compareTo(treeNode.getKey()) == 0;
        }

        public T get(T key) {
            TreeNode<T> treeNode = lookup(key);
            return treeNode != null && key.compareTo(treeNode.getKey()) == 0 ? treeNode.getKey() : null;
        }

        public boolean insert(T key) {
            if (isEmpty()) {
                root = new TreeNode<>(key);
            } else {
                TreeNode<T> lookupTreeNode = lookup(key);
                TreeNode<T> newTreeNode = new TreeNode<>(key, lookupTreeNode);
                if (lookupTreeNode.getKey().compareTo(key) < 0) {
                    lookupTreeNode.setRight(newTreeNode);
                } else if (lookupTreeNode.getKey().compareTo(key) > 0) {
                    lookupTreeNode.setLeft(newTreeNode);
                } else {
                    lookupTreeNode.setKey(key);
                    return false;
                }

                balanceStartingFrom(lookupTreeNode);
            }
            size++;
            return true;
        }

        private void balanceStartingFrom(TreeNode<T> treeNode) {
            TreeNode<T> currTreeNode = treeNode;
            while (currTreeNode != null) {
                currTreeNode.setHeight(calculateHeight(currTreeNode));
                int balance = getBalance(currTreeNode);

                if (balance < -1 && getBalance(currTreeNode.getLeft()) <= 0) {
                    rightRotation(currTreeNode);
                } else if (balance > 1 && getBalance(currTreeNode.getRight()) >= 0) {
                    leftRotation(currTreeNode);
                } else if (balance < -1 && getBalance(currTreeNode.getLeft()) > 0) {
                    leftRotation(currTreeNode.getLeft());
                    rightRotation(currTreeNode);
                } else if (balance > 1 && getBalance(currTreeNode.getRight()) < 0) {
                    rightRotation(currTreeNode.getRight());
                    leftRotation(currTreeNode);
                }
                currTreeNode = currTreeNode.getParent();
            }
        }

        private void deleteLeaf(TreeNode<T> leaf) {
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

        private TreeNode<T> findRightmost(TreeNode<T> treeNode) {
            while (treeNode != null && treeNode.hasRight()) {
                treeNode = treeNode.getRight();
            }
            return treeNode;
        }

        public boolean delete(T key) {
            TreeNode<T> treeNode = lookup(key);
            if (treeNode == null || treeNode.getKey().compareTo(key) != 0) {
                return false;
            }

            if (!treeNode.hasLeft() && !treeNode.hasRight()) {
                deleteLeaf(treeNode);
                if (treeNode.hasParent()) {
                    balanceStartingFrom(treeNode.getParent());
                } else {
                    // no children...
                    // no parents...
                    // root and leaf at the same time...
                    // left dying alone in this severe world...
                    // no replace...
                }
            } else if (!treeNode.hasLeft()) {
                treeNode.setKey(treeNode.getRight().getKey());
                deleteLeaf(treeNode.getRight());
                balanceStartingFrom(treeNode);
            } else {
                TreeNode<T> rightmostInLeftSubtree = findRightmost(treeNode.getLeft());
                treeNode.setKey(rightmostInLeftSubtree.getKey());
                if (rightmostInLeftSubtree.hasLeft()) {
                    // then it's the left child of deleted treeNode (there's no rightmost) and his left part should be current treeNode's left
                    if (rightmostInLeftSubtree.equals(rightmostInLeftSubtree.getParent().getLeft())) {
                        rightmostInLeftSubtree.getParent().setLeft(rightmostInLeftSubtree.getLeft());
                    } else {
                        rightmostInLeftSubtree.getParent().setRight(rightmostInLeftSubtree.getLeft());
                    }
                    rightmostInLeftSubtree.getLeft().setParent(rightmostInLeftSubtree.getParent());
                } else {
                    deleteLeaf(rightmostInLeftSubtree);
                }
                balanceStartingFrom(rightmostInLeftSubtree.getParent());
            }

            size--;
            return true;
        }

        public int getBalance(TreeNode<T> treeNode) {
            if (treeNode != null) {
                if (treeNode.hasLeft() && treeNode.hasRight()) {
                    return treeNode.getRight().getHeight() - treeNode.getLeft().getHeight();
                } else if (!treeNode.hasLeft() && treeNode.hasRight()) {
                    return treeNode.getRight().getHeight() + 1;
                } else if (treeNode.hasLeft()) {
                    // right == null
                    return -1 - treeNode.getLeft().getHeight();
                }
            }
            return 0;
        }

        public void print(EdxIO edxIO) {
            TreeNode<T> treeNode = root;
            Queue<TreeNode<T>> queue = new LinkedList<>();
            if (treeNode != null) {
                queue.add(treeNode);
            }
            int c = 1;
            while (queue.size() != 0) {
                treeNode = queue.poll();
                edxIO.print(treeNode.getKey() + " ");
                if (treeNode.hasLeft()) {
                    queue.add(treeNode.getLeft());
                    c++;
                    edxIO.print(c + " ");
                } else {
                    edxIO.print("0 ");
                }
                if (treeNode.hasRight()) {
                    queue.add(treeNode.getRight());
                    c++;
                    edxIO.print(c);
                } else {
                    edxIO.print("0");
                }
                edxIO.println();
            }
        }

        private void leftRotation(TreeNode<T> x) {
            TreeNode<T> parent = x.getParent();
            TreeNode<T> T1 = x.getLeft();
            TreeNode<T> y = x.getRight();
            TreeNode<T> T2 = y.getLeft();
            TreeNode<T> T3 = y.getRight();

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

            y.setHeight(calculateHeight(y));
            x.setHeight(calculateHeight(x));
        }

        private int calculateHeight(TreeNode<T> treeNode) {
            return 1 + Integer.max(
                    treeNode.getLeft() == null ? -1 : treeNode.getLeft().getHeight(),
                    treeNode.getRight() == null ? -1 : treeNode.getRight().getHeight()
            );
        }

        private void rightRotation(TreeNode<T> y) {
            TreeNode<T> parent = y.getParent();
            TreeNode<T> x = y.getLeft();
            TreeNode<T> T3 = y.getRight();
            TreeNode<T> T1 = x.getLeft();
            TreeNode<T> T2 = x.getRight();

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

            y.setHeight(calculateHeight(y));
            x.setHeight(calculateHeight(x));
        }

        public TreeNode<T> getRoot() {
            return root;
        }

        public void setRoot(TreeNode<T> root) {
            this.root = root;
        }
    }

    static class TreeNode<T> {
        private T key;
        private TreeNode<T> parent;
        private TreeNode<T> left;
        private TreeNode<T> right;

        private int height;

        TreeNode(T key) {
            this.key = key;
        }

        TreeNode(T key, TreeNode<T> parent) {
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

        public TreeNode<T> getParent() {
            return parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        public void setRight(TreeNode<T> right) {
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
