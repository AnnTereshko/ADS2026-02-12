package by.it.group551003.tereshko.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class SourceScannerC {

    private static final int MAX_EDITS = 10;

    public static void main(String[] args) throws IOException {
        String src = System.getProperty("user.dir")
                + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);
        List<FileData> files = new ArrayList<>();

        Files.walkFileTree(srcPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".java")) return FileVisitResult.CONTINUE;
                try {
                    CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                            .onMalformedInput(CodingErrorAction.REPORT);
                    CharBuffer cb = decoder.decode(ByteBuffer.wrap(Files.readAllBytes(file)));
                    String content = cb.toString();

                    if (content.contains("@Test") || content.contains("org.junit.Test"))
                        return FileVisitResult.CONTINUE;

                    // 1. package/import без regex-split
                    StringBuilder sb = new StringBuilder(content.length());
                    int pos = 0, len = content.length();
                    while (pos <= len) {
                        int nl = content.indexOf('\n', pos);
                        String line = (nl < 0) ? content.substring(pos) : content.substring(pos, nl);
                        String t = line.trim();
                        if (!t.startsWith("package ") && !t.startsWith("import "))
                            sb.append(line).append('\n');
                        if (nl < 0) break;
                        pos = nl + 1;
                    }

                    // 2-4. комментарии + нормализация <33 -> пробел + trim, за 1 проход по char[]
                    char[] norm = normalize(removeComments(sb.toString().toCharArray()));
                    int s = 0, e = norm.length - 1;
                    while (s <= e && norm[s] == ' ') s++;
                    while (e >= s && norm[e] == ' ') e--;
                    char[] text = (s <= e) ? Arrays.copyOfRange(norm, s, e + 1) : new char[0];

                    files.add(new FileData(srcPath.relativize(file).toString(), text));
                } catch (MalformedInputException _) {
                }
                return FileVisitResult.CONTINUE;
            }
        });

        files.sort(Comparator.comparingInt(f -> f.text.length));

        Map<String, Set<String>> copies = new LinkedHashMap<>();
        for (int i = 0; i < files.size(); i++) {
            FileData fi = files.get(i);
            for (int j = i + 1; j < files.size(); j++) {
                FileData fj = files.get(j);
                if (fj.text.length - fi.text.length >= MAX_EDITS) break;

                int dist;
                if (fi.text.length == fj.text.length && Arrays.equals(fi.text, fj.text))
                    dist = 0;
                else
                    dist = levenshteinBanded(fi.text, fj.text, MAX_EDITS);

                if (dist < MAX_EDITS) {
                    copies.computeIfAbsent(fi.path, k -> new TreeSet<>()).add(fj.path);
                    copies.computeIfAbsent(fj.path, k -> new TreeSet<>()).add(fi.path);
                }
            }
        }

        new TreeMap<>(copies).forEach((path, set) -> {
            System.out.println(path);
            set.forEach(c -> System.out.println("  " + c));
        });
    }

    /**
     * Заменяет последовательности <33 на один пробел.
     */
    private static char[] normalize(char[] in) {
        char[] out = new char[in.length];
        int w = 0;
        boolean inSpace = false;
        for (char c : in) {
            if (c < 33) {
                if (!inSpace) {
                    out[w++] = ' ';
                    inSpace = true;
                }
            } else {
                out[w++] = c;
                inSpace = false;
            }
        }
        return Arrays.copyOf(out, w);
    }

    private static char[] removeComments(char[] code) {
        StringBuilder r = new StringBuilder(code.length);
        int i = 0, len = code.length;
        while (i < len) {
            char c = code[i];
            if (i + 1 < len && c == '/' && code[i + 1] == '/') {
                while (i < len && code[i] != '\n') i++;
            } else if (i + 1 < len && c == '/' && code[i + 1] == '*') {
                i += 2;
                while (i + 1 < len && !(code[i] == '*' && code[i + 1] == '/')) i++;
                if (i + 1 < len) i += 2;
            } else if (c == '"' || c == '\'') {
                r.append(c);
                i++;
                while (i < len && code[i] != c) {
                    if (code[i] == '\\' && i + 1 < len) r.append(code[i++]);
                    r.append(code[i++]);
                }
                if (i < len) r.append(code[i++]);
            } else r.append(code[i++]);
        }
        return r.toString().toCharArray();
    }

    /**
     * Banded Levenshtein. Сложность O(n * maxEdits).
     * Граничные ячейки полосы подделываются за O(1) на строку —
     * никаких O(m)-циклов fill.
     */
    private static int levenshteinBanded(char[] a, char[] b, int k) {
        int n = a.length, m = b.length;
        if (Math.abs(n - m) >= k) return k;

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        // строка i=0: d(0,j)=j, всё >=k обрезаем до k (точные значения там не нужны)
        for (int j = 0; j <= m; j++) prev[j] = Math.min(j, k);

        for (int i = 1; i <= n; i++) {
            int jMin = Math.max(1, i - k + 1);
            int jMax = Math.min(m, i + k - 1);

            // левый край текущей строки (нужен как curr[j-1] при j=jMin)
            if (jMin > 1) curr[jMin - 1] = k;
            else curr[0] = Math.min(i, k);

            char ca = a[i - 1];
            int minInRow = k;
            for (int j = jMin; j <= jMax; j++) {
                int cost = (ca == b[j - 1]) ? 0 : 1;
                int v = Math.min(Math.min(prev[j] + 1, curr[j - 1] + 1), prev[j - 1] + cost);
                curr[j] = v;
                if (v < minInRow) minInRow = v;
            }

            // правый край станет prev[jMax] следующей строки (d(i-1, jMax) >= k)
            if (jMax < m) curr[jMax + 1] = k;

            if (minInRow >= k) return k;
            int[] t = prev;
            prev = curr;
            curr = t;
        }
        return prev[m];
    }

    record FileData(String path, char[] text) {
        int length() {
            return text.length;
        }
    }
}