package by.it.group551003.tereshko.lesson13;

import java.util.*;

public class GraphB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            System.out.println("no");
            return;
        }
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("no");
            return;
        }
        String[] edgeStrings = input.split("\\s*,\\s*");
        Map<String, List<String>> adj = new HashMap<>();
        Set<String> vertices = new HashSet<>();
        for (String edgeStr : edgeStrings) {
            String[] parts = edgeStr.split("\\s*->\\s*");
            if (parts.length == 2) {
                String u = parts[0].trim();
                String v = parts[1].trim();
                vertices.add(u);
                vertices.add(v);
                adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            }
        }
        Map<String, Integer> color = new HashMap<>();
        for (String v : vertices)
            color.put(v, 0);
        boolean hasCycle = false;
        for (String v : vertices)
            if (color.get(v) == 0)
                if (dfs(v, adj, color)) {
                    hasCycle = true;
                    break;
                }
        if (hasCycle)
            System.out.println("yes");
        else
            System.out.println("no");
        scanner.close();
    }

    private static boolean dfs(String u, Map<String, List<String>> adj, Map<String, Integer> color) {
        color.put(u, 1);
        if (adj.containsKey(u))
            for (String v : adj.get(u)) {
                if (color.get(v) == 1)
                    return true;
                if (color.get(v) == 0)
                    if (dfs(v, adj, color))
                        return true;
            }
        color.put(u, 2);
        return false;
    }
}
