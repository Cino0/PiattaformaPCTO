package com.example.PiattaformaPCTO_v2.collection;

import com.example.PiattaformaPCTO_v2.service.Utilities;
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
    private String cognome;
    private String scuolaImp;
    private String attivita;
    
    public Professore(String nome, String cognome,String email, String scuolaImp,String attivita){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.scuolaImp = scuolaImp;
        this.attivita = attivita;
    }

    public String toString(){
        return "Nome:"+this.nome+"; Email: "+this.email+"; Scuola: "+this.scuolaImp;
    }
    public String toJson(){
        return "{nome:"+this.nome+";email:"+this.email+";scuolaImp:"+this.scuolaImp+"}";
    }
    public boolean equals(Professore prof){
        if(prof.getEmail().equals(this.getEmail()) ){
            return true;
        }
        return false;
    }
    /**
     * Confronta 2 stringhe, se tutte le sottostringhe di questa non sono contenute nell'altra ritorna falso
     * @param s1    Stringa che viene scomposta in sottostringhe
     * @param s2    Stringa che dovrà contenere le sottostringhe
     * @return      True se queste s2 contiene tutte le sottostringhe di s1, False altrimenti
     */
    public boolean compare(String s1,String s2){
        String[] substrings = s1.split(" ");
        for(String substring:substrings){
            if(!s2.contains(substring)){
                return false;
            }
        }
        return true;
    }
}
