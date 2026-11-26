package by.it.group551003.tereshko.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return;
        String[] edgeStrings = input.split("\\s*,\\s*");
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Set<String> vertices = new HashSet<>();
        for (String edgeStr : edgeStrings) {
            String[] parts = edgeStr.split("\\s*->\\s*");
            if (parts.length == 2) {
                String u = parts[0].trim();
                String v = parts[1].trim();
                vertices.add(u);
                vertices.add(v);
                adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
                inDegree.putIfAbsent(u, 0);
            }
        }
        for (String v : vertices)
            inDegree.putIfAbsent(v, 0);
        PriorityQueue<String> pq = new PriorityQueue<>();
        for (String v : vertices)
            if (inDegree.get(v) == 0)
                pq.add(v);
        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            String u = pq.poll();
            result.add(u);
            if (adj.containsKey(u))
                for (String v : adj.get(u)) {
                    inDegree.put(v, inDegree.get(v) - 1);
                    if (inDegree.get(v) == 0)
                        pq.add(v);
                }
        }
        System.out.println(String.join(" ", result));
        scanner.close();
    }
}
