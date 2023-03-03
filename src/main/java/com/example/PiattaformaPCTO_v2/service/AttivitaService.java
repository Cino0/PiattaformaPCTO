package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

public interface AttivitaService {

    String save(Attivita attivita);

    void upload();

    void uploadSummer();

    void uploadCartel();

    Sheet fileOpenerHelper(MultipartFile file);


    void uploadOpen(MultipartFile file);


    void uploadGioco(MultipartFile file);
}
