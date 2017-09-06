package com.test.dirsandfiles.repository;

import com.test.dirsandfiles.model.ParentDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ParentDirRepository extends JpaRepository<ParentDir, Integer> {

}
