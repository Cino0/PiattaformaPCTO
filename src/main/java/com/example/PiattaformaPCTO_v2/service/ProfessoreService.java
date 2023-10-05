package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Professore;

import java.util.List;

public interface ProfessoreService {
    String save(Professore professore);
    String upload();
    String stampa();

    List<Professore> getAllProf();
}
