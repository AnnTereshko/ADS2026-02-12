package by.it.group551003.tereshko.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class SourceScannerB {

    public static void main(String[] args) throws IOException {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;

        Path srcPath = Paths.get(src);
        List<FileData> fileList = new ArrayList<>();

        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    try {
                        byte[] rawBytes = Files.readAllBytes(file);

                        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
                        ByteBuffer byteBuffer = ByteBuffer.wrap(rawBytes);
                        CharBuffer charBuffer = decoder.decode(byteBuffer);
                        String content = charBuffer.toString();

                        if (content.contains("@Test") || content.contains("org.junit.Test"))
                            return FileVisitResult.CONTINUE;

                        StringBuilder sb = new StringBuilder();
                        String[] lines = content.split("\n", -1);
                        for (String line : lines) {
                            String trimmed = line.trim();
                            if (!trimmed.startsWith("package ") && !trimmed.startsWith("import "))
                                sb.append(line).append("\n");
                        }

                        String processed = sb.toString();
                        processed = removeComments(processed);
                        int start = 0;
                        while (start < processed.length() && processed.charAt(start) < 33)
                            start++;
                        int end = processed.length() - 1;
                        while (end >= start && processed.charAt(end) < 33)
                            end--;

                        if (start <= end)
                            processed = processed.substring(start, end + 1);
                        else
                            processed = "";

                        String[] finalLines = processed.split("\n", -1);
                        StringBuilder finalSb = new StringBuilder();
                        for (String line : finalLines)
                            if (!line.trim().isEmpty())
                                finalSb.append(line).append("\n");

                        processed = finalSb.toString();
                        byte[] bytes = processed.getBytes(StandardCharsets.UTF_8);
                        String relativePath = srcPath.relativize(file).toString();

                        fileList.add(new FileData(relativePath, bytes.length));

                    } catch (MalformedInputException _) {
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        fileList.sort(Comparator.comparingInt((FileData a) -> a.size).thenComparing(a -> a.path));

        for (FileData fd : fileList)
            System.out.println(fd.size + " " + fd.path);
    }

    private static String removeComments(String code) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        int len = code.length();

        while (i < len) {
            if (i + 1 < len && code.charAt(i) == '/' && code.charAt(i + 1) == '/') {
                while (i < len && code.charAt(i) != '\n')
                    i++;
            } else if (i + 1 < len && code.charAt(i) == '/' && code.charAt(i + 1) == '*') {
                i += 2;
                while (i + 1 < len && !(code.charAt(i) == '*' && code.charAt(i + 1) == '/'))
                    i++;
                if (i + 1 < len)
                    i += 2;
            } else if (code.charAt(i) == '"') {
                result.append(code.charAt(i));
                i++;
                while (i < len && code.charAt(i) != '"') {
                    if (code.charAt(i) == '\\' && i + 1 < len) {
                        result.append(code.charAt(i));
                        i++;
                    }
                    result.append(code.charAt(i));
                    i++;
                }
                if (i < len) {
                    result.append(code.charAt(i));
                    i++;
                }
            } else if (code.charAt(i) == '\'') {
                result.append(code.charAt(i));
                i++;
                while (i < len && code.charAt(i) != '\'') {
                    if (code.charAt(i) == '\\' && i + 1 < len) {
                        result.append(code.charAt(i));
                        i++;
                    }
                    result.append(code.charAt(i));
                    i++;
                }
                if (i < len) {
                    result.append(code.charAt(i));
                    i++;
                }
            } else {
                result.append(code.charAt(i));
                i++;
            }
        }

        return result.toString();
    }

    record FileData(String path, int size) {
    }
}