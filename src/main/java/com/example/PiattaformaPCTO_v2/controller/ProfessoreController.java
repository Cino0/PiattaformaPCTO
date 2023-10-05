package com.example.PiattaformaPCTO_v2.controller;


import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.example.PiattaformaPCTO_v2.service.ProfessoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professori")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class ProfessoreController {


    @Autowired
    private ProfessoreService professoreService;

    @PostMapping
    public String save(@RequestBody Professore professore){
        return professoreService.save(professore);
    }
    @GetMapping("/upload")
    public String save(){
        return professoreService.upload();
    }
    @GetMapping("/stampa")
    public String stampa(){
        return professoreService.stampa();
    }

    @GetMapping("/get")
    public ResponseEntity<List<Professore>> getProf(){
        List<Professore> p = this.professoreService.getAllProf();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
