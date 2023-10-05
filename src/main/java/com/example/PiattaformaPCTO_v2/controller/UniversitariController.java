package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Iscrizioni;
import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.service.UniversitarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/universitari")
@CrossOrigin(origins = "*",allowedHeaders = "*")

public class UniversitariController {

    @Autowired
    private UniversitarioService universitarioService;

    @PostMapping
    public String save(@RequestBody Universitario universitario){
        return universitarioService.save(universitario);
    }



    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){return universitarioService.upload(file);}


    @GetMapping("/geta")
    public ResponseEntity<List<Universitario>> getUniveristari(){
        List<Universitario> u = this.universitarioService.getUniversitari();
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/salva")
    public void salva(){
        this.universitarioService.salva();
    }


    @GetMapping("/geti")
    public ResponseEntity<List<Iscrizioni>> getIscrizioni(){
        List<Iscrizioni> i = this.universitarioService.getIscrizioni();
        return new ResponseEntity<>(i,HttpStatus.OK);
    }

    @GetMapping("/geti/{anno}")
    public ResponseEntity<List<Iscrizioni>> getIscrizioniAnno(@PathVariable int anno){
        List<Iscrizioni> i = this.universitarioService.getIscrizioniAnno(anno);
        return new ResponseEntity<>(i,HttpStatus.OK);
    }
}
