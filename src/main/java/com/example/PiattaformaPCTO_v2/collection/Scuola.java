package com.example.PiattaformaPCTO_v2.collection;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "Scuole")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Scuola {

    @Id
    private String idScuola;

    private String nome;

    private String regione;

    private String provincia;

    private String citta;

    private String tipo;


    public Scuola(String idScuola, String nome, String regione, String provincia, String citta,String tipo) {
        this.idScuola = idScuola;
        this.nome = nome;
        this.regione = regione;
        this.provincia = provincia;
        this.citta = citta;
        this.tipo = tipo;
    }

    public Scuola(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Scuola{" +
                "idScuola='" + idScuola + '\'' +
                ", nome='" + nome + '\'' +
                ", regione='" + regione + '\'' +
                ", provincia='" + provincia + '\'' +
                ", citta='" + citta + '\'' +
                '}';
    }
}
