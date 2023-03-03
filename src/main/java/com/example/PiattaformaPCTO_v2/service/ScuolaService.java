package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Scuola;

public interface ScuolaService {
    String save(Scuola scuola);

    String upload();

    void stampa();
}
