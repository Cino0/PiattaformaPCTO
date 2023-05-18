package com.example.PiattaformaPCTO_v2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.print.Doc;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.google.gson.Gson;

public class DocenteService {
    private String pathFile = "src\\main\\resources\\Docenti_attivita.xlsx";
    private String[][] data;
    private Professore[] professori;

    public DocenteService(){
        ArrayList tmp = readRawFile();
        String[][] data = formatFile(tmp);
        this.professori = data2Class(data);
        //print();    
    }

    public ArrayList readRawFile(){
        ArrayList<ArrayList<String>> rawContent = new ArrayList<ArrayList<String>>();
        File file = new File(pathFile);
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        Iterator<Row> rowIterator = null;
        try{
            fis = new FileInputStream(file);
        }catch(FileNotFoundException e){
            
        }
        try{
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            rowIterator= sheet.iterator();
        }catch(Exception e){

        }
        
        //Leggo le righe
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            ArrayList<String> tmp = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                tmp.add(cell.getStringCellValue());
            }
            rawContent.add(tmp);
        }
        return rawContent;
    }
    //Arraylist to Array
    public String[][] formatFile(ArrayList<ArrayList<String>> rawData){
        String[][] data = new String[rawData.size()][5];
        System.out.println("Inizio Formattazione");
        for(int i=0;i<rawData.size();i++){
            for(int j=0;j<5;j++){
                data[i][j] = rawData.get(i).get(j);
            }
        }
        return data;
    }
    //Stampa le classi
    public void print(){
        
        for (Professore professore : professori) {
            System.out.println(professore.toString());
        }
        
    }
    //Trasforma i dati raw in un array di Classi Professore
    public Professore[] data2Class(String[][] data){
        ArrayList<Professore> tmp = new ArrayList<Professore>();

        for (String[] row : data) {
            String nome = row[1];
            String email = row[2];
            String scuola = row[3];
            String citta = row[4];
            Professore professore = new Professore(nome, email, scuola, citta);
            if(checkProfessore(professore))
                tmp.add(professore);
        }
        tmp = removeDuplicates(tmp);
        Professore[] professori = new Professore[tmp.size()];
        return tmp.toArray(professori);
    }

    //Controlla se non è un oggetto vuoto
    private boolean checkProfessore(Professore professore){
        if(professore.getCitta() == "" && professore.getEmail() == "" && professore.getNome() == "" && professore.getScuolaImp() == "")
        return false;
        return true;
    }

    //rimuove i professori con la stessa email in quanto chiave 
    private ArrayList<Professore> removeDuplicates(ArrayList<Professore> professori){
        System.out.println("Numero campi con duplicati :"+professori.size());

        ArrayList<Professore> newProfessori = new ArrayList<>();
        for (Professore professore : professori) {
            boolean isGood= true;
            for(Professore newProfessore: newProfessori){
                if(professore.getEmail().equals(newProfessore.getEmail())){
                    isGood = false;
                    break;
                }
            }
            if(isGood)
                newProfessori.add(professore);
        }

        System.out.println("Numero campi senza duplicati: "+newProfessori.size());
        return newProfessori;
    }
    public static void main(String[] args) {
        DocenteService dos = new DocenteService();
    }
    
}
