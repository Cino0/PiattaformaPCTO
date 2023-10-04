package com.example.PiattaformaPCTO_v2.repository;


import com.example.PiattaformaPCTO_v2.collection.RisultatiAtt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RisultatiAttRepository extends MongoRepository <RisultatiAtt,String> {
}
