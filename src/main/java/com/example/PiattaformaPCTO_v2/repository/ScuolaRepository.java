package com.example.PiattaformaPCTO_v2.repository;

import com.example.PiattaformaPCTO_v2.collection.Scuola;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScuolaRepository extends MongoRepository<Scuola,String> {


    @Query("{'nome':?0}")
    Scuola getScuolaByNome(String nome);

    @Query("{'nome': {'$regex': ?0}, 'citta': ?1}")
    Scuola getScuolaByNomeContainingAndAndCitta(String nome,String citta);


    @Query("{'citta':?0, 'nome':?1}")
    Scuola getScuolaByCittaAndNome(String citta,String nome);

    @Query("{'_id': ?0}")
    Scuola getScuolaById(String id);

    @Query("{'citta': ?0}")
    List<Scuola> getScuolaByCitta(String citta);

    @Query(value="{'_id' : *}", fields="{'citta' : 1, '_id': 0}")
    List<String> getAllCitta();
}
