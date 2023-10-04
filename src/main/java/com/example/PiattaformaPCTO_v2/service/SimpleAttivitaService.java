package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.*;
import com.example.PiattaformaPCTO_v2.dto.ActivityViewDTOPair;
import com.example.PiattaformaPCTO_v2.repository.AttivitaRepository;
import com.example.PiattaformaPCTO_v2.repository.ScuolaRepository;
import com.example.PiattaformaPCTO_v2.repository.UniversitarioRepository;
import org.apache.commons.io.FilenameUtils;
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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SimpleAttivitaService implements AttivitaService {

    @Autowired
    private AttivitaRepository attivitaRepository;
    /**
     * Universitario repository instance.
     */
    @Autowired
    private UniversitarioRepository universitarioRepository;
    @Autowired
    private ScuolaRepository scuolaRepository;
    @Autowired
    private StringFinderHelper stringFinderHelper;
    @Autowired
    private ScuolaService scuolaService;
    @Autowired
    private UniversitarioService universitarioService;
    @Autowired
    private MongoTemplate mongoTemplate;



    @Override
    public String save(Attivita attivita) {
        return attivitaRepository.save(attivita).getNome();
    }

    //file path
    @Override
    public void upload() {
        String filePath = "src/main/resources/Progetto-NERD-2021-2022.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name,"PROGETTO_NERD", 4043, new ArrayList<>());
            System.out.println(filePath);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row riga = iterator.next();
                if (riga.getCell(3) != null) {
                    Scuola scuola = scuolaRepository.getScuolaByNome(riga.getCell(8).getStringCellValue());
                    if (scuola != null) {
                        String nome = riga.getCell(3).getStringCellValue();
                        String cognome = riga.getCell(4).getStringCellValue();
                        String id = nome + cognome + scuola.getNome();
                        Studente stud = new Studente(id, nome, cognome, scuola);
                        System.out.println(stud);
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


    //file path
    @Override
    public void uploadSummer() {
        String filePath = "src/main/resources/SummerSchoolSTEMPartecipanti.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name,"SUMMER_SCHOOL_STEM", 4043, new ArrayList<>());
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row riga = iterator.next();
                if (riga.getCell(1) != null) {
                    Scuola scuola = scuolaRepository.getScuolaById(riga.getCell(4).getStringCellValue());
                    if (scuola != null) {
                        //System.out.println(scuola.toString());
                        //System.out.println(riga.getCell(1).getStringCellValue());
                        String nome = riga.getCell(1).getStringCellValue();
                        String cognome = riga.getCell(2).getStringCellValue();
                        String id = nome + cognome + scuola.getNome();
                        Studente stud = new Studente(id, nome, cognome, scuola);
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


    //file path
    @Override
    public void uploadCartel() {
        String filePath = "src/main/resources/Cartel1.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name,"PCTO_RECNATI", 4043, new ArrayList<>());
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (row.getCell(0) != null) {
                    Scuola scuola = scuolaRepository.getScuolaById(row.getCell(2).getStringCellValue());
                    // System.out.println(scuola.toString());
                    String nome = row.getCell(0).getStringCellValue();
                    String cognome = row.getCell(1).getStringCellValue();
                    String id = nome + cognome + scuola.getNome();
                    Studente stud = new Studente(id, nome, cognome, scuola);
                    System.out.println(stud);
                    attivita.getStudPartecipanti().add(stud);
                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    //file PAU 2022-Informatica.xlsx
    @Override
    public void uploadOpen(MultipartFile file) {
        Attivita attivita = new Attivita("Open2022","PORTE_APERTE_UNICAM", 4043, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            //System.out.println(row.getCell(3).getStringCellValue());
            Scuola scuola = scuolaRepository.getScuolaByNome(row.getCell(3).getStringCellValue());
            String nome = row.getCell(0).getStringCellValue();
            String cognome = row.getCell(1).getStringCellValue();
            String id = nome + cognome + scuola.getNome();
            Studente stud = new Studente(id, nome, cognome, scuola);
            // System.out.println(stud.toString());
            attivita.getStudPartecipanti().add(stud);

        }
        System.out.println(this.save(attivita));
    }


    //file informatica_x_giocoEd3.xlsx
    @Override
    public void uploadGioco(MultipartFile file) {
        Attivita attivita = new Attivita("InformaticaXGioco", 4043, "CONTEST_INFORMATICA_X_GIOCO");
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        Scuola s = new Scuola("xxx", "Scuolax", "MARCHE", "FERMO", "PSE", "LICEO");
        while (iterator.hasNext()) {
            Row r = iterator.next();
            if (!r.getCell(0).getStringCellValue().isEmpty()) {
                int finale = r.getCell(0).getStringCellValue().length();
                int inizioNome = (r.getCell(0).getStringCellValue().indexOf(':')) + 2;
                int inizioCognome = (r.getCell(0).getStringCellValue().lastIndexOf(':')) + 2;
                int fineNome = r.getCell(0).getStringCellValue().indexOf('\n');
                String nome = r.getCell(0).getStringCellValue().substring(inizioNome, fineNome);
                String cognome = r.getCell(0).getStringCellValue().substring(inizioCognome, finale);
                String id = nome + cognome + s.getNome();
                Studente stud = new Studente(id, nome, cognome, s);
                attivita.getStudPartecipanti().add(stud);
                System.out.println(stud);
            }
        }

        System.out.println(this.save(attivita));
    }




    @Override
    public void uploadG(MultipartFile file) {
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row r = iterator.next();
            if (!r.getCell(0).getStringCellValue().equals("Nome e Cognome")) {
                if (r.getCell(1) != null) {
                    System.out.println("ciao");
                }
            } else {
                System.out.println("pipi");
            }

        }
    }


    //file nerd23.xlsx
    @Override
    public void uploadNerd(MultipartFile file) {
        Attivita attivita = new Attivita("Nerd23", "PROGETTO_NERD",4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (row.getCell(6)!=null) {
                String c = row.getCell(6).getStringCellValue().toUpperCase();
                String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
                List<String> nomi = this.scuolaService.getNomi(trovata);
                String s =row.getCell(4).getStringCellValue();
                String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
                Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
                String nome = row.getCell(1).getStringCellValue();
                String cognome = row.getCell(2).getStringCellValue();
                String id = nome + cognome + scuola.getNome();
                Studente stud = new Studente(id, nome, cognome, scuola);
                attivita.getStudPartecipanti().add(stud);
            }
        }
        System.out.println(this.save(attivita));
    }


    //file PCTO-Informatica23.xlsx
    @Override
    public void uploadOpen23(MultipartFile file) {
        Attivita attivita = new Attivita("PTCO23","PCTO_IN_PRESENZA", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        while (iterator.hasNext()){
            Row row = iterator.next();
            String c = row.getCell(3).getStringCellValue().toUpperCase();
            String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
            List<String> nomi = this.scuolaService.getNomi(trovata);
            String s =row.getCell(2).getStringCellValue();
            String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
            Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
            String nome = row.getCell(1).getStringCellValue();
            String cognome = row.getCell(0).getStringCellValue();
            String id = nome + cognome + scuola.getNome();
            Studente stud = new Studente(id, nome, cognome, scuola);
            attivita.getStudPartecipanti().add(stud);
        }
        System.out.println(this.save(attivita));

    }


    //file SummerLab2023.xlsx
    @Override
    public void uploadLab(MultipartFile file) {
        Attivita attivita = new Attivita("SummerLab23","SUMMERLAB", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        iterator.next();
        while (iterator.hasNext()){
            Row row = iterator.next();
            if (!row.getCell(2).getStringCellValue().isEmpty()){
                String c = row.getCell(3).getStringCellValue().toUpperCase();
                String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
                List<String> nomi = this.scuolaService.getNomi(trovata);
                String s =row.getCell(2).getStringCellValue();
                String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
                System.out.println("Data: "+s+" Trovata: "+sT);
                Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
                String nome = row.getCell(0).getStringCellValue();
                String cognome = row.getCell(1).getStringCellValue();
                String id = nome + cognome + scuola.getNome();
                Studente stud = new Studente(id, nome, cognome, scuola);
                attivita.getStudPartecipanti().add(stud);
            }
        }
        System.out.println(this.save(attivita));
    }


    //SummerSchoolSTEM2023.xlsx
    @Override
    public void uploadStem(MultipartFile file) {
        Attivita attivita = new Attivita("SummerSchoolSTEM23","SUMMER_SCHOOL_STEM", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        while(iterator.hasNext()){
            Row row = iterator.next();
            String c = row.getCell(12).getStringCellValue().toUpperCase();
            String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
            List<String> nomi = this.scuolaService.getNomi(trovata);
            String s =row.getCell(11).getStringCellValue();
            String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
            System.out.println("Data: "+s+" Trovata: "+sT);
            Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
            String nome = row.getCell(0).getStringCellValue();
            String cognome = row.getCell(1).getStringCellValue();
            String id = nome + cognome + scuola.getNome();
            Studente stud = new Studente(id, nome, cognome, scuola);
            attivita.getStudPartecipanti().add(stud);
        }
        System.out.println(this.save(attivita));
    }


    //file informaticaopenday13luglio.xlsx
    @Override
    public void uploadScuoleA(MultipartFile file) {
        Attivita attivita = new Attivita("OpenDayLuglio2023","OPEN_DAY_UNICAM", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        iterator.next();
        while (iterator.hasNext()){
            Row row = iterator.next();
            if(!row.getCell(15).getStringCellValue().equals("*")){
                String c = row.getCell(14).getStringCellValue().toUpperCase();
                String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
                List<String> nomi = this.scuolaService.getNomi(trovata);
                String s =row.getCell(13).getStringCellValue();
                String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
                System.out.println("Data: "+s+" Trovata: "+sT);
                Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
                String nome = row.getCell(0).getStringCellValue();
                String cognome = row.getCell(1).getStringCellValue();
                String id = nome + cognome + scuola.getNome();
                Studente stud = new Studente(id, nome, cognome, scuola);
                attivita.getStudPartecipanti().add(stud);
            }
        }
        System.out.println(this.save(attivita));
    }


    //file  informaticalabaperti27luglio.xlsx
    @Override
    public void uploadLabOpen(MultipartFile file) {
        Attivita attivita = new Attivita("LabApertiLuglio2023","LABORATORI_APERTI_UNICAM", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        while (iterator.hasNext()){
            Row row = iterator.next();
            String c = row.getCell(14).getStringCellValue().toUpperCase();
            String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
            List<String> nomi = this.scuolaService.getNomi(trovata);
            String s =row.getCell(13).getStringCellValue();
            String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
            System.out.println("Data: "+s+" Trovata: "+sT);
            Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
            String nome = row.getCell(0).getStringCellValue();
            String cognome = row.getCell(1).getStringCellValue();
            String id = nome + cognome + scuola.getNome();
            Studente stud = new Studente(id, nome, cognome, scuola);
            attivita.getStudPartecipanti().add(stud);
        }
        System.out.println(this.save(attivita));
    }

    @Override
    public void uploadGenerico(MultipartFile file, String nome) {
        System.out.println(nome);
        System.out.println(file.getOriginalFilename());
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 0;
        while (iterator.hasNext()){
            Row row = iterator.next();
            System.out.println(row.getCell(1).getStringCellValue());
        }
    }


    // file PAU 2023 - Informatica.xlsx
    @Override
    public void uploadPau23(MultipartFile file) {
        Attivita attivita = new Attivita("Porte_Aperte_Unicam_4045","PORTE_APERTE_UNICAM", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 1;
        iterator.next();
        while(iterator.hasNext()){
            Row row = iterator.next();
            if (row.getCell(0)!=null){
                System.out.println(ncolonna+" "+row.getCell(8).getStringCellValue());
                ncolonna++;
                if(row.getCell(8).getStringCellValue().equals("*")){
                    System.out.println("ciao");
                    String c = row.getCell(2).getStringCellValue().toUpperCase();
                    String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
                    List<String> nomi = this.scuolaService.getNomi(trovata);
                    String s =row.getCell(4).getStringCellValue();
                    String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
                    System.out.println("Data: "+s+" Trovata: "+sT);
                    Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
                    String nome = row.getCell(0).getStringCellValue();
                    String cognome = row.getCell(1).getStringCellValue();
                    String id = nome + cognome + scuola.getNome();
                    Studente stud = new Studente(id, nome, cognome, scuola);
                    attivita.getStudPartecipanti().add(stud);
                }
            }

        }
        System.out.println(this.save(attivita));
    }

    @Override
    public void uploaedContest23(MultipartFile file) {
        Attivita attivita = new Attivita("Contest_Informatica_X_Gioco_4045","CONTEST_INFORMATICA_X_GIOCO", 4045, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        List<String> citta = this.scuolaService.getCitta();
        Iterator<Row> iterator = dataSheet.rowIterator();
        int ncolonna= 1;
        while (iterator.hasNext()){
            Row row = iterator.next();
            if(!row.getCell(7).getStringCellValue().equals("*")){
                String c = row.getCell(7).getStringCellValue().toUpperCase();
                String trovata = this.stringFinderHelper.findMostSimilarString(c,citta);
                List<String> nomi = this.scuolaService.getNomi(trovata);
                String s =row.getCell(6).getStringCellValue();
                String sT= this.stringFinderHelper.findMostSimilarString(s,nomi);
                System.out.println("Data: "+s+" Trovata: "+sT);
                Scuola scuola = scuolaRepository.getScuolaByCittaAndNome(trovata,sT);
                for (int i =0;i<6;i++){
                    if (!row.getCell(i).getStringCellValue().isEmpty()){
                        int fine = row.getCell(i).getStringCellValue().length();
                        int spazio = row.getCell(i).getStringCellValue().lastIndexOf(" ");
                        String nome= row.getCell(i).getStringCellValue().substring(1,spazio).toUpperCase();
                        String cognome= row.getCell(i).getStringCellValue().substring(spazio+1,fine).toUpperCase();
                        String id = nome + cognome + scuola.getNome();
                        Studente stud = new Studente(id, nome, cognome, scuola);
                        System.out.println(stud.toString());
                        attivita.getStudPartecipanti().add(stud);
                    }
                }
            }
        }
        System.out.println(this.save(attivita));
    }


    /**
     * Find information about students that chose UNICAM and their high school, given an activity.
     *
     * @return list of activity view pairs
     */

    @Override
    public List<ActivityViewDTOPair> findStudentsFromActivity(String activityName) {
        List<ActivityViewDTOPair> result = new ArrayList<>();
        Attivita activity = this.attivitaRepository.findByNome(activityName);
        System.out.println(activity.getNome());
        System.out.println("prova");
        if(activity.getNome().equals("InformaticaXGioco")){
            System.out.println("qua");
            activity.getStudPartecipanti().forEach(s -> {
                List<Iscrizioni> i = this.universitarioService.getIscrizioniAnno(4047);
                System.out.println(i.size());
                for (Universitario un : i.get(0).getUniversitari()){
                    if(un.getNome().equals(s.getNome().toUpperCase())){
                        if (un.getCognome().equals(s.getCognome().toUpperCase())){
                            result.add(new ActivityViewDTOPair(un,this.findScuola(un.getComuneScuola(),un.getScuolaProv())));
                        }
                    }
                }
                /*Universitario u = this.universitarioRepository.findByNomeAndCognome(s.getNome().toUpperCase(),s.getCognome().toUpperCase());
                if (u !=null)
                    result.add(new ActivityViewDTOPair(u,this.findScuola(u.getComuneScuola(),u.getScuolaProv())));*/
            });
        }else {
            activity.getStudPartecipanti().forEach(s -> {
                List<Iscrizioni> i = this.universitarioService.getIscrizioniAnno(4047);
                for (Universitario un : i.get(0).getUniversitari()){
                    if(un.getNome().equals(s.getNome().toUpperCase())){
                        if (un.getCognome().equals(s.getCognome().toUpperCase())){
                            if (un.getComuneScuola().equals(s.getScuola().getCitta().toUpperCase())){
                                result.add(new ActivityViewDTOPair(un, this.scuolaRepository.getScuolaByNomeContainingAndAndCitta(
                                        s.getScuola().getNome(), un.getComuneScuola())));
                            }
                        }
                    }
                }
                /*Universitario u = this.universitarioRepository.findByNomeAndCognomeAndComuneScuola(
                        s.getNome().toUpperCase(), s.getCognome().toUpperCase(), s.getScuola().getCitta().toUpperCase());
                if (u != null) {
                    result.add(new ActivityViewDTOPair(u, this.scuolaRepository.getScuolaByNomeContainingAndAndCitta(
                            s.getScuola().getNome(), u.getComuneScuola())));
                }*/
            });
        }
        return result;
    }

    @Override
    public List<Attivita> getAttivita(int anno) {
        int min= anno-6;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoAcc").gte(min).lt(anno));
        return mongoTemplate.find(query,Attivita.class);
    }

    @Override
    public void prova() {
        List<Attivita> a = this.getAttivita(4045);
        System.out.println(a.size());
    }

    private Scuola findScuola(String citta, String scuola){
        List<Scuola> scuole = scuolaRepository.getScuolaByCitta(citta);
        List<String> nomi = new ArrayList<>();
        for (Scuola s : scuole){
            nomi.add(s.getNome());
        }
        return  scuolaRepository.getScuolaByNome(findMostSimilarString(scuola,nomi));
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



    private  String findMostSimilarString(String input, @org.jetbrains.annotations.NotNull List<String> strings) {
        String mostSimilarString = "";
        int minDistance = Integer.MAX_VALUE;
        for (String str : strings) {
            int distance = levenshteinDistance(input, str);
            if (distance < minDistance) {
                minDistance = distance;
                mostSimilarString = str;
            }
        }
        return mostSimilarString;
    }


    private  int levenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                }
            }
        }
        return dp[m][n];
    }





}
