package by.it.group551003.tereshko.lesson13;

import java.util.*;

public class GraphC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return;
        String[] edgeStrings = input.split("\\s*,\\s*");
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, List<String>> revAdj = new HashMap<>();
        Set<String> vertices = new HashSet<>();
        for (String edgeStr : edgeStrings) {
            String[] parts = edgeStr.split("\\s*->\\s*");
            if (parts.length == 2) {
                String u = parts[0].trim();
                String v = parts[1].trim();
                vertices.add(u);
                vertices.add(v);
                adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                revAdj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            }
        }
        for (String v : vertices) {
            adj.putIfAbsent(v, new ArrayList<>());
            revAdj.putIfAbsent(v, new ArrayList<>());
        }
        Set<String> visited = new HashSet<>();
        List<String> order = new ArrayList<>();
        for (String v : vertices)
            if (!visited.contains(v))
                dfs1(v, adj, visited, order);
        visited.clear();
        List<List<String>> sccs = new ArrayList<>();
        for (int i = order.size() - 1; i >= 0; i--) {
            String v = order.get(i);
            if (!visited.contains(v)) {
                List<String> component = new ArrayList<>();
                dfs2(v, revAdj, visited, component);
                Collections.sort(component);
                sccs.add(component);
            }
        }
        for (List<String> scc : sccs)
            System.out.println(String.join("", scc));
        scanner.close();
    }

    private static void dfs1(String u, Map<String, List<String>> adj, Set<String> visited, List<String> order) {
        visited.add(u);
        for (String v : adj.get(u))
            if (!visited.contains(v))
                dfs1(v, adj, visited, order);
        order.add(u);
    }

    private static void dfs2(String u, Map<String, List<String>> revAdj, Set<String> visited, List<String> component) {
        visited.add(u);
        component.add(u);
        for (String v : revAdj.get(u))
            if (!visited.contains(v))
                dfs2(v, revAdj, visited, component);
    }
}