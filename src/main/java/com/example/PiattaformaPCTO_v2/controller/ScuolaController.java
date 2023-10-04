package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.service.ScuolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/scuola")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
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



    @GetMapping("/visua")
    public ResponseEntity<List<Scuola>> visualizza(){
        List<Scuola> s = this.scuolaService.getScuole();
        return new ResponseEntity<List<Scuola>>(s, HttpStatus.OK);
    }


    @GetMapping("/c")
    public void citta(){
        this.scuolaService.getCitta();
    }
}
