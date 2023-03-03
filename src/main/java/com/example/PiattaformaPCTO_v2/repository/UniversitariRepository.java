package com.example.PiattaformaPCTO_v2.repository;

import com.example.PiattaformaPCTO_v2.collection.Universitario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversitariRepository extends MongoRepository<Universitario,String> {


    @Query("{'nome': ?0,'cognome': ?1,'comuneScuola':?2}")
    Universitario findByNomeAndCognomeAndComuneScuola(String nome,String cognome,String citta);

}
