package com.app.djitrack.controller;

import com.app.djitrack.entity.ReleveCompteur;
import com.app.djitrack.service.ReleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/releves")
@RequiredArgsConstructor
public class ReleveController {

    private final ReleveService service;

    @GetMapping
    public List<ReleveCompteur> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ReleveCompteur create(
            @RequestBody ReleveCompteur releve) {

        return service.save(releve);
    }
}