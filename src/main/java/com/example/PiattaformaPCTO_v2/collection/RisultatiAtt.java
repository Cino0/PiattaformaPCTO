package com.example.PiattaformaPCTO_v2.collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@Document(collection = "RisultatiAtt")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RisultatiAtt {


    private String attivita;

    private int annoAcc;


    private List<Universitario> universitarii;

    public RisultatiAtt() {
        this.universitarii = new ArrayList<>(10000);
    }



    public void addUniversitari(Universitario universitario) {
        this.universitarii.add(universitario);
    }

}
