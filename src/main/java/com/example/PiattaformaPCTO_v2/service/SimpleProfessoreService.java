package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.example.PiattaformaPCTO_v2.repository.ProfessoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleProfessoreService implements ProfessoreService{

    @Autowired
    private ProfessoreRepository professoreRepository;

    @Override
    public String save(Professore professore) {
        return professoreRepository.save(professore).getEmail();
    }
}
