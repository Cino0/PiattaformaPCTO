package com.example.PiattaformaPCTO_v2.repository;

import com.example.PiattaformaPCTO_v2.collection.Professore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessoreRepository extends MongoRepository<Professore,String> {
    @Query("{'nome': ?0}")
    List<Professore> getProfessoresByNomeContains(String nome);

}
