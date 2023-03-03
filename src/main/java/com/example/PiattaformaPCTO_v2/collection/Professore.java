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
    private String idProf;

    private String nome;

    private String cognome;

    private String email;

    private Scuola scuolaImp;

}
