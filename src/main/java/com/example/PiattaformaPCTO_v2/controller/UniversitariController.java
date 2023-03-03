package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.service.UniversitariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/universitari")
public class UniversitariController {

    @Autowired
    private UniversitariService universitariService;

    @PostMapping
    public String save(@RequestBody Universitario universitario){
        return universitariService.save(universitario);
    }



    @GetMapping("/upload")
    public String upload(){return universitariService.upload();}
}
