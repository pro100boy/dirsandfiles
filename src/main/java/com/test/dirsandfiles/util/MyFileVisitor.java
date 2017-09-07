package com.test.dirsandfiles.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Component
public class MyFileVisitor extends SimpleFileVisitor<Path> {
    List<String> dirs = new LinkedList<>();
    Map<String, Long> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isDirectory())
            dirs.add(file.getFileName().toString());

        if (attrs.isRegularFile())
            files.put(file.getFileName().toString(), attrs.size());

        return super.visitFile(file, attrs);
    }

    public static void main(String[] args) throws IOException {
        final MyFileVisitor myFileVisitor = new MyFileVisitor();
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(Paths.get("c:\\TDMS_"), options, 1, myFileVisitor);

        System.out.println("Директорий: " + myFileVisitor.dirs.size());
        myFileVisitor.dirs.forEach(System.out::println);
        System.out.println("----");
        System.out.println("Файлов: " + myFileVisitor.files.size());
        System.out.println(myFileVisitor.files);
        System.out.println("----");
        System.out.println("Размер файлов: " + myFileVisitor.files.values().stream().mapToLong(Number::longValue).sum());
    }
}