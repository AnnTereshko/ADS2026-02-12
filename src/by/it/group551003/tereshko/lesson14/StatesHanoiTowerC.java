package by.it.group551003.tereshko.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    static int stepIndex = 0;

    static void hanoi(int n, int from, int to, int aux, int[] sizes, int[] stepMaxHeight) {
        if (n == 0) return;

        hanoi(n - 1, from, aux, to, sizes, stepMaxHeight);
        sizes[from]--;
        sizes[to]++;

        int max = sizes[0];
        if (sizes[1] > max) max = sizes[1];
        if (sizes[2] > max) max = sizes[2];

        stepMaxHeight[stepIndex++] = max;

        hanoi(n - 1, aux, to, from, sizes, stepMaxHeight);
    }

    static int find(int i, int[] parent) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i], parent);
    }

    static void union(int i, int j, int[] parent, int[] size) {
        int rootI = find(i, parent);
        int rootJ = find(j, parent);

        if (rootI != rootJ) {
            if (size[rootI] < size[rootJ]) {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            } else {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
        }
    }

    public static void main(String[] args) {
        stepIndex = 0;
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println();
            return;
        }

        int totalMoves = (1 << n) - 1;

        int[] stepMaxHeight = new int[totalMoves];
        int[] sizes = new int[3];
        sizes[0] = n;

        hanoi(n, 0, 1, 2, sizes, stepMaxHeight);

        int[] parent = new int[totalMoves];
        int[] size = new int[totalMoves];
        for (int i = 0; i < totalMoves; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int[] representative = new int[n + 1];
        for (int i = 0; i <= n; i++)
            representative[i] = -1;

        for (int i = 0; i < totalMoves; i++) {
            int h = stepMaxHeight[i];
            if (representative[h] != -1)
                union(i, representative[h], parent, size);
            else
                representative[h] = i;
        }

        int[] resultSizes = new int[n + 1];
        int resCount = 0;
        for (int i = 0; i < totalMoves; i++)
            if (parent[i] == i)
                resultSizes[resCount++] = size[i];

        for (int i = 1; i < resCount; i++) {
            int key = resultSizes[i];
            int j = i - 1;
            while (j >= 0 && resultSizes[j] > key) {
                resultSizes[j + 1] = resultSizes[j];
                j--;
            }
            resultSizes[j + 1] = key;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resCount; i++) {
            if (i > 0) sb.append(" ");
            sb.append(resultSizes[i]);
        }
        System.out.println(sb.toString());

        scanner.close();
    }
}