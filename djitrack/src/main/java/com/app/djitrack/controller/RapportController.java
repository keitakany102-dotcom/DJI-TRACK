package com.app.djitrack.controller;

import com.app.djitrack.entity.Rapport;
import com.app.djitrack.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportController {

    private final RapportService service;

    @GetMapping
    public List<Rapport> getAll(){
        return service.getAll();
    }

    @PostMapping("/generer")
    public Rapport generer(){
        return service.genererRapport();
    }
}