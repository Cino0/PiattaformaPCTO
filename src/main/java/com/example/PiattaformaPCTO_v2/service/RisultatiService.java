package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.collection.Risultati;
import com.example.PiattaformaPCTO_v2.collection.RisultatiAtt;

import java.util.List;

public interface RisultatiService {
    void crea();


    void risultatiInf(List<Attivita> attivita, List<Risultati> risultati);


    void createStudentsFromActivities();

    List<Risultati> getRisultati();

    List<RisultatiAtt> getRisultatiAtt();
    Risultati stampa();

    List<Risultati> getRisultatiAnno(int anno);
}
