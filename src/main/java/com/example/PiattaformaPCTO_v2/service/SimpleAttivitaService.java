package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.collection.Studente;
import com.example.PiattaformaPCTO_v2.repository.AttivitaRepository;
import com.example.PiattaformaPCTO_v2.repository.ScuolaRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

@Service
public class SimpleAttivitaService implements AttivitaService{

    @Autowired
    private AttivitaRepository attivitaRepository;

    @Autowired
    private ScuolaRepository scuolaRepository;

    @Override
    public String save(Attivita attivita) {
        return attivitaRepository.save(attivita).getNome();
    }

    @Override
    public void upload() {
        String filePath ="src/main/resources/Progetto-NERD-2021-2022.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file= new File(filePath);
            String name= FilenameUtils.removeExtension(file.getName());
            Attivita attivita= new Attivita(name,4043);
            System.out.println(filePath);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()){
                Row riga = iterator.next();
                if(riga.getCell(3)!=null){
                    Scuola scuola = scuolaRepository.getScuolaByNome(riga.getCell(8).getStringCellValue());
                    if (scuola!=null){
                        String nome=riga.getCell(3).getStringCellValue();
                        String cognome=riga.getCell(4).getStringCellValue();
                        String id= nome + cognome+scuola.getNome();
                        Studente stud = new Studente(id,nome,cognome,scuola);
                        System.out.println(stud.toString());
                        attivita.getStudPartecipanti().add(stud);
                        System.out.println(attivita.getStudPartecipanti().size());
                    }

                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadSummer() {
        String filePath ="src/main/resources/SummerSchoolSTEMPartecipanti.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file= new File(filePath);
            String name= FilenameUtils.removeExtension(file.getName());
            Attivita attivita= new Attivita(name,4043);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()){
                Row riga = iterator.next();
                if(riga.getCell(1) != null){
                    Scuola scuola = scuolaRepository.getScuolaById(riga.getCell(4).getStringCellValue());
                    if(scuola!=null){
                        //System.out.println(scuola.toString());
                        //System.out.println(riga.getCell(1).getStringCellValue());
                        String nome=riga.getCell(1).getStringCellValue();
                        String cognome=riga.getCell(2).getStringCellValue();
                        String id= nome + cognome+scuola.getNome();
                        Studente stud = new Studente(id,nome,cognome,scuola);
                        //System.out.println(stud.toString());
                        attivita.getStudPartecipanti().add(stud);
                    }
                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void uploadCartel() {
        String filePath ="src/main/resources/Cartel1.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file= new File(filePath);
            String name= FilenameUtils.removeExtension(file.getName());
            Attivita attivita= new Attivita(name,4043);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()){
                Row row = iterator.next();
               if(row.getCell(0)!=null){
                   Scuola scuola = scuolaRepository.getScuolaById(row.getCell(2).getStringCellValue());
                  // System.out.println(scuola.toString());
                   String nome=row.getCell(0).getStringCellValue();
                   String cognome=row.getCell(1).getStringCellValue();
                   String id= nome + cognome+scuola.getNome();
                   Studente stud = new Studente(id,nome,cognome,scuola);
                   System.out.println(stud.toString());
                   attivita.getStudPartecipanti().add(stud);
               }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void uploadOpen(MultipartFile file) {
        Attivita attivita= new Attivita("Open2022",4043);
        Sheet dataSheet= this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()){
            Row row = iterator.next();
            //System.out.println(row.getCell(3).getStringCellValue());
            Scuola scuola = scuolaRepository.getScuolaByNome(row.getCell(3).getStringCellValue());
            String nome=row.getCell(0).getStringCellValue();
            String cognome=row.getCell(1).getStringCellValue();
            String id= nome + cognome+scuola.getNome();
            Studente stud = new Studente(id,nome,cognome,scuola);
           // System.out.println(stud.toString());
            attivita.getStudPartecipanti().add(stud);

        }
        System.out.println(this.save(attivita));
    }

    @Override
    public Sheet fileOpenerHelper(MultipartFile file) {
        try {
            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);
            Workbook workbook  = new XSSFWorkbook(tempFile);
            Sheet dataSheet = workbook.getSheetAt(0);
            return dataSheet;
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }


}
