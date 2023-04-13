package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Studenti")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Studente {

    @Id
    private String idStudente;
    private String nome;
    private String cognome;
    private Scuola scuola;

    public Studente(String idStudente, String nome, String cognome, Scuola scuola) {
        this.idStudente = idStudente;
        this.nome = nome;
        this.cognome = cognome;
        this.scuola = scuola;
    }
}
