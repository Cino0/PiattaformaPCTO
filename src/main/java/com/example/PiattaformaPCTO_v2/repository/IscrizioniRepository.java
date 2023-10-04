package com.example.PiattaformaPCTO_v2.repository;

import com.example.PiattaformaPCTO_v2.collection.Iscrizioni;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IscrizioniRepository extends MongoRepository<Iscrizioni,String> {
}
