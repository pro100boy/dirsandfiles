package com.test.dirsandfiles.util;

import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.model.SubDir;
import com.test.dirsandfiles.util.formatter.LongToStringConverter;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.test.dirsandfiles.util.DirFileNamesComparator.comparator;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    private final Set<String> dirs = new TreeSet<>(comparator);
    private final Map<String, Long> files = new TreeMap<>(comparator);
    private final Converter<Long, String> converter = new LongToStringConverter();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isDirectory())
            dirs.add(file.getFileName().toString());
        if (attrs.isRegularFile())
            files.put(file.getFileName().toString(), attrs.size());

        return super.visitFile(file, attrs);
    }

    public ParentDir getDirInfo(String path, MyFileVisitor myFileVisitor) throws IOException {
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        // throws IOException if path is wrong
        Files.walkFileTree(Paths.get(path), options, 1, myFileVisitor);
        ParentDir parentDir = new ParentDir();
        parentDir.setPath(path);
        parentDir.setDircount(dirs.size());
        parentDir.setFilescount(files.keySet().size());
        parentDir.setDate(LocalDateTime.now());
        parentDir.setSize(converter.convert(files.values().stream().mapToLong(Number::longValue).sum()));
        List<SubDir> subDirList = dirs.stream().map(s -> new SubDir(null, s, "DIR", parentDir)).collect(Collectors.toList());
        files.forEach((key, value) -> subDirList.add(new SubDir(null, key, converter.convert(value), parentDir)));

        parentDir.setSubdirs(subDirList);
        return parentDir;
    }
}