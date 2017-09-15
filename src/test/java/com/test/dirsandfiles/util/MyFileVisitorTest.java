package com.test.dirsandfiles.util;

import com.test.dirsandfiles.model.ParentDir;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyFileVisitorTest {
    private MyFileVisitor myFileVisitor;
    private ParentDir parentDir;

    @Before
    public void setUp() throws Exception {
        myFileVisitor = new MyFileVisitor();
    }

    @After
    public void tearDown()
    {
        System.out.println(parentDir);

        parentDir.getSubdirs().forEach(System.out::println);
    }

    @Test
    public void getDirInfo_matches() throws Exception {
        parentDir = myFileVisitor.getDirInfo("c:\\Users\\User\\Downloads\\1", myFileVisitor);
    }

    @Test
    public void getDirInfo2_nonmatches() throws Exception {
        parentDir = myFileVisitor.getDirInfo("c:\\Users\\User\\Downloads\\CRUD\\AngularJS Essential", myFileVisitor);
    }
}