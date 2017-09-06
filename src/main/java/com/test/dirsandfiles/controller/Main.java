package com.test.dirsandfiles.controller;

import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.model.SubDir;
import com.test.dirsandfiles.repository.SubDirRepository;
import com.test.dirsandfiles.service.ParentDirService;
import com.test.dirsandfiles.to.PathTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.test.dirsandfiles.controller.Main.URL;

@Controller
@RequestMapping(value = URL)
public class Main {
    public static final String URL = "/dirs_and_files";
    private final PathTo pathTo = new PathTo();

    @Autowired
    private ParentDirService service;

    @Autowired
    private SubDirRepository subDirRepository;

    @ModelAttribute("parentDirs")
    public List<ParentDir> getAll() {
        return service.getAll();
    }

    @ModelAttribute("pathTo")
    public PathTo getPathTo()
    {
        return pathTo;
    }

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping(value = "/{id}/subdirs")
    //https://stackoverflow.com/a/5526817/7203956
    public List<SubDir> getSubdirs(@PathVariable("id") int id) {
        return subDirRepository.findAll(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteDir(@PathVariable("id") int id) {
        service.deleteDir(id);
        return "redirect:" + URL;
    }

    @PostMapping
    public String insertPath(@ModelAttribute(value = "pathTo") PathTo pathTo) {
        ParentDir parentDir = new ParentDir();
        parentDir.setPath(pathTo.getPath());
        System.out.println(service.save(parentDir));
        return "redirect:" + URL;
    }
}
