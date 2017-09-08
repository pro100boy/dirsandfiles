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

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private Comparator<String> fileNamesCompare = (o1, o2) -> {
        int res = 0;
        try {
            String[] o1parts = o1.split("[^A-Za-zА-Яа-я0-9]");
            String[] o2parts = o2.split("[^A-Za-zА-Яа-я0-9]");

            // в именах fre4, f008 выделяем буквы и сравниваем
            String f1name = o1parts[0].replaceAll("\\d", "");
            String f2name = o2parts[0].replaceAll("\\d", "");

            // если буквы одинаковые, выделяем цифры
            String f1num = o1parts[0].replaceAll("\\D", "");
            String f2num = o2parts[0].replaceAll("\\D", "");

            res = f1name.compareToIgnoreCase(f2name);
            if (res == 0) {
                // заносим цифры в группы чисел и сравниваем
                o1parts[0] = f1num.length() == 0 ? "0" : f1num;
                o2parts[0] = f2num.length() == 0 ? "0" : f2num;
            } else return res;

            for (int i = 0; i < Math.min(o1parts.length, o2parts.length); i++) {
                if (!o1parts[i].equalsIgnoreCase(o2parts[i]))
                    return Integer.compare(Integer.valueOf(o1parts[i]), Integer.valueOf(o2parts[i]));
            }
        } catch (Exception e) {
            res = o1.compareToIgnoreCase(o2);
        }
        return res;
    };

    private final Set<String> dirs = new TreeSet<>(fileNamesCompare);
    private final Map<String, Long> files = new TreeMap<>(fileNamesCompare);
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

        List<SubDir> subDirList = dirs.stream().map(s -> new SubDir(null, s, "<DIR>", parentDir)).collect(Collectors.toList());
        files.forEach((key, value) -> subDirList.add(new SubDir(null, key, converter.convert(value), parentDir)));

        parentDir.setSubdirs(subDirList);
        return parentDir;
    }

/*    public static void main(String[] args) throws IOException {
        final MyFileVisitor myFileVisitor = new MyFileVisitor();
        ParentDir parentDir = myFileVisitor.getDirInfo("c:\\TDMS_\\2", myFileVisitor);
        System.out.println(parentDir);

        parentDir.getSubdirs().forEach(System.out::println);
    }*/
}