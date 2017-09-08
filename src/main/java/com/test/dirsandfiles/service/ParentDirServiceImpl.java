package com.test.dirsandfiles.service;

import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.repository.ParentDirRepository;
import com.test.dirsandfiles.repository.SubDirRepository;
import com.test.dirsandfiles.util.MyFileVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class ParentDirServiceImpl implements ParentDirService {
    @Autowired
    private MyFileVisitor myFileVisitor;

    @Autowired
    private ParentDirRepository parentDirRepository;

    @Autowired
    private SubDirRepository subDirRepository;

    @Override
    @Transactional
    public ParentDir save(String path) throws IOException {
        // TODO check files. Some files are not saving
        ParentDir parentDir = myFileVisitor.getDirInfo(path, myFileVisitor);
        parentDir = parentDirRepository.save(parentDir);

        subDirRepository.save(parentDir.getSubdirs());

        return parentDir;
    }

    @Override
    public List<ParentDir> getAll() {
        return parentDirRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteDir(int id) {
        parentDirRepository.delete(id);
    }
}
