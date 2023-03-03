package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Attivita")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attivita {
/*
    @Id
    private String idAttivita;*/

    private String nome;

   // private String modalita;

    private int annoAcc;

    //private Scuola scuola;

    private List<Studente> studPartecipanti;

    //private Professore professore;


    public Attivita(String nome, int annoAcc) {
        this.nome = nome;
        this.annoAcc = annoAcc;
        this.studPartecipanti = new ArrayList<Studente>(1000);
    }
}
