package by.it.group551003.tereshko.lesson12;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    private static class Node {
        Integer key;
        String value;
        Node left;
        Node right;
        int height;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public MyAvlMap() {
        this.root = null;
        this.size = 0;
    }

    private int height(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node balance(Node node) {
        if (node == null) return null;

        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node insert(Node node, Integer key, String value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.equals(node.key)) {
            node.value = value;
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        } else
            node.right = insert(node.right, key, value);
        return balance(node);
    }

    private Node findMinNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node delete(Node node, Integer key) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            size--;
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null)
                    return null;
                else
                    return temp;
            } else {
                Node successor = findMinNode(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = delete(node.right, successor.key);
            }
        }
        return balance(node);
    }

    private String search(Node node, Integer key) {
        if (node == null) return null;
        if (key.equals(node.key)) return node.value;
        if (key.compareTo(node.key) < 0) return search(node.left, key);
        return search(node.right, key);
    }

    private boolean containsKeyHelper(Node node, Integer key) {
        if (node == null) return false;
        if (key.equals(node.key)) return true;
        if (key.compareTo(node.key) < 0) return containsKeyHelper(node.left, key);
        return containsKeyHelper(node.right, key);
    }

    private void inorderTraversal(StringBuilder sb, Node node) {
        if (node == null) return;
        inorderTraversal(sb, node.left);
        sb.append(node.key).append("=").append(node.value).append(", ");
        inorderTraversal(sb, node.right);
    }

    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(sb, root);
        if (sb.length() > 1)
            sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof Integer)) return false;
        return containsKeyHelper(root, (Integer) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer)) return null;
        return search(root, (Integer) key);
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        String oldValue = get(key);
        root = insert(root, key, value);
        return oldValue;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer)) return null;
        Integer intKey = (Integer) key;
        String oldValue = get(intKey);
        if (oldValue != null)
            root = delete(root, intKey);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of();
    }
}
