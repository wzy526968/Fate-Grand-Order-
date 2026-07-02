package com.chaldea.controller;

import com.chaldea.model.ServantClass;
import com.chaldea.repository.ServantClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ServantClassRepository classRepository;

    public ClassController(ServantClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @GetMapping
    public ResponseEntity<List<ServantClass>> getAllClasses() {
        return ResponseEntity.ok(classRepository.findAll());
    }
}
