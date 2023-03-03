package com.example.PiattaformaPCTO_v2.service;

public class SimpleStringaService implements StringaService{
    @Override
    public String mofidica(String s) {
        String scuola = s.replace('"','$');
        return scuola;
    }
}
