package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Iscrizioni;
import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.repository.IscrizioniRepository;
import com.example.PiattaformaPCTO_v2.repository.UniversitarioRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

@Service
public class SimpleUniversitarioService implements UniversitarioService {

    @Autowired
    private UniversitarioRepository universitarioRepository;
    @Autowired
    private IscrizioniRepository iscrizioniRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public String save(Universitario universitario) {
        return universitarioRepository.save(universitario).getNome();
    }

    @Override
    public String upload(MultipartFile file) {
        LocalDate date = LocalDate.now();
        System.out.println(file.getOriginalFilename());
        Iscrizioni i = new Iscrizioni((date.getYear()*2)+1);
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()){
            Row row = iterator.next();
            double matr= row.getCell(1).getNumericCellValue();
            String m = String.valueOf(matr).replaceAll("[0]*$", "").replaceAll(".$", "");
            String nome = row.getCell(2).getStringCellValue();
            String cognome = row.getCell(3).getStringCellValue();
            if (row.getCell(5)!=null){
                String comune = row.getCell(5).getStringCellValue().toUpperCase();
                String scuola = row.getCell(4).getStringCellValue();
                String corso = row.getCell(0).getStringCellValue();
                Universitario universitario = new Universitario(m,nome,cognome,4047,corso,comune,scuola);
                System.out.println(universitario.toString());
                i.addUniversitario(universitario);
            }
        }
        this.iscrizioniRepository.save(i);
        return "caricati";
    }

    @Override
    public List<Universitario> getUniversitari() {
        return this.universitarioRepository.findAll();
    }

    @Override
    public void salva() {
       /* Iscrizioni i = new Iscrizioni(2023);
        Universitario u = new Universitario("109211","Matteo","Manci","2023","info","pse","pippo");
        i.addUniversitario(u);
        this.iscrizioniRepository.save(i);*/
    }

    @Override
    public List<Iscrizioni> getIscrizioni() {
       return this.iscrizioniRepository.findAll();
    }

    @Override
    public List<Iscrizioni> getIscrizioniAnno(int anno) {
        Query query = new Query();
        query.addCriteria(Criteria.where("annoAc").is(anno));
        return mongoTemplate.find(query,Iscrizioni.class);
    }

    @Override
    public Sheet fileOpenerHelper(MultipartFile file) {
        try {
            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);
            Workbook workbook = new XSSFWorkbook(tempFile);
            Sheet dataSheet = workbook.getSheetAt(0);
            return dataSheet;
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
