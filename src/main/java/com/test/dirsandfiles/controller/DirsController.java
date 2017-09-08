package com.test.dirsandfiles.controller;

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
import java.io.IOException;
import java.util.List;

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
        return subDirRepository.findAll(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void deleteDir(@PathVariable("id") int id) {
        service.deleteDir(id);
    }

    @PostMapping
    @ResponseBody
    public void insertPath(@Valid PathTo pathTo, final BindingResult result) throws IOException {
        if (!result.hasErrors())
            service.save(pathTo.getPath());
        else throw new ValidationException("Неверный путь к каталогу");
    }
}
