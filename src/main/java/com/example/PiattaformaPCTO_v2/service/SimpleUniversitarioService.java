package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.repository.UniversitariRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class SimpleUniversitarioService implements UniversitariService{


    @Autowired
    private UniversitariRepository universitariRepository;
    @Override
    public String save(Universitario universitario) {
        return universitariRepository.save(universitario).getNome();
    }

    @Override
    public String upload() {
        String filePath="src/main/resources/iscri.xlsx";
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()){
                Row row = iterator.next();
                String matr= row.getCell(4).getStringCellValue();
               // System.out.println(matr);
                //String matricola= String.valueOf(matr);
                String nome = row.getCell(7).getStringCellValue();
               // System.out.println(nome);
                String cognome = row.getCell(6).getStringCellValue();
                //System.out.println(cognome);
                String comune = row.getCell(39).getStringCellValue().toUpperCase();
               // System.out.println(comune);
                String scuola = row.getCell(38).getStringCellValue();
               // System.out.println(scuola);
                Universitario universitario = new Universitario(matr,nome,cognome,"4043",comune,scuola);
                System.out.println(universitario.toString());
               this.save(universitario);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
