package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Risultati;
import com.example.PiattaformaPCTO_v2.collection.RisultatiAtt;
import com.example.PiattaformaPCTO_v2.service.RisultatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/risultati")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class RisultatiController {
    @Autowired
    private RisultatiService risultatiService;

    @PostMapping("/crea")
    public void crea(){
        risultatiService.crea();
    }

    @RequestMapping("/createStudentsFromActivities")
    public void createStudentsFromActivities(){
        this.risultatiService.createStudentsFromActivities();
    }




    @GetMapping("/res")
    public ResponseEntity<List<Risultati>> getRisultati(){
        List<Risultati> res = this.risultatiService.getRisultati();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/resa")
    public ResponseEntity<List<RisultatiAtt>> getRisulatatiAtt(){
        List<RisultatiAtt> resa = this.risultatiService.getRisultatiAtt();
        System.out.println(resa.size());
        return new ResponseEntity<>(resa,HttpStatus.OK);
    }

    @GetMapping("/res/{anno}")
    public ResponseEntity<List<Risultati>> getRisultatiAnno(@PathVariable int anno){
        List<Risultati> res = this.risultatiService.getRisultatiAnno(anno);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @GetMapping("/s")
    public ResponseEntity<Risultati> stampa(){
        Risultati r = this.risultatiService.stampa();
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
