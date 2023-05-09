package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
@Builder
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

    public Attivita(String nome, int AnnoAcc) {
        this(nome, AnnoAcc, Collections.emptyList());
    }


    public Attivita(String nome, int annoAcc, List<Studente> studPartecipanti) {
        this.nome = nome;
        this.annoAcc = annoAcc;
        this.studPartecipanti = studPartecipanti;
    }

    public Attivita getActivityWithoutStudents() {
        return new Attivita(this.nome, this.annoAcc, Collections.emptyList());
    }
}
