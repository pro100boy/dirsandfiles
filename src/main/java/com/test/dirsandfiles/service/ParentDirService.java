package com.test.dirsandfiles.service;

import com.test.dirsandfiles.model.ParentDir;

import java.io.IOException;
import java.util.List;

public interface ParentDirService {
    ParentDir save(String path) throws IOException;

    List<ParentDir> getAll();

    void deleteDir(int id);
}
