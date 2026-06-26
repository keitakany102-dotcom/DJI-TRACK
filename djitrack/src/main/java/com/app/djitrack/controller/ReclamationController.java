package com.app.djitrack.controller;

import com.app.djitrack.entity.Reclamation;
import com.app.djitrack.service.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

    private final ReclamationService service;

    @GetMapping
    public List<Reclamation> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Reclamation create(
            @RequestBody Reclamation reclamation){

        return service.save(reclamation);
    }
}