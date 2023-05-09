package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.collection.Risultati;

import java.util.List;

public interface RisultatiService {
    void crea();


    void risultatiInf(List<Attivita> attivita, List<Risultati> risultati);


    void createStudentsFromActivities();
}
