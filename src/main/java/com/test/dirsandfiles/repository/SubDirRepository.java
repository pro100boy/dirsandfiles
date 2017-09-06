package com.test.dirsandfiles.repository;

import com.test.dirsandfiles.model.SubDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SubDirRepository extends JpaRepository<SubDir, Integer> {
    @Query("SELECT s FROM SubDir s WHERE s.parentdir.id =:id")
    List<SubDir> findAll(@Param("id") int id);
}
