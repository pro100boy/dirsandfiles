package com.test.dirsandfiles.service;

import com.test.dirsandfiles.model.ParentDir;

import java.util.List;

public interface ParentDirService {
    ParentDir save(ParentDir parentDir);

    List<ParentDir> getAll();

    void deleteDir(int id);
}
