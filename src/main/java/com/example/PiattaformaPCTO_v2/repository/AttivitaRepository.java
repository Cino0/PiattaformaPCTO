package com.example.PiattaformaPCTO_v2.repository;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttivitaRepository extends MongoRepository<Attivita, String> {
    @Query("{'nome': ?0}")
    Attivita findByNome(String nome);

}
