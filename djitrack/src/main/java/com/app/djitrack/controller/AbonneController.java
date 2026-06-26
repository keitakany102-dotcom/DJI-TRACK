package com.app.djitrack.controller;

import com.app.djitrack.entity.Abonne;
import com.app.djitrack.service.AbonneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abonnes")
@RequiredArgsConstructor
public class AbonneController {

    private final AbonneService service;

    @GetMapping
    public List<Abonne> getAll() {

        return service.findAll();
    }

    @PostMapping
    public Abonne create(
            @RequestBody Abonne abonne) {

        return service.save(abonne);
    }
}