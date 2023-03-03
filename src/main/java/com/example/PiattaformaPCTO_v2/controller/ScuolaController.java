package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.service.ScuolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scuola")
public class ScuolaController {

    @Autowired
    private ScuolaService scuolaService;


    @PostMapping
    public String save(@RequestBody Scuola scuola){
        return scuolaService.save(scuola);
    }


    @GetMapping("/upload")
    public String save(){
        return scuolaService.upload();
    }

    @GetMapping("/stampa")
    public void stampa(){scuolaService.stampa();}
}
