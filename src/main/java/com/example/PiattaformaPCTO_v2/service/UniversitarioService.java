package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Iscrizioni;
import com.example.PiattaformaPCTO_v2.collection.Universitario;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UniversitarioService {
    String save(Universitario universitario);

    String upload(MultipartFile file);

    List<Universitario> getUniversitari();

    void salva();

    List<Iscrizioni> getIscrizioni();

    List<Iscrizioni> getIscrizioniAnno(int anno);


    Sheet fileOpenerHelper(MultipartFile file);
}
