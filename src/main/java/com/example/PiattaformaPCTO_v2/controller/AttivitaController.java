package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.dto.ActivityViewDTOPair;
import com.example.PiattaformaPCTO_v2.service.AttivitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attivita")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
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

    @PostMapping("/mont")
    public  void uploadMontani(@RequestParam("file")MultipartFile file){
        attivitaService.uploadMontani(file);
    }

    @PostMapping("/n")
    public void uploadOpen(@RequestParam("file") MultipartFile file) {
        attivitaService.uploadOpen(file);
    }


    @PostMapping("/gioco")
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

    @PostMapping("/nerd")
    public void uploadN(@RequestParam("file") MultipartFile file){
        attivitaService.uploadNerd(file);
    }

    @PostMapping("/open23")
    public void uploadO23(@RequestParam("file")MultipartFile file){ attivitaService.uploadOpen23(file); }


    @PostMapping("/lab")
    public void uploadLab(@RequestParam("file")MultipartFile file){attivitaService.uploadLab(file);}


    @PostMapping("/stem")
    public void uploadStem(@RequestParam("file") MultipartFile file){attivitaService.uploadStem(file);}


    @PostMapping("/aper")
    public void uploadScuoleA(@RequestParam("file") MultipartFile file){attivitaService.uploadScuoleA(file);}


    @PostMapping("/laba")
    public void uploadLabOpen(@RequestParam("file")MultipartFile file){attivitaService.uploadLabOpen(file);}


    @PostMapping("/generi/{nome}")
    public void uploadGenerico(@RequestParam("file")MultipartFile file ,
                               @PathVariable String nome){attivitaService.uploadGenerico(file,nome);}

    @PostMapping("/pau")
    public void uploadPau(@RequestParam("file")MultipartFile file){attivitaService.uploadPau23(file);}

    @PostMapping("/cont")
    public void uploadContest(@RequestParam("file")MultipartFile file){attivitaService.uploaedContest23(file);}

    @PostMapping("/recanati")
    public void uploadRecanati23(@RequestParam("file")MultipartFile file){attivitaService.uploadRecanati23(file);}


    @GetMapping("/prova")
    void prova(){attivitaService.prova();}


}
