package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.repository.ProfessoreRepository;
import com.example.PiattaformaPCTO_v2.repository.ScuolaRepository;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SimpleProfessoreService implements ProfessoreService{

    @Autowired
    private ProfessoreRepository professoreRepository;
    @Autowired
    private ScuolaRepository scuolaRepository;

    @Override
    public String save(Professore professore) {
        return professoreRepository.save(professore).getEmail();
    }
    @Override
    public String stampa(){
        List<Professore> profs = professoreRepository.findAll();
        String message = "MESSAGGIO:<br> Lunghezza: "+ profs.size()+"<br>";
        message+="<table>";
        message+="<tr><th>Nome</th><th>Cognome</th><th>Email</th><th>Attivita</th><th>Scuola</th><th>Citta</th><th>Regione</th></tr>";
        for (Professore prof:profs) {
            Scuola scuola = scuolaRepository.getScuolaById(prof.getScuolaImp());
            System.out.println(scuola.getNome());
            message += "<tr><th>"+prof.getNome()+"</th><th>"+prof.getCognome()+"</th><th>"+prof.getEmail()+"</th><th>"+prof.getAttivita()+"</th><th>"+scuola.getNome()+"</th><th>"+scuola.getCitta()+"</th><th>"+scuola.getRegione()+"</th></tr>";
        }
        message+="</table>";
        return message;
    }

    @Override
    public List<Professore> getAllProf() {
        List<Professore> p = this.professoreRepository.findAll();
        return p;
    }

    @Override
    public String upload()  {
        int counter = 0;
        XSSFSheet regioni;
        XSSFWorkbook wbRegioni = null;
        try{
            FileInputStream fisRegioni = new FileInputStream(new File("src/main/resources/comuni_regioni.xlsx"));
            wbRegioni = new XSSFWorkbook(fisRegioni);

        }catch(Exception e){}
        regioni = wbRegioni.getSheetAt(0);
        //file da dove prendo i dati
        String[] files = new String[]{"Docenti_attivita.xlsx","Progetto-NERD-2021-2022.xlsx"};
        Set<Professore> professori = new HashSet<Professore>();
        //Docenti attivita
        XSSFSheet sheet = null;
        try{
            FileInputStream fis = new FileInputStream(new File("src/main/resources/Docenti_attivita.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            sheet = wb.getSheetAt(0);

        }catch(FileNotFoundException e){
            System.out.println("Errore file non trovato !!");
        } catch (IOException e) {
            System.out.println("Errore Workbook!!");
        }
        for(Row row:sheet) {
            String nome = "";
            String cognome = "";
            String email = "";
            String scuolaImp = "";
            String citta= "";
            String attivita="";
            for (int i=0; i<5;i++){
                switch (i){
                    case 0:
                        attivita = row.getCell(0).getStringCellValue();
                        break;
                    case 1:
                        try {
                            String[] tmp = row.getCell(1).getStringCellValue().split(" ",2);
                            if(tmp.length>1){
                                nome = tmp[0];
                                cognome = tmp[1];
                            }else{
                                nome = tmp[0];
                            }
                            if(Utilities.isEmail(nome))
                                nome = "";
                        }catch (NullPointerException e) {
                            continue;
                        }
                        break;
                    case 2:
                        try {
                            email = row.getCell(2).getStringCellValue();
                            email = email.toLowerCase();
                            if(!Utilities.isEmail(email)){
                                email = "";
                            }
                        }catch (NullPointerException e) {
                            continue;
                        }
                        break;
                    case 3:
                        try {
                            scuolaImp = row.getCell(3).getStringCellValue();
                            if(Utilities.isEmail(scuolaImp))
                                scuolaImp = "";
                        }catch (NullPointerException e) {
                            continue;
                        }
                        break;
                    case 4:
                        try {
                            citta = row.getCell(4).getStringCellValue();
                            if(Utilities.isEmail(citta))
                                citta = "";
                        }catch (NullPointerException e) {
                            continue;
                        }
                        break;
                }
            }

            //Se nome o email sono vuote salto la riga
            if (nome.isEmpty() || email.isEmpty())
                continue;
            //controllo presenza provincia
            if(citta.contains("(")){
                boolean open = false;

                int cat = citta.length()-1;
                int i = cat;
                do{
                    //rimuovo spazi vuoti in coda
                    if(citta.charAt(cat)==' '){
                        cat--;
                        i--;
                    }
                    if(citta.charAt(cat)==')'){
                        cat--;
                        i--;
                        open = true;
                    }
                    if(citta.charAt(cat)=='('){
                        cat--;
                        i--;
                        open=false;
                    }
                    if(open){
                        cat--;
                        i--;
                    }
                    i--;
                }while(i>=0);
                citta = citta.substring(0,cat);
                if(citta.length()>0){
                    String low = citta.substring(1);
                    char upper = citta.charAt(0);
                    low = low.toLowerCase();
                    upper = Character.toUpperCase(upper);
                    citta = upper+low;
                }
            }
            if(Utilities.isEmail(nome) && !Utilities.isEmail(email)){
                String tmp = email;
                email = nome;
                nome = tmp;
            }
            Professore prof = new Professore(nome,cognome, email, findSchoolId(scuolaImp,citta),attivita);
            //professori.add(prof);
            this.save(prof);
            counter++;
        }
        //Professore[] profs = professori.toArray(new Professore[professori.size()]);

        return "Caricati "+counter+" professori";
    }

    private String findSchoolId(String scuola,String citta){
        //converto tutto in maiuscolo
        citta = citta.toUpperCase();
        scuola = scuola.toUpperCase();
        //Prendo le scuole che si trovano nella stessa citta
        List<Scuola> scuole = scuolaRepository.getScuolaByCitta(citta);
        if(scuole.size()==0){
            scuole= scuolaRepository.findAll();
        }
            List<String> nomiScuole = new ArrayList<>();
            for (Scuola s:scuole) {
                nomiScuole.add(s.getNome());
            }
            ScuolaHelperService helper= new ScuolaHelperService();
            String mostSimilarScuola = helper.findMostSimilarString(scuola,nomiScuole);
            Iterator<Scuola> it = scuole.iterator();
            while(it.hasNext()){
                Scuola tmp = it.next();
                if(tmp.getNome().equals(mostSimilarScuola))
                    return tmp.getIdScuola();
            }


        return "";
    }
}
