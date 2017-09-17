package com.test.dirsandfiles.util.formatter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class LongToStringConverter implements Converter<Long, String> {
    @Override
    public String convert(@NonNull Long size) {
        if (size < 1024) return size + " b";
        else if (size < 1024 * 1024) return String.format("%.2f Kb", size / 1024f);
        else if (size < 1024 * 1024 * 1024) return String.format("%.2f Mb", size / 1024f / 1024f);
        else return String.format("%.2f Gb", size / 1024f / 1024f / 1024f);
    }
}
