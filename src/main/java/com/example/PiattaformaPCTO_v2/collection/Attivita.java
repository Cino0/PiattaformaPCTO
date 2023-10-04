package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Attivita")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attivita {
/*
    @Id
    private String idAttivita;*/

    private String nome;

    private String tipo;

    // private String modalita;

    private int annoAcc;

    //private Scuola scuola;

    private List<Studente> studPartecipanti;

    //private Professore professore;

    public Attivita(String nome, int AnnoAcc,String tipo) {
        this(nome,tipo, AnnoAcc, Collections.emptyList());
    }


    public Attivita(String nome,String tipo, int annoAcc, List<Studente> studPartecipanti) {
        this.nome = nome;
        this.tipo= tipo;
        this.annoAcc = annoAcc;
        this.studPartecipanti = studPartecipanti;
    }

    public Attivita getActivityWithoutStudents() {
        return new Attivita(this.nome,this.tipo, this.annoAcc, Collections.emptyList());
    }
}
