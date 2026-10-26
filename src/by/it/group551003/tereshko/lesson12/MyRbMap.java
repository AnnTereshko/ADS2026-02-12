package by.it.group551003.tereshko.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {
//    headMap(Integer)
//    tailMap(Integer)

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Integer key;
        String value;
        Node left;
        Node right;
        Node parent;
        boolean color;

        Node(Integer key, String value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }
    }

    private final Node NIL;
    private Node root;
    private int size;

    public MyRbMap() {
        NIL = new Node(null, null, BLACK);
        root = NIL;
        size = 0;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != NIL)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else
            y.parent.left = x;
        x.right = y;
        y.parent = x;
    }

    private void fixInsert(Node z) {
        while (z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == RED) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y.color == RED) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == NIL)
            root = v;
        else if (u == u.parent.left) {
            u.parent.left = v;
        } else
            u.parent.right = v;
        v.parent = u.parent;
    }

    private void fixDelete(Node x) {
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == BLACK && w.left.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    private Node searchNode(Node node, Integer key) {
        while (node != NIL && !key.equals(node.key))
            if (key.compareTo(node.key) < 0)
                node = node.left;
            else
                node = node.right;
        return node;
    }

    private Node minimum(Node node) {
        while (node.left != NIL)
            node = node.left;
        return node;
    }

    private Node maximum(Node node) {
        while (node.right != NIL)
            node = node.right;
        return node;
    }

    private void inorderToString(Node node, StringBuilder sb) {
        if (node == NIL) return;
        inorderToString(node.left, sb);
        sb.append(node.key).append("=").append(node.value).append(", ");
        inorderToString(node.right, sb);
    }

    public String toString() {
        if (isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{");
        inorderToString(root, sb);
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    private void collectLessThan(Node node, Integer toKey, MyRbMap result) {
        if (node == NIL) return;
        if (node.key.compareTo(toKey) < 0) {
            result.put(node.key, node.value);
            collectLessThan(node.left, toKey, result);
            collectLessThan(node.right, toKey, result);
        } else
            collectLessThan(node.left, toKey, result);
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null) throw new NullPointerException("toKey cannot be null");
        MyRbMap result = new MyRbMap();
        collectLessThan(root, toKey, result);
        return result;
    }

    private void collectGreaterOrEqual(Node node, Integer fromKey, MyRbMap result) {
        if (node == NIL) return;
        if (node.key.compareTo(fromKey) >= 0) {
            result.put(node.key, node.value);
            collectGreaterOrEqual(node.left, fromKey, result);
            collectGreaterOrEqual(node.right, fromKey, result);
        } else
            collectGreaterOrEqual(node.right, fromKey, result);
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null) throw new NullPointerException("fromKey cannot be null");
        MyRbMap result = new MyRbMap();
        collectGreaterOrEqual(root, fromKey, result);
        return result;
    }

    @Override
    public Integer firstKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        return minimum(root).key;
    }

    @Override
    public Integer lastKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        return maximum(root).key;
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
        return searchNode(root, (Integer) key) != NIL;
    }

    private boolean containsValueHelper(Node node, Object value) {
        if (node == NIL) return false;
        if (value == null ? node.value == null : value.equals(node.value)) return true;
        return containsValueHelper(node.left, value) || containsValueHelper(node.right, value);
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValueHelper(root, value);
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer)) return null;
        Node node = searchNode(root, (Integer) key);
        return node != NIL ? node.value : null;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        Node y = NIL;
        Node x = root;
        while (x != NIL) {
            y = x;
            if (key.equals(x.key)) {
                String oldValue = x.value;
                x.value = value;
                return oldValue;
            } else if (key.compareTo(x.key) < 0) {
                x = x.left;
            } else
                x = x.right;
        }
        Node z = new Node(key, value, RED);
        z.parent = y;
        if (y == NIL) {
            root = z;
        } else if (key.compareTo(y.key) < 0) {
            y.left = z;
        } else
            y.right = z;
        fixInsert(z);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer)) return null;
        Integer k = (Integer) key;
        Node z = searchNode(root, k);
        if (z == NIL) return null;
        String oldValue = z.value;
        Node y = z;
        boolean yOriginalColor = y.color;
        Node x;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == BLACK)
            fixDelete(x);
        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        root = NIL;
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
