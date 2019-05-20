package week8;

import mooc.EdxIO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Week_8_2 {

    private static Node tail;

    public static void main(String[] args) {
        EdxIO edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        Map<String, Node> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            switch (edxIO.next()) {
                case "get":
                    Node node = map.get(edxIO.next());
                    if (node == null) {
                        edxIO.println("<none>");
                    } else {
                        edxIO.println(node.getValue());
                    }
                    break;
                case "prev":
                    node = map.get(edxIO.next());
                    if (node != null && node.getPrev() != null) {
                        edxIO.println(node.getPrev().getValue());
                    } else {
                        edxIO.println("<none>");
                    }
                    break;
                case "next":
                    node = map.get(edxIO.next());
                    if (node != null && node.getNext() != null) {
                        edxIO.println(node.getNext().getValue());
                    } else {
                        edxIO.println("<none>");
                    }
                    break;
                case "put":
                    String key = edxIO.next();
                    String value = edxIO.next();
                    node = map.get(key);
                    if (node == null) {
                        node = new Node(key, value);
                        node.setPrev(tail);
                        if (tail != null) {
                            tail.setNext(node);
                        }
                        tail = node;
                        map.put(key, node);
                    } else {
                        node.setValue(value);
                    }
                    break;
                case "delete":
                    key = edxIO.next();
                    node = map.get(key);
                    if (node != null) {
                        map.remove(key);
                        Node prev = node.getPrev();
                        Node next = node.getNext();
                        if (prev != null) {
                            prev.setNext(next);
                        }
                        if (next != null) {
                            next.setPrev(prev);
                        } else if (node.equals(tail)) {
                            tail = prev;
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        edxIO.close();
    }

    static class Node {
        private Node prev;
        private Node next;
        private String key;
        private String value;

        public Node(String key, String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

    }


}
