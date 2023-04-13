package com.example.PiattaformaPCTO_v2.controller;

import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.service.UniversitarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/universitari")
public class UniversitariController {

    @Autowired
    private UniversitarioService universitarioService;

    @PostMapping
    public String save(@RequestBody Universitario universitario){
        return universitarioService.save(universitario);
    }



    @GetMapping("/upload")
    public String upload(){return universitarioService.upload();}
}
