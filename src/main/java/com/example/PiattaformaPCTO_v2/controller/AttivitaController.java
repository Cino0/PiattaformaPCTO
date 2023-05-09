package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.dto.ActivityViewDTOPair;
import com.example.PiattaformaPCTO_v2.service.AttivitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attivita")
public class AttivitaController {

    @Autowired
    private AttivitaService attivitaService;

    @PostMapping
    public String save(@RequestBody Attivita attivita) {
        return attivitaService.save(attivita);
    }


    @RequestMapping("/upload")
    public void uploadAttivita() {
        attivitaService.upload();
    }

    @RequestMapping("/summer")
    public void uploadSummer() {
        attivitaService.uploadSummer();
    }

    @RequestMapping("/cartel")
    public void uploadCartel() {
        attivitaService.uploadCartel();
    }

    @PostMapping("/n")
    public void uploadOpen(@RequestParam("file") MultipartFile file) {
        attivitaService.uploadOpen(file);
    }


    @PostMapping("/g")
    public void uploadGioco(@RequestParam("file") MultipartFile file) {
        attivitaService.uploadGioco(file);
    }

    /**
     * Get information about students that chose UNICAM and their high school, given an activity.
     *
     * @return list of activity view pairs
     */
    @ResponseBody
    @GetMapping("/studentsFromActivity/{activityName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ActivityViewDTOPair> getStudentsFromActivity(@PathVariable String activityName) {
        return this.attivitaService.findStudentsFromActivity(activityName);
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
