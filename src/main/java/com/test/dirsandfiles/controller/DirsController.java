package com.test.dirsandfiles.controller;

import com.test.dirsandfiles.model.BaseEntity;
import com.test.dirsandfiles.model.ParentDir;
import com.test.dirsandfiles.model.SubDir;
import com.test.dirsandfiles.repository.SubDirRepository;
import com.test.dirsandfiles.service.ParentDirService;
import com.test.dirsandfiles.to.PathTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.test.dirsandfiles.controller.DirsController.URL;

@Controller
@RequestMapping(value = URL)
public class DirsController {
    public static final String URL = "/dirs_and_files";

    @Autowired
    private ParentDirService service;

    @Autowired
    private SubDirRepository subDirRepository;

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<ParentDir> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}/subdirs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<SubDir> getSubdirs(@PathVariable("id") int id) {
        List<SubDir> all = subDirRepository.findAll(id);

        return all.stream().sorted(Comparator.comparingInt(BaseEntity::getId).reversed()).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void deleteDir(@PathVariable("id") int id) {
        service.deleteDir(id);
    }

    @PostMapping
    @ResponseBody
    public void insertPath(@Valid PathTo pathTo, final BindingResult result) {
        if (!result.hasErrors()) {
            ParentDir parentDir = new ParentDir();
            parentDir.setPath(pathTo.getPath());
            service.save(parentDir);
        } else throw new ValidationException("Неверный путь к каталогу");
    }
}
