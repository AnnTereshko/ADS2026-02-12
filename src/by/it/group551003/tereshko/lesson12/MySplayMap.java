package by.it.group551003.tereshko.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {
//                toString()

    private static class Node {
        Integer key;
        String value;
        Node left;
        Node right;
        Node parent;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private void rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null)
            root = x;
        else if (y == y.parent.left) {
            y.parent.left = x;
        } else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left) {
            x.parent.left = y;
        } else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void splay(Node x) {
        if (x == null) return;
        while (x.parent != null) {
            Node p = x.parent;
            Node g = p.parent;
            if (g == null) {
                if (x == p.left) rotateRight(p);
                else rotateLeft(p);
            } else {
                if (x == p.left && p == g.left) {
                    rotateRight(g);
                    rotateRight(p);
                } else if (x == p.right && p == g.right) {
                    rotateLeft(g);
                    rotateLeft(p);
                } else if (x == p.left && p == g.right) {
                    rotateRight(p);
                    rotateLeft(g);
                } else {
                    rotateLeft(p);
                    rotateRight(g);
                }
            }
        }
        root = x;
    }

    private Node findNode(Integer key) {
        Node curr = root;
        Node last = null;
        while (curr != null) {
            last = curr;
            int cmp = key.compareTo(curr.key);
            if (cmp == 0)
                return curr;
            else if (cmp < 0) {
                curr = curr.left;
            } else
                curr = curr.right;
        }
        return last;
    }

    private Node findMin(Node node) {
        if (node == null) return null;
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node findMax(Node node) {
        if (node == null) return null;
        while (node.right != null)
            node = node.right;
        return node;
    }

    private Node root;
    private int size;

    public MySplayMap() {
        this.root = null;
        this.size = 0;
    }

    private void inorderToString(Node node, StringBuilder sb) {
        if (node == null) return;
        inorderToString(node.left, sb);
        sb.append(node.key).append("=").append(node.value).append(", ");
        inorderToString(node.right, sb);
    }

    public String toString() {
        if (isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{");
        inorderToString(root, sb);
        sb.setLength(sb.length() - 2); // Убираем последнюю ", "
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    private Node findLower(Integer key) {
        Node curr = root;
        Node best = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp > 0) {
                best = curr;
                curr = curr.right;
            } else
                curr = curr.left;
        }
        if (best != null) splay(best);
        return best;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node best = findLower(key);
        return best != null ? best.key : null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    private Node findFloor(Integer key) {
        Node curr = root;
        Node best = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp == 0) {
                best = curr;
                break;
            } else if (cmp > 0) {
                best = curr;
                curr = curr.right;
            } else
                curr = curr.left;
        }
        if (best != null) splay(best);
        return best;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node best = findFloor(key);
        return best != null ? best.key : null;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    private Node findCeiling(Integer key) {
        Node curr = root;
        Node best = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp == 0) {
                best = curr;
                break;
            } else if (cmp < 0) {
                best = curr;
                curr = curr.left;
            } else
                curr = curr.right;
        }
        if (best != null) splay(best);
        return best;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node best = findCeiling(key);
        return best != null ? best.key : null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    private Node findHigher(Integer key) {
        Node curr = root;
        Node best = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp < 0) {
                best = curr;
                curr = curr.left;
            } else
                curr = curr.right;
        }
        if (best != null) splay(best);
        return best;
    }

    @Override
    public Integer higherKey(Integer key) {
        Node best = findHigher(key);
        return best != null ? best.key : null;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    private void collectHead(Node node, Integer toKey, boolean inclusive, MySplayMap result) {
        if (node == null) return;
        int cmp = node.key.compareTo(toKey);
        if (cmp < 0 || (inclusive && cmp == 0)) {
            result.put(node.key, node.value);
            collectHead(node.left, toKey, inclusive, result);
            collectHead(node.right, toKey, inclusive, result);
        } else
            collectHead(node.left, toKey, inclusive, result);
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        if (toKey == null) throw new NullPointerException("toKey cannot be null");
        MySplayMap result = new MySplayMap();
        collectHead(root, toKey, inclusive, result);
        return result;
    }

    private void collectTail(Node node, Integer fromKey, boolean inclusive, MySplayMap result) {
        if (node == null) return;
        int cmp = node.key.compareTo(fromKey);
        if (cmp > 0 || (inclusive && cmp == 0)) {
            result.put(node.key, node.value);
            collectTail(node.left, fromKey, inclusive, result);
            collectTail(node.right, fromKey, inclusive, result);
        } else {
            collectTail(node.right, fromKey, inclusive, result);
        }
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        if (fromKey == null) throw new NullPointerException("fromKey cannot be null");
        MySplayMap result = new MySplayMap();
        collectTail(root, fromKey, inclusive, result);
        return result;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        return headMap(toKey, false);
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        return tailMap(fromKey, true);
    }

    @Override
    public Integer firstKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        Node min = findMin(root);
        splay(min);
        return min.key;
    }

    @Override
    public Integer lastKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        Node max = findMax(root);
        splay(max);
        return max.key;
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
        if (key == null) return false;
        Node node = findNode((Integer) key);
        if (node != null && node.key.equals(key)) {
            splay(node);
            return true;
        }
        if (node != null) splay(node);
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValueHelper(root, value);
    }

    private boolean containsValueHelper(Node node, Object value) {
        if (node == null) return false;
        if (value == null ? node.value == null : value.equals(node.value)) return true;
        return containsValueHelper(node.left, value) || containsValueHelper(node.right, value);
    }

    @Override
    public String get(Object key) {
        if (key == null) return null;
        Node node = findNode((Integer) key);
        if (node != null && node.key.equals(key)) {
            splay(node);
            return node.value;
        }
        if (node != null) splay(node);
        return null;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        Node curr = root;
        Node parent = null;
        while (curr != null) {
            parent = curr;
            int cmp = key.compareTo(curr.key);
            if (cmp == 0) {
                String oldValue = curr.value;
                curr.value = value;
                splay(curr);
                return oldValue;
            } else if (cmp < 0) {
                curr = curr.left;
            } else
                curr = curr.right;
        }
        Node newNode = new Node(key, value);
        newNode.parent = parent;
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else
            parent.right = newNode;
        splay(newNode);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        if (key == null) return null;
        Node node = findNode((Integer) key);
        if (node == null || !node.key.equals(key)) {
            if (node != null) splay(node);
            return null;
        }
        splay(node);
        String oldValue = root.value;
        if (root.left == null) {
            root = root.right;
            if (root != null) root.parent = null;
        } else {
            Node maxLeft = findMax(root.left);
            splay(maxLeft);
            root.right = node.right;
            if (root.right != null)
                root.right.parent = root;
        }
        size--;
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
