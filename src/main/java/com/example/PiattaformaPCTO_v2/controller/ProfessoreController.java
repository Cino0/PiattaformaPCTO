package com.example.PiattaformaPCTO_v2.controller;


import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.example.PiattaformaPCTO_v2.service.ProfessoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professori")
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
    @GetMapping("/stampa/")
    public String stampa(){
        return professoreService.stampa();
    }
}
