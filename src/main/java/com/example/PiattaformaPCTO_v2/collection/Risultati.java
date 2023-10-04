package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Risultati")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Risultati {

    private int annoAcc;

    private Scuola  scuola;

    private List<Presenza> attivita;

    private List<Universitario> iscritti;

    public Risultati(int annoAcc, Scuola scuola) {
        this.annoAcc = annoAcc;
        this.scuola = scuola;
        this.attivita=new ArrayList<>(10000);
        this.iscritti=new ArrayList<>();
    }



    public void nuovoIscritto(Universitario studente){
        this.iscritti.add(studente);
    }
}
