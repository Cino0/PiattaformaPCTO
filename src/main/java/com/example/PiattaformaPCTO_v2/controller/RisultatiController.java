package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.service.RisultatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risultati")
public class RisultatiController {
    @Autowired
    private RisultatiService risultatiService;

    @RequestMapping("/crea")
    public void crea(){
        risultatiService.crea();
    }

    @RequestMapping("/createStudentsFromActivities")
    public void createStudentsFromActivities(){
        this.risultatiService.createStudentsFromActivities();
    }
}
