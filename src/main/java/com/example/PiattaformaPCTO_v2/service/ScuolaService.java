package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Scuola;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ScuolaService {
    String save(Scuola scuola);

    String upload();

    void stampa();

    List<Scuola> getScuole();


    List<String> getCitta();

    List<String> getNomi(String c);

}
