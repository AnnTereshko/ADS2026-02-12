package by.it.group551003.tereshko.lesson14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SitesB {

    static class DSU {
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> size = new HashMap<>();

        void makeSet(String x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                size.put(x, 1);
            }
        }

        String find(String x) {
            makeSet(x);
            if (parent.get(x).equals(x)) return x;

            String root = find(parent.get(x));
            parent.put(x, root);
            return root;
        }

        void union(String x, String y) {
            String rootX = find(x);
            String rootY = find(y);

            if (!rootX.equals(rootY)) {
                int sizeX = size.get(rootX);
                int sizeY = size.get(rootY);
                if (sizeX < sizeY) {
                    parent.put(rootX, rootY);
                    size.put(rootY, sizeY + sizeX);
                } else {
                    parent.put(rootY, rootX);
                    size.put(rootX, sizeX + sizeY);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();
        while (scanner.hasNext()) {
            String line = scanner.next().trim();
            if (line.equals("end")) break;

            String[] parts = line.split("\\+");
            if (parts.length == 2)
                dsu.union(parts[0], parts[1]);
        }

        List<Integer> clusterSizes = new ArrayList<>();
        for (String key : dsu.parent.keySet())
            if (dsu.parent.get(key).equals(key))
                clusterSizes.add(dsu.size.get(key));

        clusterSizes.sort((a, b) -> b - a);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clusterSizes.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(clusterSizes.get(i));
        }
        System.out.println(sb.toString());

        scanner.close();
    }
}