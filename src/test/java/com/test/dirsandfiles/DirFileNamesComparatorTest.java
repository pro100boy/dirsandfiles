package com.test.dirsandfiles;

import com.test.dirsandfiles.model.NamedEntity;
import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.model.SubDir;
import com.test.dirsandfiles.util.formatter.LongToStringConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.test.dirsandfiles.util.DirFileNamesComparator.comparator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirFileNamesComparatorTest {
    private final Set<String> dirs = new TreeSet<>(comparator);
    private final Map<String, Long> files = new TreeMap<>(comparator);
    private final Converter<Long, String> converter = new LongToStringConverter();
    private final ParentDir parentDir = new ParentDir();

    @Before
    public void setUp() throws Exception {
        dirs.add("innerTemp");
        dirs.add("X-FILES");

        files.put("f.txt", 4_382L);
        files.put("function.cpp", 3_570L);
        files.put("f0008.doc", 26_000_000L);
        files.put("F4_00127.pdf", 30_000_000L);
        files.put("F4_99.JPG", 1_520L);
        files.put("F1.txt", 12_570L);

        parentDir.setPath("/opt/tomcat/temp");
        parentDir.setDate(LocalDateTime.of(2016, Month.SEPTEMBER, 14, 18, 3, 0));
        parentDir.setFilescount(files.keySet().size());
        parentDir.setDircount(dirs.size());
        parentDir.setSize(converter.convert(files.values().stream().mapToLong(Number::longValue).sum()));

        // add dirs
        List<SubDir> subDirList = dirs.stream().map(s -> new SubDir(null, s, "<DIR>", parentDir)).collect(Collectors.toList());
        // add files
        files.forEach((key, value) -> subDirList.add(new SubDir(null, key, converter.convert(value), parentDir)));

        parentDir.setSubdirs(subDirList);
    }

    @Test
    public void comparatorTest()
    {
        assertEquals(parentDir.getSize(), "53,43 Mb");
        assertEquals((long)parentDir.getDircount(), 2);
        assertEquals((long)parentDir.getFilescount(), 6);

        Object[] actual = parentDir.getSubdirs().stream().map(NamedEntity::getPath).toArray();
        Object[] expected = {"innerTemp", "X-FILES", "f.txt", "F1.txt", "F4_99.JPG", "F4_00127.pdf", "f0008.doc", "function.cpp"};
        assertTrue(Arrays.equals(actual, expected));
    }
}