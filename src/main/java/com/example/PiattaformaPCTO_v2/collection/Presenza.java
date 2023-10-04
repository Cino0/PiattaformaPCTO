package com.example.PiattaformaPCTO_v2.collection;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class Presenza {

    private String nomeAttivita;

    private List<Studente> partecipanti;

    private List<Studente> iscritti;


    public Presenza(String nomeAttivita) {
        this.nomeAttivita = nomeAttivita;
        this.partecipanti=new ArrayList<>();
        this.iscritti=new ArrayList<>();
    }



    public void nuovoIscritto(Studente s){
        this.iscritti.add(s);
    }
}
