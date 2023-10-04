package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.repository.ScuolaRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;


import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SimpleScuolaService implements ScuolaService{

    @Autowired
    private ScuolaRepository scuolaRepository;


    @Override
    public String save(Scuola scuola) {
        return scuolaRepository.save(scuola).getIdScuola();
    }



    @Override
    public String upload() {
        String filePath="src/main/resources/scuole-statali.xlsx";
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            System.out.println(filePath);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if (!row.getCell(7).getStringCellValue().equals("SCUOLA PRIMO GRADO")){
                    if (!row.getCell(7).getStringCellValue().equals("SCUOLA PRIMARIA")){
                        if (!row.getCell(7).getStringCellValue().equals("SCUOLA INFANZIA")){
                            String id = row.getCell(2).getStringCellValue();
                            String nome = row.getCell(3).getStringCellValue();
                            String regione = row.getCell(0).getStringCellValue();
                            String provincia = row.getCell(1).getStringCellValue();
                            String citta = row.getCell(6).getStringCellValue();
                            String tipo =row.getCell(7).getStringCellValue();
                            Scuola scuola = new Scuola(id,nome,regione,provincia,citta,tipo);
                            this.save(scuola);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void stampa() {
        Scuola scuola = scuolaRepository.getScuolaByNomeContainingAndAndCitta("RICCI","FERMO");
        String s= scuola.getNome();

        System.out.println(s.replace('"','$'));
    }

    @Override
    public List<Scuola> getScuole() {
        return this.scuolaRepository.findAll();
    }

    @Override
    public List<String> getCitta() {
        List<Scuola> scuole = this.scuolaRepository.findAll();
        List<String> citta = new ArrayList<>();
        for (Scuola s : scuole){
            citta.add(s.getCitta());
        }
        return citta;
    }

    @Override
    public List<String> getNomi(String c) {
        List<Scuola> scuole = this.scuolaRepository.getScuolaByCitta(c);
        List<String> nomi = new ArrayList<>();
        for (Scuola s : scuole){
            nomi.add(s.getNome());
        }
        return nomi;
    }


}
