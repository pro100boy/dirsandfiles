package com.test.dirsandfiles.service;

import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.model.SubDir;
import com.test.dirsandfiles.repository.ParentDirRepository;
import com.test.dirsandfiles.repository.SubDirRepository;
import com.test.dirsandfiles.util.MyFileVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    public ParentDir save(ParentDir parentDir) {
        parentDir.setDate(LocalDateTime.now());
        parentDir.setDircount(100);
        parentDir.setFilescount(500);
        parentDir.setSize("500 Mb");

        parentDir = parentDirRepository.save(parentDir);

        List<SubDir> subDirList = Arrays.asList(
                new SubDir(null, "path1" + parentDir.getId(), "15,6 mb", parentDirRepository.getOne(parentDir.getId())),
                new SubDir(null, "path2" + parentDir.getId(), "153,6 Kb", parentDirRepository.getOne(parentDir.getId())),
                new SubDir(null, "path3" + parentDir.getId(), "215,64 Gb", parentDirRepository.getOne(parentDir.getId())));
        subDirRepository.save(subDirList);

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
