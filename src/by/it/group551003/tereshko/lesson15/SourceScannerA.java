package by.it.group551003.tereshko.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class SourceScannerA {

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
                        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                                .onMalformedInput(CodingErrorAction.REPORT);
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

    record FileData(String path, int size) {
    }
}