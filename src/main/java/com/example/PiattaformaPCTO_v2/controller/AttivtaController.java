package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.service.AttivitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attivita")
public class AttivtaController {

    @Autowired
    private AttivitaService attivitaService;

    @PostMapping
    public String save(@RequestBody Attivita attivita){
        return  attivitaService.save(attivita);
    }



    @RequestMapping("/upload")
    public void uploadAttivita(){
        attivitaService.upload();
    }

    @RequestMapping("/summer")
    public void uploadSummer(){
        attivitaService.uploadSummer();
    }

    @RequestMapping("/cartel")
    public void uploadCartel(){
        attivitaService.uploadCartel();
    }

    @PostMapping("/n")
    public void uploadOpen(@RequestParam("file") MultipartFile file){
        attivitaService.uploadOpen(file);
    }


    @PostMapping("/g")
    public void uploadGioco(@RequestParam("file") MultipartFile file){
        attivitaService.uploadGioco(file);
    }


    @PostMapping("/a")
    public void uploadG(@RequestParam("file") MultipartFile file){
        attivitaService.uploadG(file);
    }


    @GetMapping("/crea")
    public void creaPdf(){
        attivitaService.creaPdf();
    }



}
