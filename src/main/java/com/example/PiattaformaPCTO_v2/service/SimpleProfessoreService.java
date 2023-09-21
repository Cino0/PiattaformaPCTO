package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.example.PiattaformaPCTO_v2.repository.ProfessoreRepository;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SimpleProfessoreService implements ProfessoreService{

    @Autowired
    private ProfessoreRepository professoreRepository;

    @Override
    public String save(Professore professore) {
        return professoreRepository.save(professore).getEmail();
    }

    public static Professore[] getProfessors(XSSFSheet regioni)  {
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
            String email = "";
            String scuolaImp = "";
            String citta= "";
            for (int i=0; i<5;i++){
                switch (i){
                    case 0:
                        break;
                    case 1:
                        try {
                            nome = row.getCell(1).getStringCellValue();
                            if(Utilities.isEmail(nome))
                                nome = "";
                        }catch (NullPointerException e) {
                            continue;
                        }
                        break;
                    case 2:
                        try {
                            email = row.getCell(2).getStringCellValue();
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
            Professore prof = new Professore(nome, email, scuolaImp);
            professori.add(prof);
        }
        Professore[] profs = professori.toArray(new Professore[professori.size()]);
        return profs;
    }

    public static void main(String[] args) {
        XSSFSheet sheet = null;
        try{
            FileInputStream fis = new FileInputStream(new File("src/main/resources/comuni_regioni.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            sheet = wb.getSheetAt(0);


        }catch(Exception e){

        }
       Professore[] professori = SimpleProfessoreService.getProfessors(sheet);
        System.out.println(professori.length);
        for (Professore prof: professori) {
            System.out.println(prof.toJson());
        }

    }
}
