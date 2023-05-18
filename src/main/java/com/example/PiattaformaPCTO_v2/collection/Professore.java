package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Professori")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Professore {


    
    @Id
    private String email;

    private String nome;

    private String scuolaImp;
    
    private String citta;
    
    public Professore(String nome, String email, String scuolaImp, String citta){
        this.nome = nome;
        this.email = email;
        this.scuolaImp = scuolaImp;
        this.citta = citta;
    }

    public String toString(){
        return "Nome:"+this.nome+"; Email: "+this.email+"; Scuola: "+this.scuolaImp+"; Citta: "+this.citta+";";
    }
}
