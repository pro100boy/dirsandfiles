package com.test.dirsandfiles.util;

import java.util.Comparator;
import java.util.regex.Pattern;

class DirFileNamesComparator {
    private static final Pattern pattern = Pattern.compile("(^\\D*)(\\d*\\D*)*(\\.[^.]+)$");

    static Comparator<String> comparator = (o1, o2) -> {
        int res = 0;
        if (pattern.matcher(o1).matches() && pattern.matcher(o2).matches()) {
            String[] o1parts = o1.split("[^A-Za-zА-Яа-я0-9]");
            String[] o2parts = o2.split("[^A-Za-zА-Яа-я0-9]");

            // в именах fre4, f008 выделяем буквы и сравниваем
            String f1name = o1parts[0].replaceAll("\\d", "");
            String f2name = o2parts[0].replaceAll("\\d", "");

            res = f1name.compareToIgnoreCase(f2name);
            if (res == 0) {
                // если буквы одинаковые, выделяем цифры
                String f1num = o1parts[0].replaceAll("\\D", "");
                String f2num = o2parts[0].replaceAll("\\D", "");
                // заносим цифры в группы чисел и сравниваем
                o1parts[0] = f1num.length() == 0 ? "0" : f1num;
                o2parts[0] = f2num.length() == 0 ? "0" : f2num;
            } else return res;

            for (int i = 0; i < Math.min(o1parts.length, o2parts.length); i++) {
                if (!o1parts[i].equalsIgnoreCase(o2parts[i]))
                    return Integer.compare(Integer.valueOf(o1parts[i]), Integer.valueOf(o2parts[i]));
            }
        } else return o1.compareToIgnoreCase(o2);

        return res;
    };
}
